package me.a8kj.config;

import me.a8kj.config.file.ConfigFile;
import me.a8kj.config.template.registry.ConfigRegistry;
import me.a8kj.eventbus.Event;
import me.a8kj.eventbus.Listener;
import me.a8kj.eventbus.manager.EventManager;
import java.util.Objects;

/**
 * The core API interface for managing configurations and event handling.
 * Provides access to the event system and the central configuration registry.
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public interface ConfigAPI {

    /**
     * Retrieves the event manager responsible for handling library-specific events.
     *
     * @return the active {@link EventManager} instance.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    EventManager getEventManager();

    /**
     * Retrieves the central registry where all configuration files are stored.
     *
     * @return the {@link ConfigRegistry} instance.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    ConfigRegistry getConfigRegistry();

    /**
     * Retrieves a specific configuration file from the registry using its unique key.
     *
     * @param key the unique identifier of the configuration.
     * @return the {@link ConfigFile} associated with the key.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    default ConfigFile<?> getConfig(String key) {
        return getConfigRegistry().get(key);
    }

    /**
     * Registers a listener to the event system.
     *
     * @param listener the listener instance to register.
     * @throws NullPointerException if the listener is null.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    default void registerListener(Listener listener) {
        Objects.requireNonNull(listener, "Listener cannot be null");
        getEventManager().register(listener);
    }

    /**
     * Unregisters a previously registered listener from the event system.
     *
     * @param listener the listener instance to unregister.
     * @throws NullPointerException if the listener is null.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    default void unregisterListener(Listener listener) {
        Objects.requireNonNull(listener, "Listener cannot be null");
        getEventManager().unregister(listener);
    }

    /**
     * Calls an event and dispatches it to all registered listeners.
     *
     * @param event the event instance to dispatch.
     * @throws NullPointerException if the event is null.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    default void callEvent(Event event) {
        Objects.requireNonNull(event, "Event cannot be null");
        getEventManager().callEvent(event);
    }

}