package me.a8kj.config.file.operation.impl;

import me.a8kj.config.file.operation.ConfigOperation;

/**
 * Defines the capability for a configuration to be deleted.
 * Implementations of this interface provide a specific {@link ConfigOperation}
 * designed to safely remove the configuration file from disk and clear associated memory.
 *
 * @param <K> the type of data keys used in the configuration file.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public interface DeletableConfig<K> {

    /**
     * Provides the operation logic for deleting the configuration.
     * This typically handles the physical deletion of the file and wiping any in-memory data structures.
     *
     * @return a {@link ConfigOperation} representing the deletion process.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    ConfigOperation<K> delete();
}