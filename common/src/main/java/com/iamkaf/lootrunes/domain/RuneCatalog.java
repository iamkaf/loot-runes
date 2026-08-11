package com.iamkaf.lootrunes.domain;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

/**
 * The first playtest roster. Keep definitions here so unlock thresholds and effects can be replaced
 * without changing persistence, networking, loot interception, or the tablet UI.
 */
public final class RuneCatalog {
    private static final List<RuneDefinition> RUNES = List.of(
            definition(RuneId.PLENTY, always(), (context, plan) -> plan.addBonusRolls(1)),
            definition(RuneId.SACRIFICE, kills(10), (context, plan) -> plan.discardNaturalDrops().addBonusRolls(2)),
            definition(RuneId.ECHOES, distinctMobs(3), (context, plan) -> plan.echo(context.profile().rememberedDrop())),
            definition(RuneId.ASCENDANCE, bestStreak(5), (context, plan) -> {
                if (context.nextStreak() >= 3) {
                    plan.addBonusRolls(Math.min(3, context.nextStreak() / 3));
                }
            }),
            definition(RuneId.MIGRATION, distinctBiomes(3), (context, plan) -> {
                if (context.changedBiome()) {
                    plan.addBonusRolls(1);
                }
            }),
            definition(RuneId.IMPROVISATION, distinctWeapons(3), (context, plan) -> {
                if (context.changedWeapon()) {
                    plan.addBonusRolls(1);
                }
            })
    );
    private static final Map<RuneId, RuneDefinition> BY_ID = byId(RUNES);

    private RuneCatalog() {
    }

    public static List<RuneDefinition> all() {
        return RUNES;
    }

    public static RuneDefinition get(RuneId id) {
        RuneDefinition definition = BY_ID.get(id);
        if (definition == null) {
            throw new IllegalArgumentException("Unknown rune: " + id);
        }
        return definition;
    }

    private static RuneDefinition definition(RuneId id, RuneUnlockRule unlock, RuneDropRule rule) {
        String prefix = "rune.lootrunes." + id.value();
        return new RuneDefinition(id, prefix, prefix + ".description", prefix + ".unlock", unlock, rule);
    }

    private static RuneUnlockRule always() {
        return metric(1, profile -> 1);
    }

    private static RuneUnlockRule kills(int target) {
        return metric(target, RuneProfileState::totalKills);
    }

    private static RuneUnlockRule distinctMobs(int target) {
        return metric(target, profile -> profile.seenMobs().size());
    }

    private static RuneUnlockRule distinctBiomes(int target) {
        return metric(target, profile -> profile.seenBiomes().size());
    }

    private static RuneUnlockRule distinctWeapons(int target) {
        return metric(target, profile -> profile.seenWeapons().size());
    }

    private static RuneUnlockRule bestStreak(int target) {
        return metric(target, profile -> profile.streak().best());
    }

    private static RuneUnlockRule metric(int target, ToIntFunction<RuneProfileState> progress) {
        return new RuneUnlockRule() {
            @Override
            public int progress(RuneProfileState profile) {
                return Math.min(target, progress.applyAsInt(profile));
            }

            @Override
            public int target() {
                return target;
            }
        };
    }

    private static Map<RuneId, RuneDefinition> byId(List<RuneDefinition> definitions) {
        EnumMap<RuneId, RuneDefinition> result = new EnumMap<>(RuneId.class);
        for (RuneDefinition definition : definitions) {
            if (result.put(definition.id(), definition) != null) {
                throw new IllegalStateException("Duplicate rune definition: " + definition.id());
            }
        }
        return Map.copyOf(result);
    }
}
