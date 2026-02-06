package me.a8kj.config.builder;

import me.a8kj.config.file.ConfigFile;
import me.a8kj.config.file.PathProvider;
import me.a8kj.config.file.impl.BasicConfigFile;
import me.a8kj.config.file.properties.ConfigMeta;
import me.a8kj.config.template.memory.impl.PairedDataMemory;

import java.io.File;
import java.util.Objects;

/**
 * A specialized builder class for creating {@link ConfigFile} instances that utilize {@link PairedDataMemory}.
 * This builder is designed for configurations that require a key-value mapping structure in memory.
 *
 * @param <K> the type of keys used in the paired data memory.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public class PairedConfigBuilder<K> {

    private ConfigMeta meta;
    private PairedDataMemory<K> memory;
    private PathProvider pathProvider;

    /**
     * Private constructor to enforce the use of the static factory method.
     *
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    private PairedConfigBuilder() {}

    /**
     * Static factory method to initiate the paired builder with a specific key type.
     *
     * @param <T> the type of keys for the new builder.
     * @param keyType the class representing the key type.
     * @return a new instance of {@link PairedConfigBuilder}.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public static <T> PairedConfigBuilder<T> of(Class<T> keyType) {
        return new PairedConfigBuilder<>();
    }

    /**
     * Sets the metadata for the configuration, containing identifying information and paths.
     *
     * @param meta the {@link ConfigMeta} instance.
     * @return the current builder instance for chaining.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public PairedConfigBuilder<K> meta(ConfigMeta meta) {
        this.meta = meta;
        return this;
    }

    /**
     * Sets the path provider used to determine the storage location of the configuration file.
     *
     * @param pathProvider the {@link PathProvider} instance.
     * @return the current builder instance for chaining.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public PairedConfigBuilder<K> at(PathProvider pathProvider) {
        this.pathProvider = pathProvider;
        return this;
    }

    /**
     * Sets the paired data memory implementation for managing key-value configuration data.
     *
     * @param memory the {@link PairedDataMemory} instance.
     * @return the current builder instance for chaining.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public PairedConfigBuilder<K> memory(PairedDataMemory<K> memory) {
        this.memory = memory;
        return this;
    }

    /**
     * Validates all required components and creates a new {@link ConfigFile} instance.
     *
     * @return a fully initialized {@link ConfigFile} using paired memory.
     * @throws NullPointerException if meta, memory, or pathProvider are not initialized.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public ConfigFile<K> build() {
        Objects.requireNonNull(meta, "ConfigMeta must not be null!");
        Objects.requireNonNull(memory, "PairedDataMemory must not be null!");
        Objects.requireNonNull(pathProvider, "PathProvider must not be null!");

        File physicalFile = pathProvider.resolve(meta.getRelativePath());

        return new BasicConfigFile<>(meta, physicalFile, memory);
    }
}