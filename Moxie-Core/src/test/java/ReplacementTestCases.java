import me.a8kj.config.template.replacement.Replacement;
import me.a8kj.config.template.replacement.ReplacementProcessor;
import me.a8kj.config.template.replacement.impl.PlaceholderProcessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Automated unit tests for the Moxie replacement engine.
 * These tests ensure that various placeholder strategies and edge cases
 * behave consistently across different input scenarios.
 */
class ReplacementTestCases {

    /**
     * Verifies that the percent-based processor correctly identifies and
     * replaces multiple unique placeholders in a standard string.
     */
    @Test
    @DisplayName("Should replace simple percentage placeholders")
    void testSimpleReplacement() {
        ReplacementProcessor processor = PlaceholderProcessor.percent();
        String template = "Hello %user%, welcome to %server%!";

        String result = processor.process(template,
                Replacement.of("user", "A8kj"),
                Replacement.of("server", "A8kj-Network")
        );

        assertEquals("Hello A8kj, welcome to A8kj-Network!", result);
    }

    /**
     * Verifies that the bracket-based processor correctly parses keys enclosed
     * in curly braces and handles numerical values by converting them to strings.
     */
    @Test
    @DisplayName("Should support different bracket types using the same logic")
    void testBracketReplacement() {
        ReplacementProcessor processor = PlaceholderProcessor.brackets();
        String template = "Score: {points}";

        String result = processor.process(template, Replacement.of("points", 100));

        assertEquals("Score: 100", result);
    }

    /**
     * Ensures that the processor can iterate through a collection of strings
     * and apply replacements to each element individually.
     */
    @Test
    @DisplayName("Should handle list processing correctly")
    void testListProcessing() {
        ReplacementProcessor processor = PlaceholderProcessor.percent();
        List<String> lines = List.of(
                "Name: %name%",
                "Rank: %rank%"
        );

        List<String> results = processor.process(lines,
                Replacement.of("name", "A8kj"),
                Replacement.of("rank", "NOOB")
        );

        assertEquals("Name: A8kj", results.get(0));
        assertEquals("Rank: NOOB", results.get(1));
    }

    /**
     * Validates stability under abnormal conditions such as null inputs,
     * null arrays, or templates that contain no matching placeholders.
     */
    @Test
    @DisplayName("Should return original text if replacements are empty or null")
    void testEdgeCases() {
        ReplacementProcessor processor = PlaceholderProcessor.percent();
        String template = "No changes here.";

        assertEquals(template, processor.process(template));
        assertEquals(template, processor.process(template, (Replacement[]) null));
        assertNull(processor.process((String) null, Replacement.of("any", "val")));
    }

    /**
     * Ensures that every occurrence of a placeholder is replaced when the
     * same key appears multiple times within a single template.
     */
    @Test
    @DisplayName("Should handle multiple occurrences of the same key")
    void testMultipleOccurrences() {
        ReplacementProcessor processor = PlaceholderProcessor.percent();
        String template = "%val% + %val% = 2";

        String result = processor.process(template, Replacement.of("val", "1"));

        assertEquals("1 + 1 = 2", result);
    }
}