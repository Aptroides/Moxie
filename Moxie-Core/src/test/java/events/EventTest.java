package events;

import me.a8kj.config.builder.BasicConfigBuilder;
import me.a8kj.config.event.impl.ConfigCreateEvent;
import me.a8kj.config.event.impl.ConfigLoadEvent;
import me.a8kj.config.file.BaseConfig;
import me.a8kj.config.file.ConfigFile;
import me.a8kj.config.file.PathProviders;
import me.a8kj.config.file.operation.BasicConfigOperationExecutor;
import me.a8kj.config.file.operation.ConfigOperation;
import me.a8kj.config.file.operation.ConfigOperationExecutor;
import me.a8kj.config.file.operation.impl.CRUDOperations;
import me.a8kj.config.file.properties.BasicConfigMeta;
import me.a8kj.config.template.memory.impl.MapPairedDataMemory;
import me.a8kj.config.template.registry.ConfigRegistry;
import me.a8kj.eventbus.manager.EventManager;
import me.a8kj.logging.Log;
import me.a8kj.logging.impl.ConsoleLogger;

import java.io.IOException;

/**
 * End-to-end integration test for the Moxie event system and configuration registry.
 * This class demonstrates how to bind an EventManager with a ConfigRegistry within a module
 * to create a reactive and accessible configuration lifecycle.
 */
public class EventTest {

    private static TestModule module;

    /**
     * Main execution flow for testing event triggers and registry lookups.
     * * @param args Command line arguments.
     *
     * @throws IOException If file operations encounter issues.
     */
    public static void main(String[] args) throws IOException {
        /*
         * 1. Module and Logger Initialization.
         * Sets up the environment by linking the EventManager and ConfigRegistry.
         */
        module = new TestModule(new EventManager(), new ConfigRegistry());
        Log.addDestination(new ConsoleLogger());

        /*
         * 2. Listener Registration.
         * Subscribes specific handlers to lifecycle events (Create and Load).
         */
        module.registerListener(new CreateConfigListener());
        module.registerListener(new LoadConfigListener());

        /*
         * 3. Config Construction.
         * Builds the metadata and physical path provider for the configuration.
         */
        ConfigFile<String> config = BasicConfigBuilder.of(String.class)
                .meta(BasicConfigMeta.builder()
                        .name("main-config")
                        .relativePath("sasa/ex.yml")
                        .loggingEnabled(true)
                        .build())
                .at(PathProviders.currentDir())
                .memory(new MapPairedDataMemory())
                .build();

        /*
         * 4. Registry Enrollment.
         * Stores the config instance globally within the module's registry for later access.
         */
        module.getConfigRegistry().register("main-config", config);

        /*
         * 5. CRUD Simulation (YAML).
         * Defines the behavior for reading and updating, including custom event triggers
         * during the read operation.
         */
        CRUDOperations<String> yaml = new BaseConfig<String>() {
            @Override
            public ConfigOperation<String> read() {
                return c -> {
                    c.memory().set("test", "test");
                    // Triggering the Load event manually during the read process
                    module.callEvent(new ConfigLoadEvent<String>(c));
                };
            }

            @Override
            public ConfigOperation<String> update() {
                return c -> Log.info("Updating config via Moxie operation...");
            }
        };

        /*
         * 6. Execution via Operation Executor.
         * Runs the logic defined in the CRUD implementation against the Config instance.
         */
        ConfigOperationExecutor<String> executor = new BasicConfigOperationExecutor<>(config);

        executor.execute(yaml.create());
        executor.execute(yaml.read());

        /*
         * 7. Manual Event Dispatch.
         * Fires a creation event through the module's event bus to notify listeners.
         */
        module.getEventManager().callEvent(new ConfigCreateEvent<>(config));

        /*
         * Testing global access via the registry.
         */
        testRegistryAccess();
    }

    /**
     * Demonstrates how to retrieve a configuration instance from the registry
     * without having a direct reference to the builder.
     */
    private static void testRegistryAccess() {
        Log.info("--- Testing Registry Access ---");

        // Retrieval using the unique identifier assigned during registration.
        ConfigFile<?> retrievedConfig = module.getConfigRegistry().get("main-config");

        if (retrievedConfig != null) {
            Log.info("Retrieved Config Name: " + retrievedConfig.meta().getName());
        }
    }
}