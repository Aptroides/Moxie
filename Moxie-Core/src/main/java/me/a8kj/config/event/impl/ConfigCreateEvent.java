package me.a8kj.config.event.impl;

import me.a8kj.config.event.ConfigEvent;
import me.a8kj.config.file.ConfigFile;

/**
 * Event triggered immediately after a configuration file has been successfully created on the disk.
 * This can be used to perform post-creation logic, such as initial data injection or logging.
 *
 * @param <K> the type of data keys used in the configuration file.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public class ConfigCreateEvent<K> extends ConfigEvent<K> {

    /**
     * Constructs a new ConfigCreateEvent.
     *
     * @param configFile the configuration file that was recently created.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public ConfigCreateEvent(ConfigFile<K> configFile) {
        super(configFile);
    }
}