package clanstatstest;

/**
 * Defines the contract for managing Clan-related numerical statistics.
 * This interface utilizes Java 8+ default methods to encapsulate common arithmetic logic,
 * ensuring that data manipulation is consistent across any implementation.
 */
public interface IClanStatistics {

    /**
     * Retrieves the raw integer value associated with a specific statistic type.
     *
     * @param type The statistical category to query.
     * @return The current value of the statistic.
     */
    int getStatistic(ClanStatisticsType type);

    /**
     * Updates the raw integer value for a specific statistic type.
     *
     * @param type  The statistical category to update.
     * @param value The new value to set.
     */
    void setStatistic(ClanStatisticsType type, int value);

    /**
     * Increments a statistic by a specified amount.
     * Uses Math.abs to prevent accidental subtraction via negative inputs.
     *
     * @param type   The statistical category to increase.
     * @param amount The value to add.
     */
    default void addStatistic(ClanStatisticsType type, int amount) {
        setStatistic(type, getStatistic(type) + Math.abs(amount));
    }

    /**
     * Decrements a statistic by a specified amount, ensuring the result
     * never drops below zero.
     *
     * @param type   The statistical category to decrease.
     * @param amount The value to subtract.
     */
    default void removeStatistic(ClanStatisticsType type, int amount) {
        int current = getStatistic(type);
        setStatistic(type, Math.max(0, current - Math.abs(amount)));
    }

    /**
     * Convenience method to retrieve the clan's competitive points.
     *
     * @return The current points value.
     */
    default int getPoints() {
        return getStatistic(ClanStatisticsType.POINTS);
    }

    /**
     * Initializes the statistics for a brand-new clan with default starting values.
     */
    void setupNewClan();
}