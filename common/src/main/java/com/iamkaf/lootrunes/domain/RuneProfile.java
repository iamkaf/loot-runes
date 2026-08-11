package com.iamkaf.lootrunes.domain;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/** Mutable server-owned player state. It contains progress, never effect implementation. */
public final class RuneProfile {
    public static final int MAX_ACTIVE = 3;
    public static final int STREAK_WINDOW_TICKS = 20 * 12;

    private final EnumSet<RuneId> unlocked = EnumSet.noneOf(RuneId.class);
    private final List<RuneId> active = new ArrayList<>();
    private final Set<String> seenMobs = new LinkedHashSet<>();
    private final Set<String> seenBiomes = new LinkedHashSet<>();
    private final Set<String> seenWeapons = new LinkedHashSet<>();
    private int totalKills;
    private int bestStreak;
    private int currentStreak;
    private long lastKillTick = Long.MIN_VALUE;
    private String lastMobId = "";
    private String lastBiomeId = "";
    private String lastWeaponId = "";
    private Optional<DropSnapshot> rememberedDrop = Optional.empty();

    public RuneProfile() {
        this(RuneProfileState.initial());
    }

    public RuneProfile(RuneProfileState state) {
        restore(state);
    }

    public ActivationResult toggle(RuneId id) {
        Objects.requireNonNull(id, "id");
        if (active.remove(id)) {
            return ActivationResult.DEACTIVATED;
        }
        if (!unlocked.contains(id)) {
            return ActivationResult.LOCKED;
        }
        if (active.size() >= MAX_ACTIVE) {
            return ActivationResult.FULL;
        }
        active.add(id);
        return ActivationResult.ACTIVATED;
    }

    public KillProgress recordKill(RuneKillFacts facts, List<DropSnapshot> naturalDrops) {
        int nextStreak = nextStreak(facts.gameTick());

        totalKills++;
        currentStreak = nextStreak;
        bestStreak = Math.max(bestStreak, currentStreak);
        lastKillTick = facts.gameTick();
        lastMobId = facts.mobId();
        lastBiomeId = facts.biomeId();
        lastWeaponId = facts.weaponId();
        seenMobs.add(facts.mobId());
        seenBiomes.add(facts.biomeId());
        seenWeapons.add(facts.weaponId());
        rememberedDrop = naturalDrops.stream().filter(drop -> !drop.isEmpty()).findFirst();

        RuneProfileState state = state();
        EnumSet<RuneId> newlyUnlocked = EnumSet.noneOf(RuneId.class);
        for (RuneDefinition definition : RuneCatalog.all()) {
            if (!unlocked.contains(definition.id()) && definition.unlockRule().isSatisfied(state)) {
                unlocked.add(definition.id());
                newlyUnlocked.add(definition.id());
            }
        }
        return new KillProgress(Set.copyOf(newlyUnlocked));
    }

    public int nextStreak(long gameTick) {
        if (lastKillTick == Long.MIN_VALUE || gameTick < lastKillTick || gameTick - lastKillTick > STREAK_WINDOW_TICKS) {
            return 1;
        }
        return currentStreak + 1;
    }

    public RuneProfileState state() {
        return new RuneProfileState(
                unlocked,
                active,
                totalKills,
                new RuneProfileState.Streak(bestStreak, currentStreak),
                new RuneProfileState.LastKill(lastKillTick, lastMobId, lastBiomeId, lastWeaponId),
                seenMobs,
                seenBiomes,
                seenWeapons,
                rememberedDrop
        );
    }

    private void restore(RuneProfileState state) {
        Objects.requireNonNull(state, "state");
        this.unlocked.clear();
        this.unlocked.addAll(state.unlocked());
        this.active.clear();
        this.active.addAll(state.active());
        this.totalKills = state.totalKills();
        this.bestStreak = state.streak().best();
        this.currentStreak = state.streak().current();
        this.lastKillTick = state.lastKill().gameTick();
        replace(this.seenMobs, state.seenMobs());
        replace(this.seenBiomes, state.seenBiomes());
        replace(this.seenWeapons, state.seenWeapons());
        this.lastMobId = state.lastKill().mobId();
        this.lastBiomeId = state.lastKill().biomeId();
        this.lastWeaponId = state.lastKill().weaponId();
        this.rememberedDrop = state.rememberedDrop();
    }

    private static void replace(Set<String> target, Set<String> source) {
        target.clear();
        source.stream().filter(Objects::nonNull).filter(value -> !value.isBlank()).forEach(target::add);
    }

    public Set<RuneId> unlocked() {
        return Set.copyOf(unlocked);
    }

    public List<RuneId> active() {
        return List.copyOf(active);
    }

    public boolean isUnlocked(RuneId id) {
        return unlocked.contains(id);
    }

    public boolean isActive(RuneId id) {
        return active.contains(id);
    }

    public int totalKills() {
        return totalKills;
    }

    public int bestStreak() {
        return bestStreak;
    }

    public int currentStreak() {
        return currentStreak;
    }

    public long lastKillTick() {
        return lastKillTick;
    }

    public Set<String> seenMobs() {
        return Set.copyOf(seenMobs);
    }

    public Set<String> seenBiomes() {
        return Set.copyOf(seenBiomes);
    }

    public Set<String> seenWeapons() {
        return Set.copyOf(seenWeapons);
    }

    public String lastMobId() {
        return lastMobId;
    }

    public String lastBiomeId() {
        return lastBiomeId;
    }

    public String lastWeaponId() {
        return lastWeaponId;
    }

    public Optional<DropSnapshot> rememberedDrop() {
        return rememberedDrop;
    }

    public enum ActivationResult {
        ACTIVATED,
        DEACTIVATED,
        LOCKED,
        FULL
    }

    public record KillProgress(Set<RuneId> newlyUnlocked) {
        public KillProgress {
            newlyUnlocked = Set.copyOf(newlyUnlocked);
        }
    }
}
