package me.a8kj.config.template.experimental.codec;

import org.jetbrains.annotations.Nullable;

/**
 * Defines a functional contract for transforming domain objects
 * into their serialized representations.
 * <p>
 * <b>Note:</b> This is an experimental feature and is currently considered unstable.
 * Implementations are responsible for converting strongly-typed
 * objects into formats suitable for storage, caching, or
 * network transmission (e.g., transforming a POJO into a {@code Map<String, Object>}).
 * </p>
 *
 * @param <T> the type of the source domain object
 * @param <S> the type of the serialized representation
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.3
 */
@FunctionalInterface
public interface Serializer<T, S> {

    /**
     * Serializes the provided domain object into its encoded form.
     *
     * @param object the object to serialize, may be {@code null}
     * @return the serialized representation of type {@code S},
     * or {@code null} if serialization fails or input is {@code null}
     */
    @Nullable
    S serialize(@Nullable T object);
}