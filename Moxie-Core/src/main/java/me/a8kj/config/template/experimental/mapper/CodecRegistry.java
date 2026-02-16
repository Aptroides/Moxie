package me.a8kj.config.template.experimental.mapper;

import me.a8kj.config.template.experimental.codec.CodecAdapter;

import java.util.Map;

/**
 * Defines a registry for managing and retrieving codecs used in entity mapping.
 * * <p><b>Note:</b> This is an experimental feature and is currently considered unstable.
 * It provides the foundation for the mapping system by allowing the registration of
 * {@link CodecAdapter} instances that define how specific Java types are converted
 * to and from raw {@link Map} structures.</p>
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.3
 */
public interface CodecRegistry {

    /**
     * Registers a codec for a specific class type.
     * <p>
     * Once registered, the mapping system will use this codec whenever it encounters
     * the specified type during serialization (saving) or deserialization (loading).
     * </p>
     *
     * @param <T>   the domain object type.
     * @param type  the class literal of the type to associate with the codec.
     * @param codec the {@link CodecAdapter} containing the serialization and
     *              deserialization logic.
     */
    <T> void registerCodec(Class<T> type, CodecAdapter<T, Map<String, Object>> codec);
}