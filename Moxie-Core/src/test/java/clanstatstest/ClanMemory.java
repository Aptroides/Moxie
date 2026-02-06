package clanstatstest;

import me.a8kj.config.template.memory.impl.EnumDataMemory;

/**
 * A specialized implementation of {@link EnumDataMemory} tailored for Clan statistics.
 * This class bridges the gap between the Moxie memory system and clan-specific business logic,
 * ensuring that all statistics are managed through a type-safe Enum structure.
 */
public class ClanMemory extends EnumDataMemory<ClanStatisticsType> implements IClanStatistics {

    /**
     * Initializes the clan memory buffer using the {@link ClanStatisticsType} enum class.
     */
    public ClanMemory() {
        super(ClanStatisticsType.class);
    }

    /**
     * Retrieves a specific numerical statistic for the clan.
     *
     * @param type The statistical category to fetch.
     * @return The current value, or 0 if the statistic has not been initialized.
     */
    @Override
    public int getStatistic(ClanStatisticsType type) {
        /*
         * Leverages the parent EnumDataMemory integer retrieval logic
         * with a default fallback value.
         */
        return getInt(type, 0);
    }

    /**
     * Updates or initializes a specific clan statistic.
     *
     * @param type  The statistical category to update.
     * @param value The new numerical value to store.
     */
    @Override
    public void setStatistic(ClanStatisticsType type, int value) {
        /*
         * Utilizes the base memory storage mechanism.
         */
        set(type, value);
    }

    /**
     * Populates the memory buffer with default values for a newly created clan.
     * Sets starting points while resetting all other statistics to zero.
     */
    @Override
    public void setupNewClan() {
        /*
         * Iterates through all available enum constants to ensure the memory
         * buffer is fully initialized, preventing null-pointer risks.
         */
        for (ClanStatisticsType type : ClanStatisticsType.values()) {
            if (type == ClanStatisticsType.POINTS) {
                setStatistic(type, 100);
            } else {
                setStatistic(type, 0);
            }
        }
    }
}