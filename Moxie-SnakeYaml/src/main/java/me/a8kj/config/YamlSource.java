package me.a8kj.config;

import me.a8kj.config.file.BaseConfig;
import me.a8kj.config.file.ConfigFile;
import me.a8kj.config.file.operation.ConfigOperation;
import me.a8kj.config.template.memory.impl.GenericMapDataMemory;
import me.a8kj.config.template.memory.impl.MapPairedDataMemory;
import me.a8kj.util.MapStructureUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * A robust YAML implementation of {@link BaseConfig} that supports hierarchical
 * data structures and persistent merging.
 * <p>
 * This class handles the conversion between flat in-memory data (used for easy access)
 * and nested YAML structures (used for human-readable files). It implements a
 * "Read-Modify-Write" pattern to ensure that existing data in the file is preserved
 * unless explicitly overwritten by the application.
 * </p>
 * * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 *
 * @since 0.3
 */
public class YamlSource extends BaseConfig<String> {

    private final Yaml yaml;

    /**
     * Initializes the YAML source with standard block-style formatting.
     * Block style is used to ensure the generated YAML is clean and easy for users to edit.
     */
    public YamlSource() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setIndent(2);
        options.setPrettyFlow(true);
        this.yaml = new Yaml(options);
    }

    /**
     * Reads the YAML file from disk and flattens it into the configuration memory.
     * Existing memory data is cleared before loading to ensure state consistency with the disk.
     *
     * @return A {@link ConfigOperation} representing the read process.
     */
    @Override
    public ConfigOperation<String> read() {
        return new ConfigOperation<>() {
            @Override
            public void execute(ConfigFile<String> context) throws IOException {
                if (!context.file().exists()) return;

                try (Reader reader = getReader(context.file())) {
                    Map<String, Object> data = yaml.load(reader);
                    if (data != null) {
                        context.memory().clear();
                        MapStructureUtils.flatten("", data, context.memory());
                    }
                } catch (Exception e) {
                    throw new IOException("Critical failure reading YAML source: " + context.file().getName(), e);
                }
            }

            @Override
            public String getName() {
                return "READ";
            }
        };
    }

    /**
     * Synchronizes the in-memory data with the physical file using a merge strategy.
     * <p>
     * Logic flow:
     * 1. Load the current file content into a temporary map.
     * 2. Unflatten the in-memory storage into a nested map structure.
     * 3. Merge the memory structure into the file structure (memory takes precedence).
     * 4. Save the combined structure back to the disk.
     * </p>
     *
     * @return A {@link ConfigOperation} representing the update and merge process.
     */
    @Override
    public ConfigOperation<String> update() {
        return new ConfigOperation<>() {
            @Override
            public void execute(ConfigFile<String> context) throws IOException {
                Map<String, Object> finalStructure = new HashMap<>();

                if (context.file().exists() && context.file().length() > 0) {
                    try (Reader reader = getReader(context.file())) {
                        Map<String, Object> existingData = yaml.load(reader);
                        if (existingData != null) {
                            finalStructure.putAll(existingData);
                        }
                    } catch (Exception ignored) {
                    }
                }

                Map<String, Object> storage = extractStorage(context);
                if (storage == null || storage.isEmpty()) return;

                Map<String, Object> memoryStructured = MapStructureUtils.unflatten(storage);

                finalStructure.putAll(memoryStructured);

                try (Writer writer = getWriter(context.file())) {
                    yaml.dump(finalStructure, writer);
                    writer.flush();
                } catch (Exception e) {
                    throw new IOException("Critical failure writing YAML source: " + context.file().getName(), e);
                }
            }

            @Override
            public String getName() {
                return "UPDATE";
            }
        };
    }

    /**
     * Helper method to extract the underlying Map from different memory implementations.
     * Supports both {@link GenericMapDataMemory} and {@link MapPairedDataMemory}.
     * * @param context The configuration file context.
     *
     * @return The raw map storage, or null if the memory type is unsupported.
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> extractStorage(ConfigFile<String> context) {
        if (context.memory() instanceof GenericMapDataMemory<?> generic) {
            return (Map<String, Object>) generic.getStorage();
        }
        if (context.memory() instanceof MapPairedDataMemory paired) {
            return paired.getStorage();
        }
        return null;
    }
}