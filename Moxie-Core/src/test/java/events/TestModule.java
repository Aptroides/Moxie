package events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.config.ConfigAPI;
import me.a8kj.config.template.registry.ConfigRegistry;
import me.a8kj.eventbus.Event;
import me.a8kj.eventbus.manager.EventManager;
import me.a8kj.eventbus.Listener;

/**
 * Implementation of the {@link ConfigAPI} acting as the central hub for the module.
 * It encapsulates the event management and configuration registry, providing
 * a unified access point for all configuration-related operations within the module.
 */
@RequiredArgsConstructor
@Getter
public class TestModule implements ConfigAPI {

    /**
     * The internal event bus manager for dispatching and listening to lifecycle events.
     */
    private final EventManager eventManager;

    /**
     * The global registry for storing and retrieving configuration instances by unique identifiers.
     */
    private final ConfigRegistry configRegistry;

    /**
     * Registers a listener to the internal event bus.
     * * @param listener The listener instance to register.
     */
    public void registerListener(Listener listener) {
        this.eventManager.register(listener);
    }

    /**
     * Dispatches an event to all registered subscribers through the module's event bus.
     * * @param event The event instance to call.
     */
    public void callEvent(Event event) {
        this.eventManager.callEvent(event);
    }
}