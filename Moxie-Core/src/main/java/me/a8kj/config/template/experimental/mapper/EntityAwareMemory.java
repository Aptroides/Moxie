package me.a8kj.config.template.experimental.mapper;

import me.a8kj.config.template.memory.DataMemory;

import java.util.Optional;

/**
 * An entity-aware extension of {@link DataMemory} that enables the storage and retrieval
 * of complex domain objects using structured mapping logic.
 * * <p><b>Note:</b> This is an experimental feature and is currently considered unstable.
 * It provides a high-level abstraction for interacting with entities directly,
 * abstracting away the underlying serialization and flattening processes.</p>
 * * <p>Implementations must provide a {@link MapperOperatorContract} to handle the
 * translation between raw memory paths and strongly-typed objects.</p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.3
 */
public interface EntityAwareMemory extends DataMemory<String> {

    /**
     * Retrieves the mapper service responsible for converting entities to and from
     * memory-compatible structures.
     *
     * @return the {@link MapperOperatorContract} instance used by this memory.
     */
    MapperOperatorContract getMapperService();

    /**
     * Persists an arbitrary object into the memory under the specified path.
     * <p>
     * This method delegates the serialization and flattening logic to the
     * internal mapper service.
     * </p>
     *
     * @param <T>    the type of the entity.
     * @param path   the base configuration path (e.g., "user.profile") where the
     *               entity will be stored.
     * @param entity the domain object instance to store.
     */
    default <T> void setEntity(String path, T entity) {
        getMapperService().save(this, path, entity);
    }

    /**
     * Retrieves and reconstructs an object of the specified type from the memory.
     * <p>
     * This method delegates the unflattening and deserialization logic to the
     * internal mapper service.
     * </p>
     *
     * @param <T>  the expected type of the reconstructed entity.
     * @param path the base configuration path where the entity data resides.
     * @param type the class literal of the entity type to load.
     * @return an {@link Optional} containing the reconstructed entity, or
     * {@link Optional#empty()} if the entity could not be loaded.
     */
    default <T> Optional<T> getEntity(String path, Class<T> type) {
        return getMapperService().load(this, path, type);
    }
}