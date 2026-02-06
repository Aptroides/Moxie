package me.a8kj.config.file.operation.impl;

import me.a8kj.config.file.operation.ConfigOperation;

/**
 * Defines the capability for a configuration to be created.
 * Implementations of this interface provide a specific {@link ConfigOperation}
 * dedicated to initializing the configuration file on the physical storage.
 *
 * @param <K> the type of data keys used in the configuration file.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public interface CreatableConfig<K> {

    /**
     * Provides the operation logic for creating the configuration.
     * This typically involves checking for file existence and copying default resources.
     *
     * @return a {@link ConfigOperation} representing the creation process.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    ConfigOperation<K> create();
}