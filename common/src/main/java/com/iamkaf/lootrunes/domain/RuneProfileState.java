package com.iamkaf.lootrunes.domain;

import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/** Immutable save boundary for a rune profile. */
public record RuneProfileState(
        Set<RuneId> unlocked,
        List<RuneId> active,
        int totalKills,
        Streak streak,
        LastKill lastKill,
        Set<String> seenMobs,
        Set<String> seenBiomes,
        Set<String> seenWeapons,
        Optional<DropSnapshot> rememberedDrop
) {
    public RuneProfileState {
        EnumSet<RuneId> sanitizedUnlocked = sanitizeRunes(unlocked);
        sanitizedUnlocked.add(RuneId.PLENTY);
        unlocked = Set.copyOf(sanitizedUnlocked);
        Set<RuneId> availableRunes = unlocked;
        active = active == null
                ? List.of()
                : active.stream()
                        .filter(Objects::nonNull)
                        .distinct()
                        .filter(availableRunes::contains)
                        .limit(RuneProfile.MAX_ACTIVE)
                        .toList();
        totalKills = Math.max(0, totalKills);
        streak = Objects.requireNonNullElse(streak, Streak.EMPTY);
        lastKill = Objects.requireNonNullElse(lastKill, LastKill.NONE);
        seenMobs = sanitizeStrings(seenMobs);
        seenBiomes = sanitizeStrings(seenBiomes);
        seenWeapons = sanitizeStrings(seenWeapons);
        rememberedDrop = rememberedDrop == null
                ? Optional.empty()
                : rememberedDrop.filter(drop -> !drop.isEmpty());
    }

    public static RuneProfileState initial() {
        return new RuneProfileState(
                Set.of(RuneId.PLENTY),
                List.of(),
                0,
                Streak.EMPTY,
                LastKill.NONE,
                Set.of(),
                Set.of(),
                Set.of(),
                Optional.empty()
        );
    }

    private static EnumSet<RuneId> sanitizeRunes(Set<RuneId> source) {
        EnumSet<RuneId> result = EnumSet.noneOf(RuneId.class);
        if (source != null) {
            source.stream().filter(Objects::nonNull).forEach(result::add);
        }
        return result;
    }

    private static Set<String> sanitizeStrings(Set<String> source) {
        if (source == null) {
            return Set.of();
        }
        LinkedHashSet<String> result = new LinkedHashSet<>();
        source.stream()
                .filter(Objects::nonNull)
                .filter(value -> !value.isBlank())
                .forEach(result::add);
        return Set.copyOf(result);
    }

    public record Streak(int best, int current) {
        public static final Streak EMPTY = new Streak(0, 0);

        public Streak {
            best = Math.max(0, best);
            current = Math.max(0, current);
            best = Math.max(best, current);
        }
    }

    public record LastKill(long gameTick, String mobId, String biomeId, String weaponId) {
        public static final LastKill NONE = new LastKill(Long.MIN_VALUE, "", "", "");

        public LastKill {
            mobId = Objects.requireNonNullElse(mobId, "");
            biomeId = Objects.requireNonNullElse(biomeId, "");
            weaponId = Objects.requireNonNullElse(weaponId, "");
        }
    }
}
