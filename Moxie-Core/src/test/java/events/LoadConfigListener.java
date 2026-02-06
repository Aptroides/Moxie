package events;

import me.a8kj.config.event.impl.ConfigLoadEvent;
import me.a8kj.eventbus.Listener;
import me.a8kj.eventbus.annotation.EventSubscribe;
import me.a8kj.logging.Log;

/**
 * Event listener that reacts to configuration load operations.
 * This class is responsible for post-load verification, allowing the system
 * to acknowledge and process data immediately after it has been populated
 * into memory.
 */
public class LoadConfigListener implements Listener {

    /**
     * Intercepts the {@link ConfigLoadEvent} to verify data integrity.
     * * @param event The event payload containing the loaded {@link me.a8kj.config.file.ConfigFile}.
     */
    @EventSubscribe
    public void onLoadConfig(ConfigLoadEvent<String> event) {
        /*
         * Accesses the configuration's memory buffer to retrieve a specific key.
         * This demonstrates the immediate availability of data after the 'read' operation.
         */
        Object testValue = event.getConfigFile().memory().get("test");

        if (testValue != null) {
            Log.info("Data Verification: Successfully retrieved key [test] with value [%s]", testValue.toString());
        } else {
            Log.warn("Data Verification: Key [test] was not found in the loaded configuration memory.");
        }
    }
}