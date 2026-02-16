package me.a8kj.config.builder;

import me.a8kj.config.file.ConfigFile;
import me.a8kj.config.file.PathProvider;
import me.a8kj.config.file.impl.BasicConfigFile;
import me.a8kj.config.file.properties.ConfigMeta;
import me.a8kj.config.template.memory.DataMemory;

import java.io.File;
import java.util.Objects;

/**
 * A builder class for creating instances of {@link ConfigFile} using a fluent API.
 * This class facilitates the assembly of configuration metadata, memory storage, and path resolution.
 *
 * @param <K> the type of data handled by the configuration.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public class BasicConfigBuilder<K> {

    private ConfigMeta meta;
    private DataMemory<K> memory;
    private PathProvider pathProvider;

    /**
     * Static factory method to initiate the builder with a specific key type.
     *
     * @param <T>     the type of data for the new builder.
     * @param keyType the class representing the data type.
     * @return a new instance of {@link BasicConfigBuilder}.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public static <T> BasicConfigBuilder<T> of(Class<T> keyType) {
        return new BasicConfigBuilder<>();
    }

    /**
     * Sets the metadata for the configuration, such as name and relative path.
     *
     * @param meta the {@link ConfigMeta} instance.
     * @return the current builder instance for chaining.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public BasicConfigBuilder<K> meta(ConfigMeta meta) {
        this.meta = meta;
        return this;
    }

    /**
     * Sets the path provider used to resolve the physical location of the file.
     *
     * @param pathProvider the {@link PathProvider} instance.
     * @return the current builder instance for chaining.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public BasicConfigBuilder<K> at(PathProvider pathProvider) {
        this.pathProvider = pathProvider;
        return this;
    }

    /**
     * Sets the memory storage implementation for holding the configuration data.
     *
     * @param memory the {@link DataMemory} implementation.
     * @return the current builder instance for chaining.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public BasicConfigBuilder<K> memory(DataMemory<K> memory) {
        this.memory = memory;
        return this;
    }

    /**
     * Validates the provided components and builds a {@link BasicConfigFile} instance.
     *
     * @return a fully initialized {@link ConfigFile}.
     * @throws NullPointerException if any of the required components (meta, memory, pathProvider) are null.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public ConfigFile<K> build() {
        Objects.requireNonNull(meta, "ConfigMeta must not be null!");
        Objects.requireNonNull(memory, "DataMemory must not be null!");
        Objects.requireNonNull(pathProvider, "PathProvider must not be null!");

        File physicalFile = pathProvider.resolve(meta.getRelativePath());

        return new BasicConfigFile<>(meta, physicalFile, memory);
    }
}