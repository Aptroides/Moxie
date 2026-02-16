package me.a8kj.config.template.experimental.codec;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

/**
 * Provides a unified abstraction for bidirectional data transformation
 * between domain objects and their serialized representations.
 * <p>
 * <b>Note:</b> This is an experimental feature and is currently considered unstable.
 * This adapter composes both a {@link Serializer} and a {@link Deserializer}
 * to ensure consistent encoding and decoding logic across the application.
 * It serves as a central conversion bridge between the domain layer
 * and external storage or transport layers.
 * </p>
 *
 * @param <T> the domain object type
 * @param <S> the serialized representation type
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.3
 */
@RequiredArgsConstructor
public class CodecAdapter<T, S> {

    /**
     * The internal serializer responsible for encoding the domain object.
     */
    private final Serializer<T, S> serializer;

    /**
     * The internal deserializer responsible for decoding the serialized data.
     */
    private final Deserializer<T, S> deserializer;

    /**
     * Serializes the given domain object into its encoded representation.
     * <p>
     * Delegates the transformation process to the configured {@link Serializer}.
     * </p>
     *
     * @param obj the domain object to serialize
     * @return the serialized representation, or {@code null} if the object is {@code null}
     * or serialization fails
     */
    @Nullable
    public S serialize(@Nullable T obj) {
        return serializer.serialize(obj);
    }

    /**
     * Deserializes the provided data into a domain object instance.
     * <p>
     * Delegates the reconstruction process to the configured {@link Deserializer}.
     * If the provided data is {@code null} or invalid, the result may be {@code null}.
     * </p>
     *
     * @param data the serialized data to deserialize, may be {@code null}
     * @return the reconstructed domain object, or {@code null} if deserialization fails
     * or input is {@code null}
     */
    @Nullable
    public T deserialize(@Nullable S data) {
        return deserializer.deserialize(data);
    }
}