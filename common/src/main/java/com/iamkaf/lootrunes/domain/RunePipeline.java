package com.iamkaf.lootrunes.domain;

import java.util.List;

/** Deterministic composition point for all active rules. */
public final class RunePipeline {
    public RunePlan evaluate(RuneProfile profile, RuneKillFacts kill, List<DropSnapshot> naturalDrops) {
        RunePlan.Builder plan = RunePlan.builder();
        RuneDropRule.RuneRuleContext context = new RuneDropRule.RuneRuleContext(
                profile,
                kill,
                naturalDrops,
                profile.nextStreak(kill.gameTick()),
                !profile.lastBiomeId().isBlank() && !profile.lastBiomeId().equals(kill.biomeId()),
                !profile.lastWeaponId().isBlank() && !profile.lastWeaponId().equals(kill.weaponId())
        );
        for (RuneDefinition definition : RuneCatalog.all()) {
            if (profile.isActive(definition.id())) {
                definition.dropRule().apply(context, plan);
            }
        }
        return plan.build();
    }
}
