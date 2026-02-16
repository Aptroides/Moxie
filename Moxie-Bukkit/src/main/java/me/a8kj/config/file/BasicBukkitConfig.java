package me.a8kj.config.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.config.ConfigAPI;
import me.a8kj.config.ConfigProvider;
import me.a8kj.config.event.impl.ConfigCreateEvent;
import me.a8kj.config.event.impl.ConfigDeleteEvent;
import me.a8kj.config.file.operation.ConfigOperation;
import me.a8kj.config.template.memory.impl.MapPairedDataMemory;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * A standard implementation of {@link BukkitConfig} specifically designed for YAML files.
 * <p>
 * This class bridges the gap between Bukkit's {@link YamlConfiguration} and the Moxie
 * memory system. It implements the lifecycle hooks from {@code BaseConfig} to dispatch
 * events through the {@link ConfigAPI}.
 * </p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
@RequiredArgsConstructor
@Getter
public class BasicBukkitConfig extends BukkitConfig<String> {


    /**
     * Dispatches a {@link ConfigCreateEvent} after the configuration file is successfully created.
     *
     * @param config The created configuration file instance.
     */
    @Override
    protected void onPostCreate(ConfigFile<String> config) {
        ConfigProvider.provide().callEvent(new ConfigCreateEvent<String>(config));
    }

    /**
     * Dispatches a {@link ConfigDeleteEvent} after the configuration file is successfully deleted.
     *
     * @param config The deleted configuration file instance.
     */
    @Override
    protected void onPostDelete(ConfigFile<String> config) {
        ConfigProvider.provide().callEvent(new ConfigDeleteEvent<String>(config));
    }

    /**
     * Creates a {@link ConfigOperation} to read data from a YAML file into memory.
     * <p>
     * The operation loads the {@link YamlConfiguration} from disk and populates the
     * memory buffer with non-section keys.
     * </p>
     *
     * @return a {@link ConfigOperation} for reading YAML data.
     */
    @Override
    public ConfigOperation<String> read() {
        return new ConfigOperation<String>() {
            @Override
            public void execute(ConfigFile<String> context) {
                File file = context.file();
                if (!file.exists()) return;

                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

                for (String key : yaml.getKeys(true)) {
                    if (!yaml.isConfigurationSection(key)) {
                        context.memory().set(key, yaml.get(key));
                    }
                }
            }

            @Override
            public String getName() {
                return "READ";
            }
        };
    }

    /**
     * Creates a {@link ConfigOperation} to save the current memory state back to a YAML file.
     * <p>
     * This operation synchronizes the {@link MapPairedDataMemory} storage with a
     * {@link YamlConfiguration} instance before saving to disk.
     * </p>
     *
     * @return a {@link ConfigOperation} for updating YAML disk storage.
     */
    @Override
    public ConfigOperation<String> update() {
        return new ConfigOperation<String>() {
            @Override
            public void execute(ConfigFile<String> context) throws IOException {
                File file = context.file();
                YamlConfiguration yaml = new YamlConfiguration();

                if (context.memory() instanceof MapPairedDataMemory) {
                    ((MapPairedDataMemory) context.memory()).getStorage().forEach(yaml::set);
                }

                yaml.save(file);
            }

            @Override
            public String getName() {
                return "UPDATE";
            }
        };
    }
}