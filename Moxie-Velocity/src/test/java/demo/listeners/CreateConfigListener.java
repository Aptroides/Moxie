package demo.listeners;

import me.a8kj.config.event.impl.ConfigCreateEvent;
import me.a8kj.eventbus.Listener;
import me.a8kj.eventbus.annotation.EventSubscribe;
import me.a8kj.logging.Log;
import me.a8kj.logging.LogContext;

/**
 * A specialized listener that monitors configuration creation events.
 * <p>
 * This listener provides real-time feedback through the logging system
 * whenever a new configuration file is successfully initialized within
 * the Moxie-Config framework.
 * </p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.2
 */
public class CreateConfigListener implements Listener {

    /**
     * Handles the {@link ConfigCreateEvent} to log the successful creation of a config file.
     * <p>
     * It extracts the metadata from the event's configuration file to provide
     * descriptive logging output.
     * </p>
     *
     * @param event The event triggered when a configuration is created.
     */
    @EventSubscribe
    public void onCreate(ConfigCreateEvent<String> event) {
        var cfg = event.getConfigFile();
        Log.info(new LogContext("EventBus"), cfg.meta().getName());
    }
}