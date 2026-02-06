package me.a8kj.config.template.replacement;

import java.util.Collection;

/**
 * A simple data carrier (Record) representing a placeholder and its replacement value.
 * This is used during text transformation to map specific keys to dynamic content.
 *
 * @param key   the placeholder string (e.g., "player", "amount").
 * @param value the content that will replace the placeholder.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public record Replacement(String key, String value) {

    /**
     * Creates a replacement from a raw object.
     * The object is converted to a string using {@link String#valueOf(Object)}.
     *
     * @param key   the placeholder key.
     * @param value the object value.
     * @return a new {@link Replacement} instance.
     */
    public static Replacement of(String key, Object value) {
        return new Replacement(key, String.valueOf(value));
    }

    /**
     * Creates a replacement from a collection of strings.
     * The elements are joined together using a newline character ({@code \n}).
     * Useful for multi-line replacements like lists or messages.
     *
     * @param key        the placeholder key.
     * @param collection the collection of strings to join.
     * @return a new {@link Replacement} instance.
     */
    public static Replacement of(String key, Collection<String> collection) {
        return new Replacement(key, String.join("\n", collection));
    }
}