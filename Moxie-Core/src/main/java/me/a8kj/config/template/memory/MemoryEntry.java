package me.a8kj.config.template.memory;

import lombok.Getter;
import me.a8kj.util.Pair;

/**
 * Represents a predefined entry in the configuration memory.
 * This class encapsulates both the access key and the expected data type,
 * providing a type-safe way to fetch values without manual casting or
 * repeated key strings.
 *
 * @param <T> the expected type of the value held by this entry.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
@Getter
public class MemoryEntry<T> {

    /**
     * The underlying pair containing the configuration key and its corresponding data type.
     */
    private final Pair<String, MemoryDataType> entry;

    /**
     * Private constructor to enforce the use of the static factory method.
     *
     * @param key  the configuration path/key.
     * @param type the {@link MemoryDataType} strategy to use for extraction.
     */
    private MemoryEntry(String key, MemoryDataType type) {
        this.entry = Pair.of(key, type);
    }

    /**
     * Creates a new MemoryEntry instance.
     *
     * @param <T>  the expected return type.
     * @param key  the configuration path/key.
     * @param type the {@link MemoryDataType} representing the data's nature.
     * @return a new {@link MemoryEntry} instance.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public static <T> MemoryEntry<T> of(String key, MemoryDataType type) {
        return new MemoryEntry<>(key, type);
    }

    /**
     * Fetches the value associated with this entry from the provided memory source.
     *
     * @param memory the {@link DataMemory} instance to pull data from.
     * @return the value of type T stored at the designated key.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    public T fetch(DataMemory<String> memory) {
        return entry.value().getValue(memory, entry.key());
    }
}