package me.a8kj.config.context;

import me.a8kj.config.file.ConfigFile;
import me.a8kj.config.file.operation.ConfigOperation;
import me.a8kj.config.file.operation.impl.CRUDOperations;

import java.io.IOException;
import java.util.function.Function;

/**
 * Interface defining the execution context for configuration operations.
 * Allows developers to implement custom execution logic while maintaining a fluent API.
 *
 * @param <K> the type of data keys used in the configuration.
 */
public interface ConfigExecutionContext<K> {

    /**
     * Executes a specific operation within this context.
     *
     * @param task a function that provides the operation from the available CRUD operations.
     * @return the current context for chaining.
     * @throws IOException if the execution fails.
     */
    ConfigExecutionContext<K> execute(Function<CRUDOperations<K>, ConfigOperation<K>> task) throws IOException;

    /**
     * Retrieves the underlying configuration file.
     *
     * @return the {@link ConfigFile} instance.
     */
    ConfigFile<K> config();

    /**
     * Retrieves the operations handler (operator) used by this context.
     *
     * @return the {@link CRUDOperations} instance.
     */
    CRUDOperations<K> operations();
}