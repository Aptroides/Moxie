package schema.general;

import me.a8kj.config.template.memory.impl.MapPairedDataMemory;
import me.a8kj.config.template.memory.impl.PairedDataMemory;
import me.a8kj.config.template.replacement.Replacement;
import me.a8kj.util.Pair;

import java.util.Arrays;

/**
 * High-level integration test demonstrating the synergy between schemas and paired memory.
 * This class simulates a real-world scenario where a server is initialized using
 * data retrieved from a structured memory buffer via predefined schema keys.
 */
public class GeneralUsageTest {

    /**
     * Entry point for the general usage demonstration.
     * * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        /*
         * 1. Initialize PairedDataMemory.
         * This implementation supports mapping String keys to diverse data types
         * while maintaining the relationship required for detailed fetching.
         */
        PairedDataMemory<String> config = new MapPairedDataMemory();

        /*
         * 2. Simulating Configuration Loading.
         * In a production environment, this data would be populated by a FileOperation (read).
         */
        System.out.println("--- Simulating Config Loading ---");
        config.set("network.server-ip", "192.168.1.1");
        config.set("network.max-players", 50);
        config.set("security.maintenance", true);
        config.set("messages.join-format", "Welcome %player% to our awesome server!");
        config.set("information.rules", Arrays.asList("Don't hack", "Be nice", "Have fun"));

        /*
         * 3. Triggering the server initialization logic.
         */
        initializeServer(config);

        /*
         * 4. Testing dynamic content transformation and list retrieval.
         */
        System.out.println("\n--- Testing Replacement & Lists ---");

        String joinMsg = ServerConfigSchema.JOIN_MESSAGE.fetch(config);

        /*
         * Demonstrates the transformation of a retrieved template string
         * using the Moxie replacement engine.
         */
        System.out.println("Formatted Message: " + config.transform(joinMsg, Replacement.of("player", "A8kj")));

        System.out.println("Rules:");
        ServerConfigSchema.SERVER_RULES.fetch(config).forEach(rule -> System.out.println(" - " + rule));
    }

    /**
     * Simulates server initialization by fetching values through the {@link ServerConfigSchema}.
     * * @param config The memory buffer to fetch data from.
     */
    public static void initializeServer(PairedDataMemory<String> config) {
        System.out.println("\n--- Initializing Server ---");

        /*
         * Direct fetching using the Schema constants.
         * This ensures type safety and eliminates the risk of "Magic Strings" in the logic.
         */
        String ip = ServerConfigSchema.SERVER_IP.fetch(config);
        int limit = ServerConfigSchema.MAX_PLAYERS.fetch(config);

        System.out.println("Target IP: " + ip);
        System.out.println("Player Limit: " + limit);

        if (ServerConfigSchema.MAINTENANCE_MODE.fetch(config)) {
            System.out.println("Status: [MAINTENANCE MODE ACTIVE]");
        }

        /*
         * Utilizing Detailed Fetching.
         * Demonstrates the power of PairedDataMemory by returning both the key and the value,
         * which is highly useful for debugging and internal logging.
         */
        Pair<String, String> detailedIp = config.fetchDetailed(ServerConfigSchema.SERVER_IP);
        System.out.println("[LOG] Successfully loaded " + detailedIp.key() + " with value: " + detailedIp.value());
    }
}