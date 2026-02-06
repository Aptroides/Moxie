package me.a8kj.config.event.impl;

import me.a8kj.config.event.ConfigEvent;
import me.a8kj.config.file.ConfigFile;

/**
 * Event triggered immediately after a configuration file has been deleted from the disk.
 * This event is useful for handling cleanup tasks, such as removing references to the
 * configuration from registries or stopping tasks that rely on it.
 *
 * @param <K> the type of data keys used in the configuration file.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public class ConfigDeleteEvent<K> extends ConfigEvent<K> {

    /**
     * Constructs a new ConfigDeleteEvent.
     *
     * @param configFile the configuration file that was deleted.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public ConfigDeleteEvent(ConfigFile<K> configFile) {
        super(configFile);
    }
}