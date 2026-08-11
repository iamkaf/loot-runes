package com.iamkaf.lootrunes.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Mutable server-owned player state. It contains progress, never effect implementation. */
public final class RuneProfile {
    public static final int MAX_ACTIVE = 3;
    public static final int STREAK_WINDOW_TICKS = 20 * 12;

    private final EnumSet<RuneId> unlocked = EnumSet.of(RuneId.PLENTY);
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
    private DropSnapshot rememberedDrop;

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
        boolean changedBiome = !lastBiomeId.isBlank() && !lastBiomeId.equals(facts.biomeId());
        boolean changedWeapon = !lastWeaponId.isBlank() && !lastWeaponId.equals(facts.weaponId());

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
        rememberedDrop = naturalDrops.stream().filter(drop -> !drop.isEmpty()).findFirst().orElse(null);

        EnumSet<RuneId> newlyUnlocked = EnumSet.noneOf(RuneId.class);
        for (RuneDefinition definition : RuneCatalog.all()) {
            if (!unlocked.contains(definition.id()) && definition.unlockRule().isSatisfied(this)) {
                unlocked.add(definition.id());
                newlyUnlocked.add(definition.id());
            }
        }
        return new KillProgress(nextStreak, changedBiome, changedWeapon, Set.copyOf(newlyUnlocked));
    }

    public int nextStreak(long gameTick) {
        if (lastKillTick == Long.MIN_VALUE || gameTick < lastKillTick || gameTick - lastKillTick > STREAK_WINDOW_TICKS) {
            return 1;
        }
        return currentStreak + 1;
    }

    public void restore(
            Collection<RuneId> unlocked,
            Collection<RuneId> active,
            int totalKills,
            int bestStreak,
            int currentStreak,
            long lastKillTick,
            Collection<String> seenMobs,
            Collection<String> seenBiomes,
            Collection<String> seenWeapons,
            String lastMobId,
            String lastBiomeId,
            String lastWeaponId,
            DropSnapshot rememberedDrop
    ) {
        this.unlocked.clear();
        this.unlocked.add(RuneId.PLENTY);
        this.unlocked.addAll(unlocked);
        this.active.clear();
        active.stream().filter(this.unlocked::contains).limit(MAX_ACTIVE).forEach(this.active::add);
        this.totalKills = Math.max(0, totalKills);
        this.bestStreak = Math.max(0, bestStreak);
        this.currentStreak = Math.max(0, currentStreak);
        this.lastKillTick = lastKillTick;
        replace(this.seenMobs, seenMobs);
        replace(this.seenBiomes, seenBiomes);
        replace(this.seenWeapons, seenWeapons);
        this.lastMobId = Objects.requireNonNullElse(lastMobId, "");
        this.lastBiomeId = Objects.requireNonNullElse(lastBiomeId, "");
        this.lastWeaponId = Objects.requireNonNullElse(lastWeaponId, "");
        this.rememberedDrop = rememberedDrop == null || rememberedDrop.isEmpty() ? null : rememberedDrop;
    }

    private static void replace(Set<String> target, Collection<String> source) {
        target.clear();
        source.stream().filter(Objects::nonNull).filter(value -> !value.isBlank()).forEach(target::add);
    }

    public Set<RuneId> unlocked() { return Set.copyOf(unlocked); }
    public List<RuneId> active() { return List.copyOf(active); }
    public boolean isUnlocked(RuneId id) { return unlocked.contains(id); }
    public boolean isActive(RuneId id) { return active.contains(id); }
    public int totalKills() { return totalKills; }
    public int bestStreak() { return bestStreak; }
    public int currentStreak() { return currentStreak; }
    public long lastKillTick() { return lastKillTick; }
    public Set<String> seenMobs() { return Set.copyOf(seenMobs); }
    public Set<String> seenBiomes() { return Set.copyOf(seenBiomes); }
    public Set<String> seenWeapons() { return Set.copyOf(seenWeapons); }
    public String lastMobId() { return lastMobId; }
    public String lastBiomeId() { return lastBiomeId; }
    public String lastWeaponId() { return lastWeaponId; }
    public DropSnapshot rememberedDrop() { return rememberedDrop; }

    public enum ActivationResult { ACTIVATED, DEACTIVATED, LOCKED, FULL }

    public record KillProgress(int streak, boolean changedBiome, boolean changedWeapon, Set<RuneId> newlyUnlocked) {
    }
}
