package me.a8kj.config.template.memory.impl;

import lombok.Getter;
import me.a8kj.config.template.memory.DataMemory;
import me.a8kj.config.template.replacement.Replacement;
import me.a8kj.config.template.replacement.ReplacementProcessor;
import me.a8kj.config.template.replacement.impl.PlaceholderProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A flexible, map-based implementation of {@link DataMemory} that uses a {@link HashMap}
 * for internal storage. This class is ideal for configurations where keys are dynamic
 * or do not follow a specific Enum structure.
 *
 * @param <K> the type of keys maintained by this memory storage.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public class GenericMapDataMemory<K> implements DataMemory<K> {

    /**
     * The internal map used to store configuration values.
     * Initialized with a standard {@link HashMap} for O(1) average time complexity.
     */
    protected final Map<K, Object> storage = new HashMap<>();

    /**
     * The default replacement processor for this memory instance.
     * Uses a percent-style strategy (e.g., %placeholder%) by default.
     */
    @Getter
    private final ReplacementProcessor processor = PlaceholderProcessor.percent();

    @Override
    public void set(K key, Object value) {
        storage.put(key, value);
    }

    @Override
    public Object get(K key) {
        return storage.get(key);
    }

    @Override
    public String getString(K key) {
        Object val = get(key);
        return val != null ? val.toString() : null;
    }

    @Override
    public int getInt(K key, int defaultValue) {
        Object val = get(key);
        return (val instanceof Number n) ? n.intValue() : defaultValue;
    }

    @Override
    public boolean getBoolean(K key, boolean defaultValue) {
        Object val = get(key);
        return (val instanceof Boolean b) ? b : defaultValue;
    }

    @Override
    public double getDouble(K key, double defaultValue) {
        Object val = get(key);
        return (val instanceof Number n) ? n.doubleValue() : defaultValue;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getStringList(K key) {
        Object val = get(key);
        return (val instanceof List) ? (List<String>) val : null;
    }

    @Override
    public boolean contains(K key) {
        return storage.containsKey(key);
    }

    @Override
    public Set<K> getKeys(K path, boolean deep) {
        return storage.keySet();
    }

    @Override
    public void clear() {
        this.storage.clear();
    }

    /**
     * Applies text transformations using the internal {@link ReplacementProcessor}.
     *
     * @param text         the string to be processed.
     * @param replacements the placeholder mappings to apply.
     * @return the processed string with all replacements injected.
     */
    @Override
    public String transform(String text, Replacement... replacements) {
        return processor.process(text, replacements);
    }
}