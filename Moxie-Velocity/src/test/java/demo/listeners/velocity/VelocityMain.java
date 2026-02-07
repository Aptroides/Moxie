package demo.listeners.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import demo.listeners.CreateConfigListener;
import me.a8kj.config.ConfigProvider;
import me.a8kj.config.VelocityConfigAPI;
import me.a8kj.config.builder.BasicConfigBuilder;
import me.a8kj.config.context.impl.BasicConfigExecutionContext;
import me.a8kj.config.file.impl.BasicVelocityConfig;
import me.a8kj.config.file.PathProviders;
import me.a8kj.config.file.operation.impl.CRUDOperations;
import me.a8kj.config.file.properties.BasicConfigMeta;
import me.a8kj.config.template.memory.DataMemory;
import me.a8kj.config.template.memory.impl.VelocityDataMemory;
import me.a8kj.config.template.registry.ConfigRegistry;
import me.a8kj.eventbus.manager.EventManager;
import me.a8kj.logging.Log;
import me.a8kj.logging.impl.ConsoleLogger;

import java.io.IOException;
import java.nio.file.Path;

/**
 * The main entry point for the Moxie-Config demonstration plugin on Velocity.
 * <p>
 * This class handles the initialization of the configuration API, registration of
 * settings files, and setup of necessary event listeners for the proxy.
 * </p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.2
 */
@Plugin(
        id = "moxie_test",
        name = "MoxieTest",
        version = "0.2",
        authors = "a8kj"
)
public class VelocityMain {

    private final ProxyServer server;
    private final Path dataDirectory;

    /**
     * Initializes the plugin with required Velocity components.
     *
     * @param server        The {@link ProxyServer} instance.
     * @param dataDirectory The {@link Path} to the plugin's data folder.
     */
    @Inject
    public VelocityMain(ProxyServer server, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.dataDirectory = dataDirectory;
    }

    /**
     * Handles the proxy initialization phase.
     * <p>
     * Sets up the logging system, loads the Moxie-Config provider, and initializes
     * the configuration lifecycle for the tags system.
     * </p>
     *
     * @param event The {@link ProxyInitializeEvent} triggered by Velocity.
     */
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        Log.addDestination(new ConsoleLogger());
        Log.info("Moxie-Config Velocity implementation is starting...");

        ConfigProvider.load(new VelocityConfigAPI(new EventManager(), new ConfigRegistry()));
        ConfigProvider.provide().registerListener(new CreateConfigListener());

        ConfigProvider.provide().getConfigRegistry().register("tags",
                BasicConfigBuilder.of(String.class)
                        .meta(BasicConfigMeta.builder()
                                .name("tags")
                                .relativePath("tags.yml")
                                .loggingEnabled(true)
                                .build())
                        .at(PathProviders.custom(dataDirectory.toString()))
                        .memory(new VelocityDataMemory())
                        .build());

        var tagsContext = BasicConfigExecutionContext.<String>of("tags", new BasicVelocityConfig());

        try {
            tagsContext
                    .execute(CRUDOperations::create)
                    .execute(CRUDOperations::read);

            DataMemory<String> memory = tagsContext.config().memory();

            if (!memory.contains("chat-format")) {
                Log.info("Config 'chat-format' not found, setting default values...");

                memory.set("chat-format", "&8[&9Member&8] &f%player%: &7%message%");
                memory.set("admin-format", "&4[Admin] &c%player%: &f%message%");

                tagsContext.execute(CRUDOperations::update);
            }
        } catch (IOException e) {
            Log.exception("Critical error during Velocity configuration initialization", e);
        }

        server.getEventManager().register(this, new PlayerChatListener(this.server));
        Log.info("Moxie-Config fully initialized for Velocity!");
    }
}