import me.a8kj.config.builder.BasicConfigBuilder;
import me.a8kj.config.file.BaseConfig;
import me.a8kj.config.file.ConfigFile;
import me.a8kj.config.file.PathProviders;
import me.a8kj.config.file.operation.BasicConfigOperationExecutor;
import me.a8kj.config.file.operation.ConfigOperation;
import me.a8kj.config.file.operation.ConfigOperationExecutor;
import me.a8kj.config.file.operation.impl.CRUDOperations;
import me.a8kj.config.file.properties.BasicConfigMeta;
import me.a8kj.config.template.memory.impl.MapPairedDataMemory;
import me.a8kj.logging.Log;
import me.a8kj.logging.impl.ConsoleLogger;

import java.io.IOException;

/**
 * Integration test class for verifying the Moxie configuration lifecycle.
 * This class demonstrates the usage of the Fluent Builder, Path Providers,
 * and the Operation Executor to handle physical file initialization.
 */
public class RandomTest {

    /**
     * Entry point for testing the build and execution flow of a configuration file.
     * * @param args Command line arguments.
     * @throws IOException If any file operation fails during execution.
     */
    public static void main(String[] args) throws IOException {

        /*
         * Setup the logging system to redirect Moxie internal logs to the console.
         */
        Log.addDestination(new ConsoleLogger());

        /*
         * Building a ConfigFile instance using the BasicConfigBuilder.
         * Configuration includes metadata (name, path, logging), physical location,
         * and the memory structure used to store keys.
         */
        ConfigFile<String> config = BasicConfigBuilder.of(String.class)
                .meta(BasicConfigMeta.builder()
                        .name("auth-service")
                        .relativePath("auth/config.yml")
                        .loggingEnabled(true)
                        .build())
                .at(PathProviders.custom("/home/serqux/Downloads"))
                .memory(new MapPairedDataMemory())
                .build();

        /*
         * Defining a concrete CRUDConfig implementation for YAML.
         * In a real-world scenario, read() and update() would handle the
         * actual parsing and saving logic.
         */
        CRUDOperations<String> yaml = new BaseConfig<String>() {
            @Override
            public ConfigOperation<String> read() {
                return null;
            }

            @Override
            public ConfigOperation<String> update() {
                return null;
            }
        };

        /*
         * Initializing the Operation Executor.
         * This component is responsible for running specific ConfigOperations
         * against the built ConfigFile instance.
         */
        ConfigOperationExecutor<String> executor = new BasicConfigOperationExecutor<>(config);

        /*
         * Executing the 'create' operation.
         * This handles directory creation and resource extraction as defined in BaseConfig.
         */
        executor.execute(yaml.create());
    }
}