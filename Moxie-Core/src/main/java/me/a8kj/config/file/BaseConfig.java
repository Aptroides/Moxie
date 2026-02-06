package me.a8kj.config.file;

import me.a8kj.config.file.operation.ConfigOperation;
import me.a8kj.config.file.operation.impl.CRUDOperations;
import me.a8kj.logging.Log;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * An abstract base implementation of {@link CRUDOperations} providing core file management logic.
 * <p>
 * This class serves as the foundation for all configuration formats (YAML, JSON, etc.).
 * It handles the physical file lifecycle on the disk, including creation, resource extraction,
 * and deletion, while providing non-mandatory lifecycle hooks for extended logic.
 * </p>
 *
 * @param <K> The type of data keys used in the configuration.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public abstract class BaseConfig<K> implements CRUDOperations<K> {

    /**
     * Optional hook triggered immediately before the configuration file is created.
     * * @param config The configuration file instance to be created.
     */
    protected void onPreCreate(ConfigFile<K> config) {}

    /**
     * Optional hook triggered after the configuration file has been successfully
     * written to disk, either from a resource or as a new empty file.
     * * @param config The created configuration file instance.
     */
    protected void onPostCreate(ConfigFile<K> config) {}

    /**
     * Optional hook triggered immediately before the configuration file is deleted from disk.
     * * @param config The configuration file instance to be deleted.
     */
    protected void onPreDelete(ConfigFile<K> config) {}

    /**
     * Optional hook triggered after the configuration file and its memory buffer
     * have been successfully cleared and removed.
     * * @param config The deleted configuration file instance.
     */
    protected void onPostDelete(ConfigFile<K> config) {}

    /**
     * Creates a {@link ConfigOperation} for initializing the configuration file.
     * <p>
     * The process includes pre-hook execution, parent directory validation,
     * resource extraction, and post-hook execution upon success.
     * </p>
     *
     * @return A {@link ConfigOperation} for file creation and resource mapping.
     */
    @Override
    public ConfigOperation<K> create() {
        return new ConfigOperation<K>() {
            @Override
            public void execute(ConfigFile<K> config) throws IOException {
                onPreCreate(config);

                File file = config.file();
                boolean logging = config.meta().isLoggingEnabled();
                String name = config.meta().getName();

                if (file.exists()) {
                    if (logging) Log.info("Config '%s' already exists at: %s", name, file.getPath());
                    return;
                }

                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                String resourcePath = config.meta().getRelativePath();

                try (InputStream in = config.getClass().getClassLoader().getResourceAsStream(resourcePath)) {
                    if (in != null) {
                        Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        if (logging) Log.info("Config '%s' created from resource.", name);
                    } else {
                        if (file.createNewFile() && logging) {
                            Log.info("Config '%s' created as a new empty file.", name);
                        }
                    }
                    onPostCreate(config);
                } catch (Exception e) {
                    file.createNewFile();
                    onPostCreate(config);
                }
            }

            @Override
            public String getName() {
                return "CREATE";
            }
        };
    }

    /**
     * Provides a {@link Reader} configured with {@code UTF-8} encoding.
     *
     * @param file The file to read from.
     * @return A UTF-8 encoded {@link Reader}.
     * @throws IOException If the file is inaccessible.
     */
    protected Reader getReader(File file) throws IOException {
        return new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
    }

    /**
     * Provides a {@link Writer} configured with {@code UTF-8} encoding.
     *
     * @param file The file to write to.
     * @return A UTF-8 encoded {@link Writer}.
     * @throws IOException If the file cannot be opened for writing.
     */
    protected Writer getWriter(File file) throws IOException {
        return new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
    }

    /**
     * Creates a {@link ConfigOperation} for deleting the file and clearing memory.
     * <p>
     * Includes pre/post hooks to allow for cleanup logic or event dispatching.
     * </p>
     *
     * @return A {@link ConfigOperation} for file deletion and memory cleanup.
     */
    @Override
    public ConfigOperation<K> delete() {
        return new ConfigOperation<K>() {
            @Override
            public void execute(ConfigFile<K> config) throws IOException {
                onPreDelete(config);

                if (config.file().exists()) {
                    Files.delete(config.file().toPath());
                    onPostDelete(config);
                }

                if (config.memory() != null) {
                    config.memory().clear();
                }
            }

            @Override
            public String getName() {
                return "DELETE";
            }
        };
    }

    @Override
    public abstract ConfigOperation<K> read();

    @Override
    public abstract ConfigOperation<K> update();
}