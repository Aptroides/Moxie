package me.a8kj.config;

import me.a8kj.config.builder.BasicConfigBuilder;
import me.a8kj.config.context.ConfigExecutionContext;
import me.a8kj.config.context.impl.BasicConfigExecutionContext;
import me.a8kj.config.file.PathProviders;
import me.a8kj.config.file.operation.impl.CRUDOperations;
import me.a8kj.config.file.properties.BasicConfigMeta;
import me.a8kj.config.template.experimental.codec.CodecAdapter;
import me.a8kj.config.template.experimental.mapper.DefaultMapperOperator;
import me.a8kj.config.template.experimental.mapper.EntityDataMemory;
import me.a8kj.config.template.registry.ConfigRegistry;
import me.a8kj.eventbus.manager.EventManager;
import me.a8kj.logging.Log;
import me.a8kj.logging.impl.ConsoleLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Test class for validating the YAML configuration lifecycle with Entity Mapping.
 * <p>
 * <b>NOTE:</b> The Entity Mapping system (DefaultMapperOperator, EntityDataMemory)
 * is currently an <b>experimental feature</b> and may undergo breaking changes
 * in future releases.
 * </p>
 */
public class YamlEntityTest {

    public static void main(String[] args) {
        Log.addDestination(new ConsoleLogger());
        String configName = "database_test";

        /*
         * 1. Mapper Setup (Experimental)
         * Defines how the DatabaseSettings object is serialized to and
         * deserialized from the configuration memory.
         */
        DefaultMapperOperator mapper = new DefaultMapperOperator();
        mapper.registerCodec(DatabaseSettings.class, new CodecAdapter<>(
                entity -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("host", entity.host());
                    map.put("port", entity.port());
                    return map;
                },
                map -> new DatabaseSettings(
                        (String) map.get("host"),
                        (int) map.get("port")
                )
        ));

        /*
         * 2. Mock Provider Setup
         * Initializes a standalone configuration environment for testing purposes.
         */
        ConfigRegistry registry = new ConfigRegistry();
        ConfigProvider.load(new ConfigAPI() {
            @Override
            public EventManager getEventManager() {
                return null;
            }

            @Override
            public ConfigRegistry getConfigRegistry() {
                return registry;
            }
        });

        /*
         * 3. Memory Setup
         * Uses EntityDataMemory to support experimental object-to-path mapping.
         */
        EntityDataMemory entityMemory = new EntityDataMemory(mapper);

        /*
         * 4. Configuration Registration
         * Registers the configuration metadata and storage directory.
         */
        ConfigProvider.provide().getConfigRegistry().register(configName,
                BasicConfigBuilder.of(String.class)
                        .meta(BasicConfigMeta.builder()
                                .name(configName)
                                .relativePath(configName + ".yml")
                                .build())
                        .at(PathProviders.currentDir())
                        .memory(entityMemory)
                        .build());

        ConfigExecutionContext<String> context = BasicConfigExecutionContext.of(configName, new YamlSource());

        try {
            System.out.println("Executing Configuration Lifecycle...");

            // Step 1: Ensure file existence without overwriting
            context.execute(CRUDOperations::create);

            // Step 2: Sync in-memory state with the current disk content
            context.execute(CRUDOperations::read);

            // Step 3: Check if the experimental Entity exists in the loaded memory
            Optional<DatabaseSettings> existing = entityMemory.getEntity("settings.mysql", DatabaseSettings.class);

            if (existing.isEmpty()) {
                System.out.println("No existing entity found. Injecting default values...");

                DatabaseSettings defaultDb = new DatabaseSettings("127.0.0.1", 3306);
                entityMemory.setEntity("settings.mysql", defaultDb);

                // Save defaults to disk (Merged with existing file structure)
                context.execute(CRUDOperations::update);
            } else {
                System.out.println("Found existing entity in file: " + existing.get());
            }

            System.out.println("Configuration lifecycle completed successfully.");

        } catch (Exception e) {
            Log.exception("Critical failure during configuration test", e);
        }
    }
}

