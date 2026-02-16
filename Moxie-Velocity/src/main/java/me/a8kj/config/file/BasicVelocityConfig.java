package me.a8kj.config.file.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.config.ConfigProvider;
import me.a8kj.config.event.impl.ConfigCreateEvent;
import me.a8kj.config.event.impl.ConfigDeleteEvent;
import me.a8kj.config.file.BaseConfig;
import me.a8kj.config.file.ConfigFile;
import me.a8kj.config.file.operation.ConfigOperation;
import me.a8kj.config.template.memory.impl.GenericMapDataMemory;
import me.a8kj.config.template.memory.impl.MapPairedDataMemory;
import me.a8kj.logging.Log;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * A specialized implementation of configuration for Velocity using Configurate 4.
 * <p>
 * This version supports both GenericMap and MapPaired memory types to ensure
 * data is correctly persisted to disk using Yaml BLOCK style for better readability.
 * </p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.2
 */
@RequiredArgsConstructor
@Getter
public class BasicVelocityConfig extends BaseConfig<String> {

    /**
     * Logic executed after the configuration file is created.
     * Fires {@link ConfigCreateEvent} and logs the process.
     *
     * @param config The configuration file instance.
     */
    @Override
    protected void onPostCreate(ConfigFile<String> config) {
        if (config.meta().isLoggingEnabled()) {
            Log.info("Configuration '%s' created.", config.meta().getName());
        }
        ConfigProvider.provide().callEvent(new ConfigCreateEvent<>(config));
    }

    /**
     * Logic executed after the configuration file is deleted.
     * Fires {@link ConfigDeleteEvent} and logs the warning.
     *
     * @param config The configuration file instance.
     */
    @Override
    protected void onPostDelete(ConfigFile<String> config) {
        if (config.meta().isLoggingEnabled()) {
            Log.warn("Configuration '%s' deleted.", config.meta().getName());
        }
        ConfigProvider.provide().callEvent(new ConfigDeleteEvent<>(config));
    }

    /**
     * Provides a read operation to load Yaml data into memory.
     * Uses BLOCK style and 2-space indentation by default.
     *
     * @return A {@link ConfigOperation} for reading data.
     */
    @Override
    public ConfigOperation<String> read() {
        return new ConfigOperation<>() {
            @Override
            public void execute(ConfigFile<String> context) throws IOException {
                File file = context.file();
                if (!file.exists()) return;

                YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                        .path(file.toPath())
                        .nodeStyle(NodeStyle.BLOCK)
                        .indent(2)
                        .build();

                ConfigurationNode root = loader.load();
                flattenNode("", root, context);
            }

            @Override
            public String getName() {
                return "READ";
            }
        };
    }

    /**
     * Provides an update operation to save memory state back to Yaml file.
     * Supports multi-layer path resolution by splitting keys with dot notation.
     *
     * @return A {@link ConfigOperation} for updating data.
     */
    @Override
    public ConfigOperation<String> update() {
        return new ConfigOperation<>() {
            @Override
            public void execute(ConfigFile<String> context) throws IOException {
                File file = context.file();
                YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                        .path(file.toPath())
                        .nodeStyle(NodeStyle.BLOCK)
                        .indent(2)
                        .build();

                ConfigurationNode root = loader.createNode();
                var memory = context.memory();

                /**
                 * Java 21 Pattern Matching:
                 * We check which memory implementation is being used and extract data accordingly.
                 */
                if (memory instanceof GenericMapDataMemory<String> genericMemory) {
                    genericMemory.getStorage().forEach((key, value) -> {
                        try {
                            root.node((Object[]) key.split("\\.")).set(value);
                        } catch (SerializationException e) {
                            throw new RuntimeException("Failed to serialize key: " + key, e);
                        }
                    });
                } else if (memory instanceof MapPairedDataMemory pairedMemory) {
                    pairedMemory.getStorage().forEach((key, value) -> {
                        try {
                            root.node((Object[]) key.toString().split("\\.")).set(value);
                        } catch (SerializationException e) {
                            throw new RuntimeException("Failed to serialize key: " + key, e);
                        }
                    });
                }

                loader.save(root);

                if (context.meta().isLoggingEnabled()) {
                    Log.info("Successfully updated '%s' on disk.", context.meta().getName());
                }
            }

            @Override
            public String getName() {
                return "UPDATE";
            }
        };
    }

    /**
     * Recursively flattens nested Yaml nodes into a flat memory structure.
     *
     * @param path    The current key path.
     * @param node    The current configuration node.
     * @param context The configuration context.
     */

    private void flattenNode(String path, ConfigurationNode node, ConfigFile<String> context) {
        if (node.isMap()) {
            for (Map.Entry<Object, ? extends ConfigurationNode> entry : node.childrenMap().entrySet()) {
                String subKey = entry.getKey().toString();
                String subPath = path.isEmpty() ? subKey : path + "." + subKey;
                flattenNode(subPath, entry.getValue(), context);
            }
        } else if (node.raw() != null) {
            context.memory().set(path, node.raw());
        }
    }
}