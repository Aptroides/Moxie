package me.a8kj.config.file.operation;

import lombok.RequiredArgsConstructor;
import me.a8kj.config.file.ConfigFile;
import me.a8kj.logging.Log;

import java.io.IOException;

/**
 * A standard implementation of {@link ConfigOperationExecutor} that orchestrates the execution
 * of configuration operations with integrated logging and error handling.
 * This executor ensures that every operation is tracked and any failures are properly logged.
 *
 * @param <K> the type of data keys used in the configuration.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
@RequiredArgsConstructor
public class BasicConfigOperationExecutor<K> implements ConfigOperationExecutor<K> {

    /**
     * The target configuration file upon which operations will be performed.
     *
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    private final ConfigFile<K> configFile;

    /**
     * Executes a given {@link ConfigOperation} while managing the execution lifecycle.
     * It handles pre-execution logging, triggers the operation logic, and logs
     * either the successful completion or the resulting exception.
     *
     * @param operation the operation to be performed on the configuration file.
     * @throws IOException if the operation encounters an I/O error during execution.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    @Override
    public void execute(ConfigOperation<K> operation) throws IOException {
        String configName = configFile.meta().getName();
        boolean isLogging = configFile.meta().isLoggingEnabled();

        String operationType = operation.getName();

        try {
            if (isLogging) {
                Log.info("[%s] Starting operation on config: %s", operationType, configName);
            }

            operation.execute(configFile);

            if (isLogging) {
                Log.info("[%s] Finished successfully for: %s", operationType, configName);
            }
        } catch (IOException e) {
            if (isLogging) {
                Log.exception(String.format("[%s] Failed for config: %s", operationType, configName), e);
            }
            throw e;
        }
    }
}