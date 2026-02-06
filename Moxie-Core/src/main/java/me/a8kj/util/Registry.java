package me.a8kj.util;

import lombok.NonNull;

/**
 * A generic contract for object registration and management.
 * This interface defines the standard operations for a central repository,
 * allowing objects of type {@code T} to be stored and retrieved using unique string keys.
 *
 * @param <T> the type of objects maintained in this registry.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public interface Registry<T> {

    /**
     * Registers a value associated with a specific key.
     *
     * @param key   the unique identifier for the entry. Must not be null.
     * @param value the object to be registered. Must not be null.
     */
    void register(@NonNull String key, @NonNull T value);

    /**
     * Removes an entry from the registry using its key.
     *
     * @param key the unique identifier of the entry to be removed. Must not be null.
     */
    void unregister(@NonNull String key);

    /**
     * Checks if the registry contains an entry for the specified key.
     *
     * @param key the identifier to check. Must not be null.
     * @return {@code true} if an entry exists, {@code false} otherwise.
     */
    boolean hasEntry(@NonNull String key);

    /**
     * Retrieves the object associated with the given key.
     *
     * @param key the unique identifier of the entry. Must not be null.
     * @return the registered object of type {@code T}.
     */
    @NonNull
    T get(@NonNull String key);

    /**
     * Provides an iterable collection of all currently registered entries.
     * Each entry is returned as a {@link Pair} containing the key and the object.
     *
     * @return an iterable of key-value pairs.
     */
    @NonNull
    Iterable<Pair<String, T>> entries();
}