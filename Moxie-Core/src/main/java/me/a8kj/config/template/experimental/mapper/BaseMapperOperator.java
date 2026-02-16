package me.a8kj.config.template.experimental.mapper;

import me.a8kj.config.template.experimental.codec.CodecAdapter;
import me.a8kj.config.template.memory.DataMemory;
import me.a8kj.util.MapStructureUtils;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An abstract base implementation of {@link MapperOperatorContract} that handles codec
 * registration and provides core logic for entity serialization and deserialization.
 * * <p><b>Note:</b> This is an experimental feature and is currently considered unstable.
 * This class facilitates the conversion between domain entities and flattened memory
 * structures by orchestrating the interaction between {@link CodecAdapter} and
 * {@link MapStructureUtils}.</p>
 * * <p>The class uses a thread-safe registry to store codecs and supports polymorphic
 * codec lookups using type compatibility checks.</p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.3
 */
public abstract class BaseMapperOperator implements MapperOperatorContract {

    /**
     * Internal registry mapping classes to their corresponding map-based codecs.
     */
    private final Map<Class<?>, CodecAdapter<?, Map<String, Object>>> registry = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     * <p>Registers a codec in the internal thread-safe map for future use in
     * load/save operations.</p>
     */
    @Override
    public <T> void registerCodec(Class<T> type, CodecAdapter<T, Map<String, Object>> codec) {
        registry.put(type, codec);
    }

    /**
     * Attempts to find a suitable codec for the specified class type.
     * <p>
     * This method searches for a codec registered for the exact type or a supertype
     * (via {@link Class#isAssignableFrom(Class)}).
     * </p>
     *
     * @param <T>  the entity type.
     * @param type the class literal to lookup.
     * @return the associated {@link CodecAdapter}, or {@code null} if no compatible
     * codec is registered.
     */
    @SuppressWarnings("unchecked")
    protected <T> CodecAdapter<T, Map<String, Object>> findCodec(Class<T> type) {
        return registry.entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(type))
                .map(entry -> (CodecAdapter<T, Map<String, Object>>) entry.getValue())
                .findFirst()
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation unflattens the data from the memory into a Map structure
     * before passing it to the appropriate codec for deserialization.
     * </p>
     */
    @Override
    public <T> Optional<T> load(DataMemory<String> memory, String path, Class<T> type) {
        CodecAdapter<T, Map<String, Object>> codec = findCodec(type);
        if (codec == null) return Optional.empty();

        Map<String, Object> data = MapStructureUtils.unflattenUnderPath(path, memory);
        return data.isEmpty() ? Optional.empty() : Optional.ofNullable(codec.deserialize(data));
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation serializes the entity into a Map using its registered
     * codec and then flattens that Map into the provided memory at the specified path.
     * </p>
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> void save(DataMemory<String> memory, String path, T entity) {
        if (entity == null) return;

        CodecAdapter<T, Map<String, Object>> codec = findCodec((Class<T>) entity.getClass());
        if (codec == null) return;

        Map<String, Object> serialized = codec.serialize(entity);
        if (serialized != null) {
            MapStructureUtils.flatten(path, serialized, memory);
        }
    }
}