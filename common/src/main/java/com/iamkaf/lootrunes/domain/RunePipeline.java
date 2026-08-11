package com.iamkaf.lootrunes.domain;

import java.util.List;

/** Deterministic composition point for all active rules. */
public final class RunePipeline {
    private final List<RuneDefinition> runes;

    public RunePipeline() {
        this(RuneCatalog.all());
    }

    public RunePipeline(List<RuneDefinition> runes) {
        this.runes = List.copyOf(runes);
    }

    public RunePlan evaluate(RuneProfile profile, RuneKillFacts kill, List<DropSnapshot> naturalDrops) {
        RunePlan.Builder plan = RunePlan.builder();
        RuneEvaluation evaluation = new RuneEvaluation(
                profile.state(),
                kill,
                naturalDrops,
                profile.nextStreak(kill.gameTick()),
                !profile.lastBiomeId().isBlank() && !profile.lastBiomeId().equals(kill.biomeId()),
                !profile.lastWeaponId().isBlank() && !profile.lastWeaponId().equals(kill.weaponId())
        );
        for (RuneDefinition definition : runes) {
            if (profile.isActive(definition.id())) {
                definition.dropRule().apply(evaluation, plan);
            }
        }
        return plan.build();
    }
}
