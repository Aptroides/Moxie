package me.a8kj.config.event.impl;

import me.a8kj.config.event.ConfigEvent;
import me.a8kj.config.file.ConfigFile;

/**
 * Event triggered immediately after a configuration file has been successfully saved to the disk.
 * This event signifies that the changes made in memory have been synchronized with the physical file,
 * ensuring data persistence.
 *
 * @param <K> the type of data keys used in the configuration file.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public class ConfigSaveEvent<K> extends ConfigEvent<K> {

    /**
     * Constructs a new ConfigSaveEvent.
     *
     * @param configFile the configuration file that was successfully saved.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public ConfigSaveEvent(ConfigFile<K> configFile) {
        super(configFile);
    }
}