package me.a8kj.config.builder;

import me.a8kj.config.file.ConfigFile;
import me.a8kj.config.file.PathProvider;
import me.a8kj.config.file.impl.BasicConfigFile;
import me.a8kj.config.file.properties.ConfigMeta;
import me.a8kj.config.template.memory.impl.StringMapPairedDataMemory;

import java.io.File;
import java.util.Objects;

/**
 * A simplified builder class specifically designed for configurations that use {@link String} keys.
 * This builder pre-initializes with {@link StringMapPairedDataMemory} to streamline the creation process.
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public class StringConfigBuilder {

    private ConfigMeta meta;
    private StringMapPairedDataMemory memory;
    private PathProvider pathProvider;

    /**
     * Private constructor that initializes the default string-based memory storage.
     *
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    private StringConfigBuilder() {
        this.memory = new StringMapPairedDataMemory();
    }

    /**
     * Static factory method to create a new instance of the string-based config builder.
     *
     * @return a new {@link StringConfigBuilder} instance.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public static StringConfigBuilder create() {
        return new StringConfigBuilder();
    }

    /**
     * Sets the configuration metadata including the name and path logic.
     *
     * @param meta the {@link ConfigMeta} instance.
     * @return the current builder instance for chaining.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public StringConfigBuilder meta(ConfigMeta meta) {
        this.meta = meta;
        return this;
    }

    /**
     * Sets the provider responsible for resolving the configuration's file path.
     *
     * @param pathProvider the {@link PathProvider} instance.
     * @return the current builder instance for chaining.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public StringConfigBuilder at(PathProvider pathProvider) {
        this.pathProvider = pathProvider;
        return this;
    }

    /**
     * Allows overriding the default string memory implementation.
     *
     * @param memory a custom {@link StringMapPairedDataMemory} instance.
     * @return the current builder instance for chaining.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public StringConfigBuilder memory(StringMapPairedDataMemory memory) {
        this.memory = memory;
        return this;
    }

    /**
     * Constructs the final {@link ConfigFile} with a guaranteed string-based memory.
     *
     * @return a fully initialized {@link ConfigFile} with String keys.
     * @throws NullPointerException if meta or pathProvider are null.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public ConfigFile<String> build() {
        Objects.requireNonNull(meta, "ConfigMeta must not be null!");
        Objects.requireNonNull(pathProvider, "PathProvider must not be null!");

        if (this.memory == null) {
            this.memory = new StringMapPairedDataMemory();
        }

        File physicalFile = pathProvider.resolve(meta.getRelativePath());
        return new BasicConfigFile<>(meta, physicalFile, memory);
    }
}