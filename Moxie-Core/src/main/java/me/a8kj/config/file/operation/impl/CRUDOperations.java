package me.a8kj.config.file.operation.impl;

/**
 * A comprehensive interface that aggregates all standard configuration life-cycle operations.
 * By extending {@link CreatableConfig}, {@link ReadableConfig}, {@link UpdatableConfig},
 * and {@link DeletableConfig}, this interface represents a complete CRUD (Create, Read, Update, Delete)
 * capability for a configuration file.
 *
 * @param <K> the type of data keys used in the configuration file.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public interface CRUDOperations<K> extends
        CreatableConfig<K>,
        ReadableConfig<K>,
        UpdatableConfig<K>,
        DeletableConfig<K> {
}