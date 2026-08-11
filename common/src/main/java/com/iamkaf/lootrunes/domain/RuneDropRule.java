package com.iamkaf.lootrunes.domain;

import java.util.List;

/** One isolated rule contribution. Rules describe intent; the Minecraft adapter performs it. */
@FunctionalInterface
public interface RuneDropRule {
    void apply(RuneRuleContext context, RunePlan.Builder plan);

    record RuneRuleContext(
            RuneProfile profile,
            RuneKillFacts kill,
            List<DropSnapshot> naturalDrops,
            int nextStreak,
            boolean changedBiome,
            boolean changedWeapon
    ) {
        public RuneRuleContext {
            naturalDrops = List.copyOf(naturalDrops);
        }
    }
}
