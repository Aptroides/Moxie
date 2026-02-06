package me.a8kj.config.file.properties;

import lombok.Builder;
import lombok.Getter;

/**
 * A standard implementation of {@link ConfigMeta} using the Builder pattern.
 * This class holds the essential identity and behavioral properties of a configuration file,
 * such as its name, path, versioning, and logging preferences.
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
@Getter
@Builder
public class BasicConfigMeta implements ConfigMeta {

    /**
     * The unique identifying name for this configuration.
     * Used primarily for logging and identification within the system.
     */
    private final String name;

    /**
     * The relative path where the configuration file is located,
     * typically relative to the base directory provided by a {@link me.a8kj.config.file.PathProvider}.
     */
    private final String relativePath;

    /**
     * Flag to determine if operations related to this configuration should be logged.
     * Defaults to {@code true}.
     */
    @Builder.Default
    private final boolean loggingEnabled = true;

    /**
     * The current version of the configuration schema.
     * Useful for tracking migrations or format changes. Defaults to "1.0.0".
     */
    @Builder.Default
    private final String version = "1.0.0";
}