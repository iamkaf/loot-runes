package com.iamkaf.lootrunes.runtime;

import com.iamkaf.lootrunes.domain.RuneCatalog;
import com.iamkaf.lootrunes.domain.RuneDefinition;
import com.iamkaf.lootrunes.domain.RuneId;
import com.iamkaf.lootrunes.domain.RuneProfile;
import com.iamkaf.lootrunes.domain.RuneProfileState;

import java.util.List;

public record RuneTabletSnapshot(List<Entry> runes, int activeCount, int activeLimit) {
    public RuneTabletSnapshot {
        runes = List.copyOf(runes);
        activeLimit = Math.max(0, Math.min(activeLimit, RuneProfile.MAX_ACTIVE));
        activeCount = Math.max(0, Math.min(activeCount, activeLimit));
    }

    public static RuneTabletSnapshot from(RuneProfile profile) {
        RuneProfileState state = profile.state();
        List<Entry> entries = RuneCatalog.all().stream().map(definition -> entry(state, definition)).toList();
        return new RuneTabletSnapshot(entries, state.active().size(), RuneProfile.MAX_ACTIVE);
    }

    private static Entry entry(RuneProfileState profile, RuneDefinition definition) {
        return new Entry(
                definition.id(),
                profile.unlocked().contains(definition.id()),
                profile.active().contains(definition.id()),
                definition.unlockRule().progress(profile),
                definition.unlockRule().target()
        );
    }

    public record Entry(RuneId id, boolean unlocked, boolean active, int progress, int target) {
    }
}
