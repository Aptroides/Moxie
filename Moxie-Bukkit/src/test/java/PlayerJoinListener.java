import me.a8kj.config.ConfigProvider;
import me.a8kj.config.file.impl.BasicConfigFile;
import me.a8kj.config.template.memory.DataMemory;
import me.a8kj.config.template.replacement.Replacement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {


    @EventHandler
    @SuppressWarnings("unchecked")
    public void onPlayerJoin(PlayerJoinEvent event) {
        BasicConfigFile<String> config = (BasicConfigFile<String>) ConfigProvider.provide().getConfig("settings");
        DataMemory<String> memory = config.memory();
        String serverName = memory.getString("server-name");
        event.getPlayer().sendMessage(memory.transform("Hello %player% welcome to our %server%", Replacement.of("player", event.getPlayer().getName())
                , Replacement.of("server", serverName)));
    }

}
