package com.iamkaf.lootrunes.domain;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

/** Converts the original flat save shape into the typed profile boundary. */
public final class LegacyRuneProfileMigration {
    private LegacyRuneProfileMigration() {
    }

    public static Map<UUID, RuneProfileState> decode(Map<String, List<String>> source) {
        Map<UUID, Map<String, List<String>>> grouped = new TreeMap<>();
        source.forEach((key, value) -> {
            int separator = key.indexOf('/');
            if (separator <= 0 || separator == key.length() - 1) {
                return;
            }
            try {
                UUID playerId = UUID.fromString(key.substring(0, separator));
                grouped.computeIfAbsent(playerId, ignored -> new HashMap<>())
                        .put(key.substring(separator + 1), value);
            } catch (IllegalArgumentException ignored) {
                // Unknown keys were tolerated by the original format and remain safe to skip.
            }
        });

        Map<UUID, RuneProfileState> profiles = new TreeMap<>();
        grouped.forEach((playerId, fields) -> profiles.put(playerId, decodeProfile(fields)));
        return Map.copyOf(profiles);
    }

    private static RuneProfileState decodeProfile(Map<String, List<String>> fields) {
        List<String> streak = fields.getOrDefault("streak", List.of());
        List<String> last = fields.getOrDefault("last", List.of());
        List<String> remembered = fields.getOrDefault("remembered", List.of());
        Optional<DropSnapshot> rememberedDrop = remembered.size() >= 2
                ? Optional.of(new DropSnapshot(remembered.get(0), parseInt(remembered.get(1), 0)))
                : Optional.empty();
        return new RuneProfileState(
                parseRunes(fields.getOrDefault("unlocked", List.of())),
                parseActiveRunes(fields.getOrDefault("active", List.of())),
                parseInt(first(fields, "kills"), 0),
                new RuneProfileState.Streak(parseInt(at(streak, 1), 0), parseInt(at(streak, 0), 0)),
                new RuneProfileState.LastKill(
                        parseLong(at(last, 3), Long.MIN_VALUE),
                        at(last, 0),
                        at(last, 1),
                        at(last, 2)
                ),
                Set.copyOf(fields.getOrDefault("mobs", List.of())),
                Set.copyOf(fields.getOrDefault("biomes", List.of())),
                Set.copyOf(fields.getOrDefault("weapons", List.of())),
                rememberedDrop
        );
    }

    private static Set<RuneId> parseRunes(List<String> values) {
        EnumSet<RuneId> result = EnumSet.noneOf(RuneId.class);
        values.forEach(value -> RuneId.byValue(value).ifPresent(result::add));
        return result;
    }

    private static List<RuneId> parseActiveRunes(List<String> values) {
        return values.stream()
                .map(RuneId::byValue)
                .flatMap(Optional::stream)
                .toList();
    }

    private static String first(Map<String, List<String>> fields, String key) {
        return at(fields.getOrDefault(key, List.of()), 0);
    }

    private static String at(List<String> values, int index) {
        return index >= 0 && index < values.size() ? values.get(index) : "";
    }

    private static int parseInt(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
            return fallback;
        }
    }

    private static long parseLong(String value, long fallback) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ignored) {
            return fallback;
        }
    }
}
