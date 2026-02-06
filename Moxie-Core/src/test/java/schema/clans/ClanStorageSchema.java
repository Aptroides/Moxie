package schema.clans;

import me.a8kj.config.template.memory.MemoryDataType;
import me.a8kj.config.template.memory.MemoryEntry;

/**
 * Defines the data schema for Clan-related storage.
 * This schema maps technical paths to specific data types, ensuring
 * that clan statistics, display information, and membership data
 * are handled with strict type safety across the Moxie engine.
 */
public final class ClanStorageSchema {

    private ClanStorageSchema() {}

    /*
     * Numeric statistics suitable for arithmetic operations.
     */
    public static final MemoryEntry<Integer> KILLS =
            MemoryEntry.of("stats.kills", MemoryDataType.INTEGER);

    public static final MemoryEntry<Integer> DEATHS =
            MemoryEntry.of("stats.deaths", MemoryDataType.INTEGER);

    /*
     * General display and economic information.
     */
    public static final MemoryEntry<String> CLAN_TAG =
            MemoryEntry.of("display.tag", MemoryDataType.STRING);

    public static final MemoryEntry<Double> BANK_BALANCE =
            MemoryEntry.of("economy.balance", MemoryDataType.DOUBLE);

    /*
     * Collection-based storage for membership tracking.
     */
    public static final MemoryEntry<java.util.List<String>> MEMBERS =
            MemoryEntry.of("members.list", MemoryDataType.STRING_LIST);
}