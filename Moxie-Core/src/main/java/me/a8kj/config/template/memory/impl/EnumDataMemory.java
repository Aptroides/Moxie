package me.a8kj.config.template.memory.impl;

import lombok.Getter;
import me.a8kj.config.template.memory.DataMemory;
import me.a8kj.config.template.replacement.Replacement;
import me.a8kj.config.template.replacement.ReplacementProcessor;
import me.a8kj.config.template.replacement.impl.PlaceholderProcessor;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An abstract implementation of {@link DataMemory} that uses an {@link EnumMap} as its internal storage.
 * This class provides high-performance data retrieval and storage specifically designed for
 * configurations that use Enum constants as keys.
 *
 * @param <E> the type of enum to be used as keys.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public abstract class EnumDataMemory<E extends Enum<E>> implements DataMemory<E> {

    /**
     * The internal storage map optimized for enum keys.
     */
    protected final Map<E, Object> storage;

    /**
     * The processor responsible for handling text transformations and placeholders.
     * Defaults to a {@link PlaceholderProcessor} using the percent sign (%) strategy.
     */
    @Getter
    private final ReplacementProcessor processor = PlaceholderProcessor.percent();

    /**
     * Constructs a new EnumDataMemory instance.
     *
     * @param enumClass the class of the enum keys to initialize the {@link EnumMap}.
     */
    public EnumDataMemory(Class<E> enumClass) {
        this.storage = new EnumMap<>(enumClass);
    }

    @Override
    public void set(E key, Object value) {
        storage.put(key, value);
    }

    @Override
    public Object get(E key) {
        return storage.get(key);
    }

    @Override
    public String getString(E key) {
        Object val = get(key);
        return val != null ? val.toString() : null;
    }

    @Override
    public int getInt(E key, int defaultValue) {
        Object val = get(key);
        return (val instanceof Number n) ? n.intValue() : defaultValue;
    }

    @Override
    public boolean getBoolean(E key, boolean defaultValue) {
        Object val = get(key);
        return (val instanceof Boolean b) ? b : defaultValue;
    }

    @Override
    public double getDouble(E key, double defaultValue) {
        Object val = get(key);
        return (val instanceof Number n) ? n.doubleValue() : defaultValue;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getStringList(E key) {
        Object val = get(key);
        return (val instanceof List) ? (List<String>) val : null;
    }

    @Override
    public boolean contains(E key) {
        return storage.containsKey(key);
    }

    @Override
    public Set<E> getKeys(E path, boolean deep) {
        return storage.keySet();
    }

    @Override
    public void clear() {
        this.storage.clear();
    }

    /**
     * Transforms the input text by processing provided replacements through the
     * configured {@link ReplacementProcessor}.
     *
     * @param text         the string containing placeholders.
     * @param replacements the variables to be injected into the text.
     * @return the fully processed string.
     */
    @Override
    public String transform(String text, Replacement... replacements) {
        return processor.process(text, replacements);
    }
}