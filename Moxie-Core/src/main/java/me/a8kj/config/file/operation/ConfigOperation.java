package me.a8kj.config.file.operation;

import me.a8kj.config.file.ConfigFile;

import java.io.IOException;

/**
 * A functional interface representing a single operation to be performed on a {@link ConfigFile}.
 * This follows the Command Pattern, allowing various actions (like CRUD) to be encapsulated
 * and passed around as objects.
 *
 * @param <K> the type of data keys used in the configuration file.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
@FunctionalInterface
public interface ConfigOperation<K> {

    /**
     * Executes the specific logic of the operation on the provided configuration file.
     *
     * @param config the {@link ConfigFile} instance to operate on.
     * @throws IOException if an I/O error occurs during the operation execution.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    void execute(ConfigFile<K> config) throws IOException;

    /**
     * Returns a human-readable name for the operation, primarily used for logging and debugging.
     * By default, it returns "AnonymousProcess" for lambdas or anonymous classes,
     * and the simple class name for concrete implementations.
     *
     * @return the string name of the operation.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    default String getName() {
        return getClass().isAnonymousClass() || getClass().isSynthetic()
                ? "AnonymousProcess"
                : getClass().getSimpleName();
    }
}