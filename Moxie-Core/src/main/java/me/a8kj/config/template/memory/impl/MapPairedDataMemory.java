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
 * A implementation of {@link PairedDataMemory} specialized for {@link String} keys.
 * This class serves as a concrete storage handler, utilizing a {@link HashMap}
 * to manage configuration entries while providing integrated support for text placeholders.
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
@Getter
public class MapPairedDataMemory implements PairedDataMemory<String> {

    /**
     * The internal map for storing configuration values indexed by string keys.
     */
    private final Map<String, Object> storage = new HashMap<>();

    /**
     * The engine used for processing dynamic text replacements.
     * Defaults to the percent-sign (%) placeholder strategy (e.g., %key%).
     */
    @Getter
    private final ReplacementProcessor processor = PlaceholderProcessor.percent();

    @Override
    public void set(String key, Object value) {
        storage.put(key, value);
    }

    @Override
    public Object get(String key) {
        return storage.get(key);
    }

    @Override
    public String getString(String key) {
        Object val = get(key);
        return val != null ? val.toString() : null;
    }

    @Override
    public int getInt(String key, int defaultValue) {
        Object val = get(key);
        return (val instanceof Number n) ? n.intValue() : defaultValue;
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        Object val = get(key);
        return (val instanceof Boolean b) ? b : defaultValue;
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        Object val = get(key);
        return (val instanceof Number n) ? n.doubleValue() : defaultValue;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getStringList(String key) {
        Object val = get(key);
        return (val instanceof List) ? (List<String>) val : null;
    }

    @Override
    public boolean contains(String key) {
        return storage.containsKey(key);
    }

    @Override
    public Set<String> getKeys(String path, boolean deep) {
        return storage.keySet();
    }

    @Override
    public void clear() {
        this.storage.clear();
    }

    /**
     * Processes placeholders within the given text using the {@link ReplacementProcessor}.
     *
     * @param text         the string template containing placeholders.
     * @param replacements the variable data to inject into the template.
     * @return a formatted string with all replacements applied.
     */
    @Override
    public String transform(String text, Replacement... replacements) {
        return processor.process(text, replacements);
    }
}