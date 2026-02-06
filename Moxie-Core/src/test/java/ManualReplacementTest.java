import me.a8kj.config.template.replacement.Replacement;
import me.a8kj.config.template.replacement.ReplacementProcessor;
import me.a8kj.config.template.replacement.impl.PlaceholderProcessor;

import java.util.Arrays;
import java.util.List;

/**
 * A manual test suite designed to verify the functionality of the Moxie replacement engine.
 * This class demonstrates various placeholder processing strategies including percent-based,
 * bracket-based, and bulk list processing.
 */
public class ManualReplacementTest {

    /**
     * Entry point for the manual replacement verification test.
     * * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("=== Starting Manual Replacement Test ===\n");

        /*
         * Testing the Percent Processor strategy (%)
         * Verifies standard key-to-value mapping within a template string.
         */
        ReplacementProcessor percentProcessor = PlaceholderProcessor.percent();
        String welcomeTemplate = "Hello %user%, welcome to %server%! Your ID is %id%.";

        String welcomeResult = percentProcessor.process(welcomeTemplate,
                Replacement.of("user", "A8kj"),
                Replacement.of("server", "A8kj-Network"),
                Replacement.of("id", 77)
        );

        printResult("Percent Processor", welcomeTemplate, welcomeResult);


        /*
         * Testing the Bracket Processor strategy ({})
         * Verifies alternative placeholder syntax handling.
         */
        ReplacementProcessor bracketProcessor = PlaceholderProcessor.brackets();
        String scoreTemplate = "Player {player_name} has reached level {lvl}.";

        String scoreResult = bracketProcessor.process(scoreTemplate,
                Replacement.of("player_name", "A8kj-Dev"),
                Replacement.of("lvl", 99)
        );

        printResult("Bracket Processor", scoreTemplate, scoreResult);


        /*
         * Testing List Processing
         * Verifies the ability to process entire collections of strings (e.g., item lores)
         * in a single operation.
         */
        List<String> loreLines = Arrays.asList(
                "Item: %item_name%",
                "Rarity: %rarity%",
                "Owner: %owner%"
        );

        List<String> processedLore = percentProcessor.process(loreLines,
                Replacement.of("item_name", "Moxie-Core"),
                Replacement.of("rarity", "LEGENDARY"),
                Replacement.of("owner", "A8kj")
        );

        System.out.println("--- Testing List Processing ---");
        System.out.println("Original:  " + loreLines);
        System.out.println("Processed: " + processedLore);
        System.out.println();


        /*
         * Testing Multiple Occurrences
         * Ensures that all instances of a specific placeholder key are replaced correctly
         * throughout the template.
         */
        String repeatTemplate = "Checking: %status%... System is %status%.";
        String repeatResult = percentProcessor.process(repeatTemplate, Replacement.of("status", "READY"));

        printResult("Repeat Test", repeatTemplate, repeatResult);

        System.out.println("=== Test Completed Successfully ===");
    }

    /**
     * Utility method to format and print the results of a specific test case to the console.
     *
     * @param testName  The name of the test scenario being executed.
     * @param original  The raw template string before processing.
     * @param processed The final string after replacements have been applied.
     */
    private static void printResult(String testName, String original, String processed) {
        System.out.println("--- " + testName + " ---");
        System.out.println("Original:  " + original);
        System.out.println("Processed: " + processed);
        System.out.println();
    }
}