package com.iamkaf.lootrunes.domain;

/** A replaceable prototype unlock rule. */
public interface RuneUnlockRule {
    int progress(RuneProfileState profile);

    int target();

    default boolean isSatisfied(RuneProfileState profile) {
        return progress(profile) >= target();
    }
}
