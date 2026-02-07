package me.a8kj.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.config.file.ConfigFile;
import me.a8kj.config.template.registry.ConfigRegistry;
import me.a8kj.eventbus.manager.EventManager;

/**
 * Implementation of the {@link ConfigAPI} specifically for the Velocity Proxy platform.
 * <p>
 * This class serves as the main entry point for managing configurations and events
 * within a Velocity environment, providing access to the global registry and event bus.
 * </p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.2
 */
@RequiredArgsConstructor
@Getter
public class VelocityConfigAPI implements ConfigAPI {

    /**
     * The manager responsible for handling configuration-related events.
     */
    private final EventManager eventManager;

    /**
     * The registry containing all registered configuration files for the proxy.
     */
    private final ConfigRegistry configRegistry;

    /**
     * Retrieves the configuration registry instance.
     *
     * @return the {@link ConfigRegistry} for this API instance.
     */
    @Override
    public ConfigRegistry getConfigRegistry() {
        return configRegistry;
    }

    /**
     * Retrieves the event manager instance.
     *
     * @return the {@link EventManager} used for dispatching configuration events.
     */
    @Override
    public EventManager getEventManager() {
        return eventManager;
    }

    /**
     * Retrieves a specific configuration file by its unique key.
     * <p>
     * This implementation delegates the lookup to the default behavior defined in {@link ConfigAPI}.
     * </p>
     *
     * @param key the unique identifier of the configuration.
     * @return the {@link ConfigFile} associated with the key, or null if not found.
     */
    @Override
    public ConfigFile<?> getConfig(String key) {
        return ConfigAPI.super.getConfig(key);
    }
}