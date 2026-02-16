package schema.general;

import me.a8kj.config.template.memory.MemoryDataType;
import me.a8kj.config.template.memory.MemoryEntry;

import java.util.List;

/**
 * Defines the static schema for server configurations.
 * This class serves as a centralized registry of {@link MemoryEntry} definitions,
 * ensuring type safety and key consistency throughout the application.
 */
public final class ServerConfigSchema {

    /*
     * Network and Connection Settings
     */
    public static final MemoryEntry<String> SERVER_IP =
            MemoryEntry.of("network.server-ip", MemoryDataType.STRING);
    public static final MemoryEntry<Integer> MAX_PLAYERS =
            MemoryEntry.of("network.max-players", MemoryDataType.INTEGER);
    /*
     * Messaging and Localization Settings
     * Supports dynamic placeholder replacement during retrieval.
     */
    public static final MemoryEntry<String> JOIN_MESSAGE =
            MemoryEntry.of("messages.join-format", MemoryDataType.STRING);
    /*
     * Collection-based Settings
     * Used for multi-line configurations such as item lores or server rules.
     */
    public static final MemoryEntry<List<String>> SERVER_RULES =
            MemoryEntry.of("information.rules", MemoryDataType.STRING_LIST);
    /*
     * Feature Toggles and Security Flags
     */
    public static final MemoryEntry<Boolean> MAINTENANCE_MODE =
            MemoryEntry.of("security.maintenance", MemoryDataType.BOOLEAN);

    private ServerConfigSchema() {
    }
}