import me.a8kj.config.BukkitConfigAPI;
import me.a8kj.config.ConfigProvider;
import me.a8kj.config.builder.BasicConfigBuilder;
import me.a8kj.config.context.ConfigExecutionContext;
import me.a8kj.config.context.impl.BasicConfigExecutionContext;
import me.a8kj.config.file.BasicBukkitConfig;
import me.a8kj.config.file.PathProviders;
import me.a8kj.config.file.operation.impl.CRUDOperations;
import me.a8kj.config.file.properties.BasicConfigMeta;
import me.a8kj.config.template.memory.impl.GenericMapDataMemory;
import me.a8kj.config.template.registry.ConfigRegistry;
import me.a8kj.eventbus.manager.EventManager;
import me.a8kj.logging.Log;
import me.a8kj.logging.impl.ConsoleLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * Main Plugin class demonstrating the full Moxie-Config integration using Java 8.
 * <p>
 * This class showcases the architecture of the library, emphasizing the strict
 * separation between configuration registration, data management, and the
 * fluent execution context.
 * </p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public class Main extends JavaPlugin {

    /**
     * Called when the plugin is enabled.
     * Initializes the Global Provider and registers the initial configurations.
     */
    @Override
    public void onEnable() {


        /**
         * 1. API & Provider Initialization.
         * We initialize the BukkitConfigAPI with a new EventManager and ConfigRegistry.
         * This must be called once during startup to populate the ConfigProvider.
         */
        ConfigProvider.load(new BukkitConfigAPI(new EventManager(), new ConfigRegistry()));

        // if you wanna to to use log btw so enable it
        Log.addDestination(new ConsoleLogger());
        ConfigProvider.provide().getEventManager().register(new CreateConfigListener());
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener() , this);



        /**
         * 2. Configuration Registration.
         * Here we register the "settings" configuration.
         * Note that we use the universal ConfigBuilder to define its metadata,
         * physical path, memory type, and its dedicated operator.
         */
        ConfigProvider.provide().getConfigRegistry().register("settings",
                BasicConfigBuilder.of(String.class)
                        .meta(BasicConfigMeta.builder().
                                loggingEnabled(false)
                                .name("settings")
                                .relativePath("settings.yml")
                                .build())
                        .at(PathProviders.custom(this.getDataFolder().getPath()))
                        .memory(new GenericMapDataMemory<>())
                        .build());

        try {
            /**
             * 3. Lifecycle Execution.
             * After registration, we proceed to perform disk-related operations.
             */
            setupConfiguration();
        } catch (IOException e) {
            getLogger().severe("Could not initialize configuration: " + e.getMessage());
        }
    }

    /**
     * Demonstrates the usage of {@link ConfigExecutionContext} to perform
     * fluent configuration operations such as creation, reading, and updating.
     * <p>
     * This method avoids direct interaction with executors or raw file objects,
     * utilizing the high-level Execution Context instead.
     * </p>
     *
     * @throws IOException if any file operation (disk IO) fails.
     */
    private void setupConfiguration() throws IOException {

        /**
         * Using the Fluent Execution API:
         * This chain ensures the file exists (create) and its data is loaded (read).
         */
        BasicConfigExecutionContext.of("settings", new BasicBukkitConfig())
                .execute(CRUDOperations::create)
                .execute(CRUDOperations::read);

        /**
         * Accessing and Manipulating Data:
         * We retrieve the context specifically for "settings" to modify the memory buffer.
         */
        ConfigExecutionContext<String> context = BasicConfigExecutionContext.of("settings", new BasicBukkitConfig());
        context.config().memory().set("server-name", "Moxie Network");

        /**
         * Persisting Changes:
         * Saving the modified memory back to the physical YAML file.
         */
        context.execute(CRUDOperations::update);

        getLogger().info("Configuration 'settings' has been successfully initialized and updated!");
    }
}