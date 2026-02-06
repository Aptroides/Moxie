package me.a8kj.config.template.replacement.impl;

import me.a8kj.config.template.replacement.Replacement;
import me.a8kj.config.template.replacement.ReplacementProcessor;

/**
 * A highly customizable implementation of {@link ReplacementProcessor} that wraps keys
 * with specified prefixes and suffixes.
 * This record-based implementation allows for quick creation of different placeholder
 * styles (e.g., %key%, {key}, <key>).
 *
 * @param prefix the character(s) placed before the key.
 * @param suffix the character(s) placed after the key.
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public record PlaceholderProcessor(String prefix, String suffix) implements ReplacementProcessor {

    /**
     * Creates a processor for percent-style placeholders: {@code %key%}.
     */
    public static PlaceholderProcessor percent() {
        return new PlaceholderProcessor("%", "%");
    }

    /**
     * Creates a processor for bracket-style placeholders: {@code {key}}.
     */
    public static PlaceholderProcessor brackets() {
        return new PlaceholderProcessor("{", "}");
    }

    /**
     * Creates a processor for chevron-style placeholders: {@code <key>}.
     */
    public static PlaceholderProcessor chevron() {
        return new PlaceholderProcessor("<", ">");
    }

    /**
     * Replaces all identified placeholders in the text with their corresponding values.
     * The method constructs the target string using {@code prefix + key + suffix}
     * and performs a literal string replacement.
     *
     * @param text         the template string.
     * @param replacements the data mappings to apply.
     * @return the processed string, or the original text if no replacements are provided.
     */
    @Override
    public String process(String text, Replacement... replacements) {
        if (text == null || replacements == null || replacements.length == 0) {
            return text;
        }

        String result = text;
        for (Replacement replacement : replacements) {
            String target = prefix + replacement.key() + suffix;
            result = result.replace(target, replacement.value());
        }
        return result;
    }
}