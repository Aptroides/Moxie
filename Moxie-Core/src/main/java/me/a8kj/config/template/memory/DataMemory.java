package me.a8kj.config.template.memory;

import me.a8kj.config.template.replacement.Replacement;
import me.a8kj.config.template.replacement.ReplacementProcessor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Represents the in-memory storage and retrieval system for configuration data.
 * <p>
 * This interface provides a type-safe way to access configuration values and
 * supports dynamic text transformation through replacements via a dedicated processor.
 * </p>
 *
 * @param <K> the type of data keys used (e.g., String for YAML/JSON paths).
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public interface DataMemory<K> {

    /**
     * Sets a value in the memory buffer for the specified key.
     *
     * @param key   the key where the value should be stored.
     * @param value the object to store.
     */
    void set(K key, Object value);

    /**
     * Retrieves a raw object from memory by its key.
     *
     * @param key the key to lookup.
     * @return the stored object, or {@code null} if not found.
     */
    Object get(K key);

    /**
     * Retrieves an object from memory, returning a default value if the key is missing.
     *
     * @param key          the key to lookup.
     * @param defaultValue the value to return if the key does not exist.
     * @return the found value or the default.
     */
    default Object get(K key, Object defaultValue) {
        Object value = get(key);
        return (value != null) ? value : defaultValue;
    }

    /**
     * Retrieves a value as a {@link String}.
     *
     * @param key the key to lookup.
     * @return the string representation of the value.
     */
    String getString(K key);

    /**
     * Retrieves a string and applies dynamic replacements to it.
     *
     * @param key          the key to lookup.
     * @param defaultValue the value to return if not found.
     * @param replacements a varargs of {@link Replacement} to apply to the string.
     * @return the transformed string or the default value.
     */
    default String getString(K key, String defaultValue, Replacement... replacements) {
        String value = getString(key);
        if (value == null) return defaultValue;
        return transform(value, replacements);
    }

    /**
     * Retrieves a value as an integer.
     */
    int getInt(K key, int defaultValue);

    /**
     * Retrieves a value as a boolean.
     */
    boolean getBoolean(K key, boolean defaultValue);

    /**
     * Retrieves a value as a double.
     */
    double getDouble(K key, double defaultValue);

    /**
     * Retrieves a list of strings from the specified key.
     */
    List<String> getStringList(K key);

    /**
     * Checks if the memory contains a specific key.
     */
    boolean contains(K key);

    /**
     * Retrieves a set of keys under a specific path.
     *
     * @param path the parent path.
     * @param deep whether to include keys from nested sections.
     * @return a set of keys.
     */
    Set<K> getKeys(K path, boolean deep);

    /**
     * Clears all data currently held in memory.
     */
    void clear();

    /**
     * Wraps the retrieval of a key in an {@link Optional}.
     *
     * @param key the key to lookup.
     * @return an Optional containing the value if present.
     */
    default Optional<Object> getOptional(K key) {
        return Optional.ofNullable(get(key));
    }

    /**
     * Retrieves a string list and applies dynamic replacements to each entry.
     *
     * @param key          the key to lookup.
     * @param replacements a varargs of {@link Replacement} to apply.
     * @return a processed list of strings.
     */
    default List<String> getStringList(K key, Replacement... replacements) {
        List<String> lines = getStringList(key);
        if (lines == null || lines.isEmpty()) return List.of();
        if (replacements.length == 0) return lines;

        return lines.stream()
                .map(line -> transform(line, replacements))
                .toList();
    }

    /**
     * Provides the {@link ReplacementProcessor} associated with this memory instance.
     * <p>
     * Every implementation must provide its own processor to define how placeholders
     * are identified and replaced.
     * </p>
     *
     * @return the replacement processor instance.
     */
    ReplacementProcessor getProcessor();

    /**
     * Transforms a given text by applying the provided replacements using the internal processor.
     * <p>
     * This is a default implementation that delegates work to {@link #getProcessor()}.
     * </p>
     *
     * @param text         the original text containing placeholders.
     * @param replacements the replacements to apply.
     * @return the processed text.
     */
    default String transform(String text, Replacement... replacements) {
        if (text == null || replacements.length == 0) {
            return text;
        }
        return getProcessor().process(text, replacements);
    }
}