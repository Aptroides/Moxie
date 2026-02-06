package me.a8kj.config.file.operation.impl;

import me.a8kj.config.file.operation.ConfigOperation;

/**
 * Defines the capability for a configuration to be updated or saved.
 * Implementations of this interface provide a {@link ConfigOperation} responsible
 * for serializing the in-memory data and persisting it back to the physical file on disk.
 *
 * @param <K> the type of data keys used in the configuration file.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public interface UpdatableConfig<K> {

    /**
     * Provides the operation logic for updating (saving) the configuration data.
     * This typically involves taking the current state from {@link me.a8kj.config.template.memory.DataMemory}
     * and writing it to the file, ensuring all changes are synchronized.
     *
     * @return a {@link ConfigOperation} representing the updating/saving process.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    ConfigOperation<K> update();
}