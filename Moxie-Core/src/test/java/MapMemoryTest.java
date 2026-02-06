import me.a8kj.config.template.memory.DataMemory;
import me.a8kj.config.template.memory.MemoryDataType;
import me.a8kj.config.template.memory.impl.MapDataMemory;
import me.a8kj.config.template.replacement.Replacement;

/**
 * Functional verification test for {@link MapDataMemory}.
 * This class demonstrates the storage of various data types and the seamless
 * integration of the replacement engine during data retrieval.
 */
public class MapMemoryTest {

    /**
     * Entry point for the memory and template integration test.
     * * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        /*
         * Initialize a new Map-based memory implementation using MemoryDataType as the key.
         */
        DataMemory<MemoryDataType> memory = new MapDataMemory();

        /*
         * Storing data into the memory buffer.
         * The string contains a placeholder to be processed during retrieval.
         */
        memory.set(MemoryDataType.STRING, "Welcome %user%!");
        memory.set(MemoryDataType.INTEGER, 150);

        /*
         * Retrieving data with dynamic placeholder processing.
         * The getString method handles the Replacement logic internally via the
         * Moxie ReplacementProcessor.
         */
        String msg = memory.getString(MemoryDataType.STRING, "Default", Replacement.of("user", "A8kj"));
        int score = memory.getInt(MemoryDataType.INTEGER, 0);

        /*
         * Displaying the final processed results to the console.
         */
        System.out.println(msg);
        System.out.println(score);
    }
}