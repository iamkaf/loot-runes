package com.iamkaf.lootrunes.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        RuneProfile profile = profile(List.of(RuneId.ASCENDANCE), new RuneProfileState.Streak(8, 8), 100);

        assertEquals(3, pipeline.evaluate(profile, kill(120, "plains", "sword"), List.of()).bonusRolls());
        assertEquals(0, pipeline.evaluate(profile, kill(100 + RuneProfile.STREAK_WINDOW_TICKS + 1L, "plains", "sword"), List.of()).bonusRolls());
    }

    private static RuneKillFacts kill(long tick, String biome, String weapon) {
        return new RuneKillFacts("minecraft:zombie", "minecraft:" + biome, "minecraft:" + weapon, tick);
    }

    private static RuneProfile unlockedWith(RuneId... active) {
        return profile(List.of(active), new RuneProfileState.Streak(5, 0), Long.MIN_VALUE);
    }

    private static RuneProfile profile(List<RuneId> active, RuneProfileState.Streak streak, long lastKillTick) {
        return new RuneProfile(new RuneProfileState(
                Set.copyOf(Arrays.asList(RuneId.values())),
                active,
                10,
                streak,
                new RuneProfileState.LastKill(lastKillTick, "a", "a", "a"),
                Set.of("a", "b", "c"),
                Set.of("a", "b", "c"),
                Set.of("a", "b", "c"),
                Optional.empty()
        ));
    }

    @Test
    void pipelineCanUseAPlaytestSpecificRoster() {
        RuneDefinition replacement = new RuneDefinition(
                RuneId.PLENTY,
                "test.name",
                "test.description",
                "test.unlock",
                new RuneUnlockRule() {
                    @Override
                    public int progress(RuneProfileState profile) {
                        return 1;
                    }

                    @Override
                    public int target() {
                        return 1;
                    }
                },
                (evaluation, plan) -> plan.addBonusRolls(4)
        );
        RuneProfile profile = unlockedWith(RuneId.PLENTY);

        RunePlan plan = new RunePipeline(List.of(replacement)).evaluate(profile, kill(20, "plains", "sword"), List.of());

        assertEquals(4, plan.bonusRolls());
    }
}
