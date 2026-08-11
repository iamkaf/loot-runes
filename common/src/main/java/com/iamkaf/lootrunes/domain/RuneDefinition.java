package com.iamkaf.lootrunes.domain;

/** Data and replaceable behavior for a rune. */
public record RuneDefinition(
        RuneId id,
        String nameKey,
        String descriptionKey,
        String unlockKey,
        RuneUnlockRule unlockRule,
        RuneDropRule dropRule
) {
}
