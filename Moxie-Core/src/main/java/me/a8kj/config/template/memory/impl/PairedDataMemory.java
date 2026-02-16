package me.a8kj.config.template.memory.impl;

import me.a8kj.config.template.memory.DataMemory;
import me.a8kj.config.template.memory.MemoryEntry;
import me.a8kj.util.Pair;

/**
 * An extension of {@link DataMemory} that supports advanced retrieval using {@link MemoryEntry}.
 * This interface introduces the ability to fetch "detailed" results, returning both the
 * configuration key and its associated value wrapped in a {@link Pair}.
 *
 * @param <K> the type of keys maintained by this memory storage.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public interface PairedDataMemory<K> extends DataMemory<K> {

    /**
     * Fetches a detailed result for a given {@link MemoryEntry}.
     * This method retrieves the value using the entry's internal logic and pairs it
     * with the original key for comprehensive data handling.
     *
     * @param <T>   the expected type of the value.
     * @param entry the pre-defined memory entry containing the key and type strategy.
     * @return a {@link Pair} containing the resolved key and the fetched value.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    @SuppressWarnings("unchecked")
    default <T> Pair<K, T> fetchDetailed(MemoryEntry<T> entry) {
        T val = entry.fetch((DataMemory<String>) this);
        return Pair.of((K) entry.getEntry().key(), val);
    }
}