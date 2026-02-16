package me.a8kj.config.template.experimental.mapper;

/**
 * A centralized contract for managing entity mapping operations within the configuration system.
 * * <p><b>Note:</b> This is an experimental feature and is currently considered unstable.
 * Its API may change in future releases as the mapping system evolves.</p>
 * * <p>This interface aggregates the following capabilities:
 * <ul>
 * <li>{@link CodecRegistry}: Management and registration of type-specific codecs.</li>
 * <li>{@link EntityLoader}: Retrieval and reconstruction of entities from flattened memory structures.</li>
 * <li>{@link EntitySaver}: Decomposition and storage of entities into hierarchical memory paths.</li>
 * </ul>
 * </p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.3
 */
public interface MapperOperatorContract extends CodecRegistry, EntityLoader, EntitySaver {
}