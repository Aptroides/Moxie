package me.a8kj.config.template.experimental.codec;

import org.jetbrains.annotations.Nullable;

/**
 * Defines a functional contract for reconstructing domain objects
 * from their serialized representations.
 * <p>
 * <b>Note:</b> This is an experimental feature and is currently considered unstable.
 * Implementations are responsible for converting raw or encoded data
 * (such as a {@code Map<String, Object>}) back into strongly-typed domain instances.
 * </p>
 *
 * @param <T> the type of the reconstructed domain object
 * @param <S> the type of the serialized representation
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.3
 */
@FunctionalInterface
public interface Deserializer<T, S> {

    /**
     * Deserializes the provided data into a domain object instance.
     *
     * @param data the serialized data, may be {@code null}
     * @return the reconstructed object of type {@code T},
     * or {@code null} if deserialization fails or input is invalid
     */
    @Nullable
    T deserialize(@Nullable S data);
}