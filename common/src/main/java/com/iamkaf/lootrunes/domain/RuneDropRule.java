package com.iamkaf.lootrunes.domain;

/** One isolated rule contribution. Rules describe intent; the Minecraft adapter performs it. */
@FunctionalInterface
public interface RuneDropRule {
    void apply(RuneEvaluation evaluation, RunePlan.Builder plan);
}
