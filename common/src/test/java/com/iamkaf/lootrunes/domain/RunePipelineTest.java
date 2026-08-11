package com.iamkaf.lootrunes.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RunePipelineTest {
    private final RunePipeline pipeline = new RunePipeline();

    @Test
    void plentyAddsOneRollWithoutReplacingNaturalDrops() {
        RuneProfile profile = unlockedWith(RuneId.PLENTY);

        RunePlan plan = pipeline.evaluate(profile, kill(20, "plains", "sword"), List.of(new DropSnapshot("minecraft:bone", 2)));

        assertTrue(plan.keepNaturalDrops());
        assertEquals(1, plan.bonusRolls());
        assertTrue(plan.echoedDrop().isEmpty());
    }

    @Test
    void sacrificeAndPlentyComposeInCatalogOrder() {
        RuneProfile profile = unlockedWith(RuneId.PLENTY, RuneId.SACRIFICE);

        RunePlan plan = pipeline.evaluate(profile, kill(20, "plains", "sword"), List.of(new DropSnapshot("minecraft:bone", 2)));

        assertFalse(plan.keepNaturalDrops());
        assertEquals(3, plan.bonusRolls());
    }

    @Test
    void echoesUsesThePreviousKillRatherThanTheCurrentOne() {
        RuneProfile profile = unlockedWith(RuneId.ECHOES);
        profile.recordKill(kill(20, "plains", "sword"), List.of(new DropSnapshot("minecraft:bone", 2)));

        RunePlan plan = pipeline.evaluate(profile, kill(40, "forest", "axe"), List.of(new DropSnapshot("minecraft:string", 1)));

        assertEquals(new DropSnapshot("minecraft:bone", 2), plan.echoedDrop().orElseThrow());
    }

    @Test
    void migrationAndImprovisationRewardActualChangesOnly() {
        RuneProfile profile = unlockedWith(RuneId.MIGRATION, RuneId.IMPROVISATION);
        profile.recordKill(kill(20, "plains", "sword"), List.of());

        assertEquals(0, pipeline.evaluate(profile, kill(40, "plains", "sword"), List.of()).bonusRolls());
        assertEquals(2, pipeline.evaluate(profile, kill(40, "forest", "axe"), List.of()).bonusRolls());
    }

    @Test
    void ascendanceScalesWithTheUpcomingStreakAndCapsAtThreeRolls() {
        RuneProfile profile = unlockedWith(RuneId.ASCENDANCE);
        profile.restore(
                List.of(RuneId.values()),
                List.of(RuneId.ASCENDANCE),
                10,
                8,
                8,
                100,
                List.of("a", "b", "c"),
                List.of("a", "b", "c"),
                List.of("a", "b", "c"),
                "a", "a", "a", null
        );

        assertEquals(3, pipeline.evaluate(profile, kill(120, "plains", "sword"), List.of()).bonusRolls());
        assertEquals(0, pipeline.evaluate(profile, kill(100 + RuneProfile.STREAK_WINDOW_TICKS + 1L, "plains", "sword"), List.of()).bonusRolls());
    }

    private static RuneKillFacts kill(long tick, String biome, String weapon) {
        return new RuneKillFacts("minecraft:zombie", "minecraft:" + biome, "minecraft:" + weapon, tick);
    }

    private static RuneProfile unlockedWith(RuneId... active) {
        RuneProfile profile = new RuneProfile();
        profile.restore(
                List.of(RuneId.values()),
                List.of(active),
                10,
                5,
                0,
                Long.MIN_VALUE,
                List.of("a", "b", "c"),
                List.of("a", "b", "c"),
                List.of("a", "b", "c"),
                "", "", "", null
        );
        return profile;
    }
}
