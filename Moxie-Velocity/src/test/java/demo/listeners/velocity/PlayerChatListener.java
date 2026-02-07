package demo.listeners.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import me.a8kj.config.ConfigProvider;
import me.a8kj.config.template.memory.impl.VelocityDataMemory;
import me.a8kj.config.template.replacement.Replacement;
import me.a8kj.logging.Log;

/**
 * Listener responsible for handling player chat events within the Velocity proxy.
 * <p>
 * This class utilizes the Moxie-Config {@link VelocityDataMemory} to fetch
 * formatted chat components and re-broadcast them to all connected players,
 * bypassing standard chat limitations.
 * </p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.2
 */
public class PlayerChatListener {

    private final ProxyServer server;

    /**
     * Injects the proxy server instance required for broadcasting messages.
     *
     * @param server The {@link ProxyServer} instance provided by Velocity's injection system.
     */
    @Inject
    public PlayerChatListener(ProxyServer server) {
        this.server = server;
    }

    /**
     * Intercepts player chat to apply custom formatting based on configuration.
     * <p>
     * The original chat result is denied to prevent "Illegal Characters" errors
     * caused by legacy color symbols, and a custom formatted component is sent
     * instead to all online players.
     * </p>
     *
     * @param event The chat event triggered by a player.
     */
    @Subscribe
    public void onPlayerChat(PlayerChatEvent event) {
        var tagsCfg = ConfigProvider.provide().getConfig("tags");
        if (tagsCfg == null) return;

        VelocityDataMemory memory = (VelocityDataMemory) tagsCfg.memory();

        if (memory.contains("chat-format")) {
            var formattedComponent = memory.getComponent("chat-format",
                    Replacement.of("player", event.getPlayer().getUsername()),
                    Replacement.of("message", event.getMessage())
            );

            event.setResult(PlayerChatEvent.ChatResult.denied());

            server.getAllPlayers().forEach(player -> player.sendMessage(formattedComponent));

            Log.info("[Chat] " + event.getPlayer().getUsername() + ": " + event.getMessage());
        }
    }
}