package me.a8kj.config.template.experimental.mapper;

import me.a8kj.config.template.memory.DataMemory;

import java.util.Optional;

/**
 * Defines the contract for reconstructing domain entities from a flattened
 * configuration memory.
 * * <p><b>Note:</b> This is an experimental feature and is currently considered unstable.
 * It serves as a core component of the mapping system, responsible for the
 * deserialization phase where path-based key-value pairs are unflattened and
 * transformed back into complex objects.</p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.3
 */
public interface EntityLoader {

    /**
     * Loads and reconstructs an entity from the provided {@link DataMemory}
     * based on a specific path.
     *
     * @param <T>    the expected type of the reconstructed entity.
     * @param memory the source memory implementation containing the flattened data.
     * @param path   the base configuration path (e.g., "database.mysql") where the
     *               entity's data is stored.
     * @param type   the class literal of the entity to be reconstructed.
     * @return an {@link Optional} containing the reconstructed entity, or
     * {@link Optional#empty()} if the data is missing or incompatible.
     */
    <T> Optional<T> load(DataMemory<String> memory, String path, Class<T> type);
}