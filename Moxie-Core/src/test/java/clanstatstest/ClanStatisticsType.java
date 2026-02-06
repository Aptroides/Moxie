package clanstatstest;

/**
 * Defines the available statistical categories for the Clan system.
 * These constants serve as the type-safe keys for the {@link ClanMemory} buffer.
 */
public enum ClanStatisticsType {

    /**
     * Total number of confirmed player kills attributed to the clan.
     */
    KILLS,

    /**
     * Total number of times clan members have fallen in combat.
     */
    DEATHS,

    /**
     * The competitive ranking score, often used for clan leaderboards.
     */
    POINTS;
}