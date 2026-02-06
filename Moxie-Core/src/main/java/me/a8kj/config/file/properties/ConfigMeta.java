package me.a8kj.config.file.properties;

/**
 * Defines the metadata and properties associated with a configuration file.
 * This interface acts as a descriptor, providing essential information such as
 * identity, location, and behavioral toggles for the configuration system.
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public interface ConfigMeta {

    /**
     * Retrieves the unique name of the configuration.
     * This name is typically used for identification in logs and internal registries.
     *
     * @return the configuration name.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    String getName();

    /**
     * Retrieves the relative path where the configuration file is located.
     * This path is resolved against a base directory to find the physical file.
     *
     * @return the relative file path.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    String getRelativePath();

    /**
     * Determines whether logging is enabled for operations performed on this configuration.
     * Defaults to {@code true}.
     *
     * @return {@code true} if logging is enabled, {@code false} otherwise.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    default boolean isLoggingEnabled() {
        return true;
    }

    /**
     * Retrieves the version string of the configuration schema.
     * Useful for tracking changes and managing data migrations.
     * Defaults to "1.0.0".
     *
     * @return the configuration version.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    default String getVersion() {
        return "1.0.0";
    }
}