package events;

import me.a8kj.config.event.impl.ConfigCreateEvent;
import me.a8kj.eventbus.Listener;
import me.a8kj.eventbus.annotation.EventSubscribe;
import me.a8kj.logging.Log;

/**
 * Event listener designed to intercept the configuration lifecycle.
 * This class reacts specifically to the creation of new configuration files,
 * allowing for post-initialization logic such as logging or validation.
 */
public class CreateConfigListener implements Listener {

    /**
     * Triggered automatically when a {@link ConfigCreateEvent} is fired.
     * * @param event The event payload containing the newly created {@link me.a8kj.config.file.ConfigFile}.
     */
    @EventSubscribe
    public void onCreateConfig(ConfigCreateEvent<String> event) {
        /*
         * Logging the success message using the integrated Moxie logging system.
         * Extracts the configuration name from the metadata for clear identification.
         */
        Log.info("Moxie Lifecycle: Successfully initialized configuration [%s]",
                event.getConfigFile().meta().getName());
    }
}