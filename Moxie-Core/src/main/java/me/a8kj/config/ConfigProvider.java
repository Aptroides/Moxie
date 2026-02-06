package me.a8kj.config;

/**
 * A static provider class that acts as a global access point for the {@link ConfigAPI}.
 * This class ensures that the API is initialized once and remains accessible throughout the application lifecycle.
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public final class ConfigProvider {

    private static ConfigAPI api;

    /**
     * Initializes the provider with a specific API implementation.
     * This method must be called only once during the application startup.
     *
     * @param api the {@link ConfigAPI} implementation to load.
     * @throws IllegalStateException if the API has already been loaded.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public static void load(ConfigAPI api) {
        if (ConfigProvider.api != null) {
            throw new IllegalStateException("Configuration API is already loaded!");
        }
        ConfigProvider.api = api;
    }

    /**
     * Retrieves the currently loaded API implementation.
     *
     * @return the active {@link ConfigAPI} instance.
     * @throws IllegalStateException if the API has not been initialized yet.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public static ConfigAPI provide() {
        if (api == null) {
            throw new IllegalStateException("Configuration API is not initialized! Call load() first.");
        }
        return api;
    }
}