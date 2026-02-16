package me.a8kj.config.template.memory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

/**
 * Strategy-based enumeration for retrieving typed data from a {@link DataMemory} instance.
 * Each constant encapsulates the logic required to extract a specific data type,
 * reducing boilerplate code when fetching configuration values.
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
@RequiredArgsConstructor
@Getter
public enum MemoryDataType {

    /**
     * Handles retrieval of plain text.
     */
    STRING((mem, key) -> mem.getString(key)),

    /**
     * Handles retrieval of integer values with a default of 0.
     */
    INTEGER((mem, key) -> mem.getInt(key, 0)),

    /**
     * Handles retrieval of boolean flags with a default of false.
     */
    BOOLEAN((mem, key) -> mem.getBoolean(key, false)),

    /**
     * Handles retrieval of floating-point numbers with a default of 0.0.
     */
    DOUBLE((mem, key) -> mem.getDouble(key, 0.0)),

    /**
     * Handles retrieval of string collections.
     */
    STRING_LIST((mem, key) -> mem.getStringList(key)),

    /**
     * Handles retrieval of raw objects (Generic/Fallback).
     */
    OBJECT((mem, key) -> mem.get(key));

    /**
     * The internal extraction logic using a {@link BiFunction}.
     * Takes a {@link DataMemory} and a key, then returns the mapped value.
     */
    private final BiFunction<DataMemory<String>, String, Object> extractor;

    /**
     * Executes the extraction strategy for the given memory and key.
     *
     * @param <T>    the expected return type.
     * @param memory the memory source to pull data from.
     * @param key    the configuration path/key.
     * @return the casted value of type T.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    @SuppressWarnings("unchecked")
    public <T> T getValue(DataMemory<String> memory, String key) {
        return (T) extractor.apply(memory, key);
    }
}