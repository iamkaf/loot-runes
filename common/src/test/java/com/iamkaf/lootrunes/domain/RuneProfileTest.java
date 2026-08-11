package com.iamkaf.lootrunes.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RuneProfileTest {
    @Test
    void startsWithOnlyPlentyUnlocked() {
        RuneProfile profile = new RuneProfile();

        assertEquals(java.util.Set.of(RuneId.PLENTY), profile.unlocked());
        assertEquals(RuneProfile.ActivationResult.ACTIVATED, profile.toggle(RuneId.PLENTY));
        assertEquals(RuneProfile.ActivationResult.LOCKED, profile.toggle(RuneId.SACRIFICE));
    }

    @Test
    void unlockRulesAdvanceFromObservedKills() {
        RuneProfile profile = new RuneProfile();
        for (int kill = 0; kill < 10; kill++) {
            profile.recordKill(
                    new RuneKillFacts("minecraft:mob_" + (kill % 3), "minecraft:biome_" + (kill % 3), "minecraft:weapon_" + (kill % 3), kill * 20L),
                    List.of(new DropSnapshot("minecraft:bone", 1))
            );
        }

        assertTrue(profile.isUnlocked(RuneId.SACRIFICE));
        assertTrue(profile.isUnlocked(RuneId.ECHOES));
        assertTrue(profile.isUnlocked(RuneId.ASCENDANCE));
        assertTrue(profile.isUnlocked(RuneId.MIGRATION));
        assertTrue(profile.isUnlocked(RuneId.IMPROVISATION));
    }

    @Test
    void activationNeverExceedsThreeAndCanFreeASlot() {
        RuneProfile profile = fullyUnlockedProfile();

        assertEquals(RuneProfile.ActivationResult.ACTIVATED, profile.toggle(RuneId.PLENTY));
        assertEquals(RuneProfile.ActivationResult.ACTIVATED, profile.toggle(RuneId.SACRIFICE));
        assertEquals(RuneProfile.ActivationResult.ACTIVATED, profile.toggle(RuneId.ECHOES));
        assertEquals(RuneProfile.ActivationResult.FULL, profile.toggle(RuneId.MIGRATION));
        assertEquals(RuneProfile.ActivationResult.DEACTIVATED, profile.toggle(RuneId.SACRIFICE));
        assertEquals(RuneProfile.ActivationResult.ACTIVATED, profile.toggle(RuneId.MIGRATION));
        assertEquals(3, profile.active().size());
    }

    @Test
    void aLongPauseResetsTheStreak() {
        RuneProfile profile = new RuneProfile();
        profile.recordKill(new RuneKillFacts("minecraft:zombie", "minecraft:plains", "minecraft:sword", 20), List.of());

        assertEquals(2, profile.nextStreak(40));
        assertEquals(1, profile.nextStreak(20 + RuneProfile.STREAK_WINDOW_TICKS + 1));
    }

    @Test
    void stateRoundTripPreservesProgressAndActivationOrder() {
        RuneProfile original = fullyUnlockedProfile();
        original.toggle(RuneId.ECHOES);
        original.toggle(RuneId.PLENTY);
        original.recordKill(
                new RuneKillFacts("minecraft:zombie", "minecraft:plains", "minecraft:sword", 120),
                List.of(new DropSnapshot("minecraft:rotten_flesh", 2))
        );

        RuneProfile restored = new RuneProfile(original.state());

        assertEquals(original.state(), restored.state());
        assertEquals(List.of(RuneId.ECHOES, RuneId.PLENTY), restored.active());
    }

    @Test
    void restoredStateRejectsLockedAndExcessActiveRunes() {
        RuneProfile profile = new RuneProfile(new RuneProfileState(
                Set.of(RuneId.PLENTY, RuneId.ECHOES, RuneId.MIGRATION),
                List.of(RuneId.PLENTY, RuneId.SACRIFICE, RuneId.ECHOES, RuneId.MIGRATION),
                -4,
                new RuneProfileState.Streak(-2, -1),
                RuneProfileState.LastKill.NONE,
                Set.of(),
                Set.of(),
                Set.of(),
                Optional.empty()
        ));

        assertEquals(List.of(RuneId.PLENTY, RuneId.ECHOES, RuneId.MIGRATION), profile.active());
        assertEquals(0, profile.totalKills());
        assertEquals(0, profile.currentStreak());
    }

    private static RuneProfile fullyUnlockedProfile() {
        return new RuneProfile(new RuneProfileState(
                Set.copyOf(Arrays.asList(RuneId.values())),
                List.of(),
                10,
                new RuneProfileState.Streak(5, 5),
                new RuneProfileState.LastKill(100, "a", "a", "a"),
                Set.of("a", "b", "c"),
                Set.of("a", "b", "c"),
                Set.of("a", "b", "c"),
                Optional.empty()
        ));
    }
}
