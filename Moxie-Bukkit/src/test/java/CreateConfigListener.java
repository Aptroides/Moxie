import me.a8kj.config.event.impl.ConfigCreateEvent;
import me.a8kj.eventbus.annotation.EventSubscribe;
import me.a8kj.logging.Log;

public class CreateConfigListener implements me.a8kj.eventbus.Listener {


    @EventSubscribe
    public void onS(ConfigCreateEvent<String> event) {
        Log.info("%s file created done and this message from event bus system ", event.getConfigFile().meta().getName());
    }
}
