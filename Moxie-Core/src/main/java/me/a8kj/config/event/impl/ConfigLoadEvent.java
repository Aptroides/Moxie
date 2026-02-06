package me.a8kj.config.event.impl;

import me.a8kj.config.event.ConfigEvent;
import me.a8kj.config.file.ConfigFile;

/**
 * Event triggered when a configuration file's data has been successfully loaded into memory.
 * This is typically fired after a 'read' operation, signaling that the data is now
 * available for retrieval via the config's memory storage.
 *
 * @param <K> the type of data keys used in the configuration file.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public class ConfigLoadEvent<K> extends ConfigEvent<K> {

    /**
     * Constructs a new ConfigLoadEvent.
     *
     * @param configFile the configuration file that has been loaded.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public ConfigLoadEvent(ConfigFile<K> configFile) {
        super(configFile);
    }
}