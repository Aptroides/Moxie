package me.a8kj.config.file.operation.impl;

import me.a8kj.config.file.operation.ConfigOperation;

/**
 * Defines the capability for a configuration to be read.
 * Implementations of this interface provide a {@link ConfigOperation} responsible
 * for parsing the physical file content and loading it into the configuration's memory storage.
 *
 * @param <K> the type of data keys used in the configuration file.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public interface ReadableConfig<K> {

    /**
     * Provides the operation logic for reading the configuration data.
     * This typically involves deserializing the file (e.g., YAML, JSON, Properties)
     * and populating the {@link me.a8kj.config.template.memory.DataMemory}.
     *
     * @return a {@link ConfigOperation} representing the reading/loading process.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    ConfigOperation<K> read();
}