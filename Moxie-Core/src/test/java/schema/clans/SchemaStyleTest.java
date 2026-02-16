package schema.clans;

import me.a8kj.config.template.memory.DataMemory;
import me.a8kj.config.template.memory.impl.MapPairedDataMemory;
import me.a8kj.config.template.memory.impl.PairedDataMemory;
import me.a8kj.config.template.replacement.Replacement;
import me.a8kj.util.Pair;

/**
 * Integration test demonstrating the "Schema Style" approach.
 * This class validates the interaction between static schemas and the
 * paired memory system, specifically focusing on detailed fetching and
 * context-aware data transformation.
 */
public class SchemaStyleTest {

    /**
     * Entry point for validating schema-based data retrieval.
     * * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        /*
         * 1. Memory Implementation Setup.
         * MapPairedDataMemory allows us to associate typed entries with their raw keys.
         */
        PairedDataMemory<String> memory = new MapPairedDataMemory();
        memory.set("stats.kills", 50);

        /*
         * Detailed Fetching.
         * Using the Entry from the schema to retrieve the full Pair (Key + Value).
         * This is useful for debugging or systems that require the exact configuration path.
         */
        Pair<String, Integer> detailedKills = memory.fetchDetailed(ClanStorageSchema.KILLS);

        System.out.println("Key: " + detailedKills.key());     // Output: stats.kills
        System.out.println("Value: " + detailedKills.value()); // Output: 50
    }

    /**
     * A mock service class demonstrating how business logic consumes the configuration.
     */
    public static class ClanDisplayService {

        /**
         * Renders a clan dashboard by fetching data through the ClanStorageSchema.
         *
         * @param memory     The data source.
         * @param playerName The context for placeholder replacement.
         */
        public void showClanDashboard(DataMemory<String> memory, String playerName) {
            /*
             * Fetching typed data directly from the Schema.
             */
            String rawTag = ClanStorageSchema.CLAN_TAG.fetch(memory);
            int kills = ClanStorageSchema.KILLS.fetch(memory);
            double balance = ClanStorageSchema.BANK_BALANCE.fetch(memory);

            /*
             * Dynamic Transformation.
             * Leveraging the built-in ReplacementProcessor within the memory layer
             * to inject live data into the configuration string.
             */
            String formattedTag = memory.transform(rawTag,
                    Replacement.of("player", playerName),
                    Replacement.of("balance", balance)
            );

            System.out.println("=== Clan Dashboard ===");
            System.out.println("Tag: " + formattedTag);
            System.out.println("Total Kills: " + kills);
            System.out.println("Bank Status: " + (balance > 1000 ? "Rich" : "Poor"));
        }
    }
}