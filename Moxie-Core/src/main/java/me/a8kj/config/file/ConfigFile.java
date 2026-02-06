package me.a8kj.config.file;

import me.a8kj.config.file.properties.ConfigMeta;
import me.a8kj.config.template.memory.DataMemory;

import java.io.File;

/**
 * Represents a configuration file as a unified object.
 * This interface links the configuration's metadata, its physical location on disk,
 * and its data representation in memory.
 *
 * @param <K> the type of data keys used in the configuration.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public interface ConfigFile<K> {

    /**
     * Retrieves the metadata associated with this configuration.
     * Metadata includes identifying properties like name, relative path, and logging preferences.
     *
     * @return the {@link ConfigMeta} instance.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    ConfigMeta meta();

    /**
     * Retrieves the physical file object pointing to the configuration's location on the disk.
     *
     * @return the {@link File} object.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    File file();

    /**
     * Retrieves the memory storage holding the configuration's loaded data.
     *
     * @return the {@link DataMemory} implementation instance.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    DataMemory<K> memory();

}