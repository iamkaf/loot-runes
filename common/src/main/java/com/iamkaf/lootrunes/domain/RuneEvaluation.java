package com.iamkaf.lootrunes.domain;

import java.util.List;
import java.util.Objects;

/** Immutable inputs presented to one rune rule during a drop evaluation. */
public record RuneEvaluation(
        RuneProfileState profile,
        RuneKillFacts kill,
        List<DropSnapshot> naturalDrops,
        int nextStreak,
        boolean changedBiome,
        boolean changedWeapon
) {
    public RuneEvaluation {
        Objects.requireNonNull(profile, "profile");
        Objects.requireNonNull(kill, "kill");
        naturalDrops = List.copyOf(naturalDrops);
        nextStreak = Math.max(1, nextStreak);
    }
}
