package com.iamkaf.lootrunes.domain;

/** A replaceable prototype unlock rule. */
public interface RuneUnlockRule {
    int progress(RuneProfile profile);

    int target();

    default boolean isSatisfied(RuneProfile profile) {
        return progress(profile) >= target();
    }
}
