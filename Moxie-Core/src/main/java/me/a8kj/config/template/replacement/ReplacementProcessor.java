package me.a8kj.config.template.replacement;

import java.util.List;

/**
 * Defines the contract for processing text and injecting dynamic data.
 * This interface is responsible for taking a template (String or List of Strings)
 * and applying a series of {@link Replacement} objects to produce the final output.
 *
 * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
 * @since 0.1
 */
public interface ReplacementProcessor {

    /**
     * Processes a single string by applying the provided replacements.
     *
     * @param text         the raw text containing placeholders.
     * @param replacements a varargs of {@link Replacement} to be applied.
     * @return the processed string with all placeholders resolved.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    String process(String text, Replacement... replacements);

    /**
     * Processes a list of strings (e.g., a configuration's lore or multi-line message).
     * This default implementation uses Java Streams to map each line through the
     * {@link #process(String, Replacement...)} method.
     *
     * @param lines        the list of template lines.
     * @param replacements a varargs of {@link Replacement} to be applied to every line.
     * @return a new list containing the processed strings.
     * @author <a href="https://github.com/a8kj7sea">a8kj7sea</a>
     * @since 0.1
     */
    default List<String> process(List<String> lines, Replacement... replacements) {
        return lines.stream().map(line -> process(line, replacements)).toList();
    }
}