package me.a8kj.config.file.operation;

import java.io.IOException;

/**
 * An executor interface responsible for dispatching and managing the execution of {@link ConfigOperation}s.
 * It serves as the invoker in the command pattern, ensuring that operations are applied correctly
 * to the underlying configuration system.
 *
 * @param <K> the type of data keys used in the configuration file.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public interface ConfigOperationExecutor<K> {

    /**
     * Executes a single configuration operation.
     *
     * @param operation the {@link ConfigOperation} to be executed.
     * @throws IOException if an I/O error occurs during execution.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    void execute(ConfigOperation<K> operation) throws IOException;

    /**
     * Executes multiple configuration operations in the order they are provided.
     * This is a convenience method for batching several actions (e.g., Create then Read).
     *
     * @param operations a varargs array of {@link ConfigOperation}s to execute.
     * @throws IOException if any of the operations encounter an I/O error.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    @SuppressWarnings("unchecked")
    default void executeAll(ConfigOperation<K>... operations) throws IOException {
        for (ConfigOperation<K> operation : operations) {
            execute(operation);
        }
    }
}