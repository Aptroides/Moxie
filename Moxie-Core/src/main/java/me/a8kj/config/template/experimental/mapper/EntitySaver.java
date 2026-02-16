package me.a8kj.config.template.experimental.mapper;

import me.a8kj.config.template.memory.DataMemory;

/**
 * Defines the contract for decomposing and persisting domain entities into a flattened
 * configuration memory.
 * * <p><b>Note:</b> This is an experimental feature and is currently considered unstable.
 * It serves as a core component of the mapping system, responsible for the serialization
 * phase where complex objects are transformed into path-based key-value pairs.</p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.3
 */
public interface EntitySaver {

    /**
     * Serializes and saves an entity into the provided {@link DataMemory} under a specific path.
     *
     * @param <T>    the type of the entity being saved.
     * @param memory the target memory implementation where data will be stored.
     * @param path   the base configuration path (e.g., "database.mysql") under which the
     *               entity's fields will be flattened.
     * @param entity the domain object instance to persist.
     */
    <T> void save(DataMemory<String> memory, String path, T entity);
}