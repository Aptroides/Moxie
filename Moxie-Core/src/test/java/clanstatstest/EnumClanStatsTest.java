package clanstatstest;

/**
 * Integration test for the Enum-based Clan statistics system.
 * This class demonstrates the "Hybrid Logic" approach—combining the raw storage
 * power of Moxie's {@link me.a8kj.config.template.memory.impl.EnumDataMemory}
 * with domain-specific business rules defined in {@link IClanStatistics}.
 */
public class EnumClanStatsTest {

    /**
     * Executes a series of operations to validate statistical tracking,
     * arithmetic logic, and boundary safety.
     * * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        /*
         * 1. Abstraction Usage.
         * We reference the implementation via the interface (IClanStatistics).
         * This follows the Dependency Inversion Principle, making the code
         * easier to swap or mock later.
         */
        IClanStatistics clanStats = new ClanMemory();

        System.out.println("--- Testing Hybrid Logic ---");

        /*
         * 2. Initialization logic.
         * setupNewClan() ensures all enum keys are populated in memory
         * with their specific starting values.
         */
        clanStats.setupNewClan();
        System.out.println("Default Points: " + clanStats.getPoints());

        /*
         * 3. Additive Operations.
         * Testing the ability to increment statistics without manual
         * get-then-set boilerplate.
         */
        clanStats.addStatistic(ClanStatisticsType.KILLS, 5);
        clanStats.addStatistic(ClanStatisticsType.POINTS, 20);

        System.out.println("Points after bonus: " + clanStats.getPoints());
        System.out.println("Kills: " + clanStats.getStatistic(ClanStatisticsType.KILLS));

        /*
         * 4. Boundary Testing.
         * Testing the subtractive logic to ensure points do not drop below
         * a logical floor (0), preventing "negative points" bugs.
         */
        clanStats.removeStatistic(ClanStatisticsType.POINTS, 500);
        System.out.println("Points after huge penalty: " + clanStats.getPoints());
    }
}