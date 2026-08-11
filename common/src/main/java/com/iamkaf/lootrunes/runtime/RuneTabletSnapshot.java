package com.iamkaf.lootrunes.runtime;

import com.iamkaf.lootrunes.domain.RuneCatalog;
import com.iamkaf.lootrunes.domain.RuneDefinition;
import com.iamkaf.lootrunes.domain.RuneId;
import com.iamkaf.lootrunes.domain.RuneProfile;

import java.util.List;

public record RuneTabletSnapshot(List<Entry> runes, int activeCount, int activeLimit) {
    public RuneTabletSnapshot {
        runes = List.copyOf(runes);
    }

    public static RuneTabletSnapshot from(RuneProfile profile) {
        List<Entry> entries = RuneCatalog.all().stream().map(definition -> entry(profile, definition)).toList();
        return new RuneTabletSnapshot(entries, profile.active().size(), RuneProfile.MAX_ACTIVE);
    }

    private static Entry entry(RuneProfile profile, RuneDefinition definition) {
        return new Entry(
                definition.id(),
                profile.isUnlocked(definition.id()),
                profile.isActive(definition.id()),
                definition.unlockRule().progress(profile),
                definition.unlockRule().target()
        );
    }

    public Entry entry(RuneId id) {
        return runes.stream().filter(entry -> entry.id() == id).findFirst().orElseThrow();
    }

    public record Entry(RuneId id, boolean unlocked, boolean active, int progress, int target) {
    }
}
