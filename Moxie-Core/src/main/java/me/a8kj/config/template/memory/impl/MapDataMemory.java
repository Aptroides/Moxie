package me.a8kj.config.template.memory.impl;

import lombok.Getter;
import me.a8kj.config.template.memory.DataMemory;
import me.a8kj.config.template.memory.MemoryDataType;
import me.a8kj.config.template.replacement.Replacement;
import me.a8kj.config.template.replacement.ReplacementProcessor;
import me.a8kj.config.template.replacement.impl.PlaceholderProcessor;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A specialized implementation of {@link DataMemory} that uses {@link MemoryDataType} as its keys.
 * Internally, it leverages an {@link EnumMap} for high-performance mapping, ensuring
 * efficient storage and retrieval of configuration data categorized by type.
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public class MapDataMemory implements DataMemory<MemoryDataType> {

    /**
     * Internal storage optimized for {@link MemoryDataType} keys.
     */
    private final Map<MemoryDataType, Object> storage = new EnumMap<>(MemoryDataType.class);

    /**
     * The processor for text transformations.
     * Defaults to the percent-sign (%) placeholder strategy.
     */
    @Getter
    private final ReplacementProcessor processor = PlaceholderProcessor.percent();

    @Override
    public void set(MemoryDataType key, Object value) {
        storage.put(key, value);
    }

    @Override
    public Object get(MemoryDataType key) {
        return storage.get(key);
    }

    @Override
    public String getString(MemoryDataType key) {
        Object val = get(key);
        return val != null ? val.toString() : null;
    }

    @Override
    public int getInt(MemoryDataType key, int defaultValue) {
        Object val = get(key);
        return (val instanceof Number n) ? n.intValue() : defaultValue;
    }

    @Override
    public boolean getBoolean(MemoryDataType key, boolean defaultValue) {
        Object val = get(key);
        return (val instanceof Boolean b) ? b : defaultValue;
    }

    @Override
    public double getDouble(MemoryDataType key, double defaultValue) {
        Object val = get(key);
        return (val instanceof Number n) ? n.doubleValue() : defaultValue;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getStringList(MemoryDataType key) {
        Object val = get(key);
        return (val instanceof List) ? (List<String>) val : null;
    }

    @Override
    public boolean contains(MemoryDataType key) {
        return storage.containsKey(key);
    }

    @Override
    public Set<MemoryDataType> getKeys(MemoryDataType path, boolean deep) {
        return storage.keySet();
    }

    @Override
    public void clear() {
        this.storage.clear();
    }

    /**
     * Transforms text by applying placeholders using the internal {@link ReplacementProcessor}.
     *
     * @param text         the raw text with placeholders.
     * @param replacements the data to be injected.
     * @return the processed and formatted string.
     */
    @Override
    public String transform(String text, Replacement... replacements) {
        return processor.process(text, replacements);
    }
}