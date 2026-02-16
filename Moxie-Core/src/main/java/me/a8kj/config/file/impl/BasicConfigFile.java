package me.a8kj.config.file.impl;

import me.a8kj.config.file.ConfigFile;
import me.a8kj.config.file.properties.ConfigMeta;
import me.a8kj.config.template.memory.DataMemory;

import java.io.File;

/**
 * A fundamental implementation of {@link ConfigFile} using Java Records.
 * This record serves as an immutable container for configuration components,
 * ensuring a consistent state between metadata, physical storage, and memory.
 *
 * @param <K>    the type of data keys used in the configuration.
 * @param meta   the metadata defining the configuration's identity and behavior.
 * @param file   the physical file representation on the system.
 * @param memory the data structure used for in-memory storage and manipulation.
 *               * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public record BasicConfigFile<K>(ConfigMeta meta, File file, DataMemory<K> memory) implements ConfigFile<K> {

}