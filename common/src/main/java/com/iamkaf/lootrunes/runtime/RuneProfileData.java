package com.iamkaf.lootrunes.runtime;

import com.iamkaf.lootrunes.Constants;
import com.iamkaf.lootrunes.domain.DropSnapshot;
import com.iamkaf.lootrunes.domain.LegacyRuneProfileMigration;
import com.iamkaf.lootrunes.domain.RuneId;
import com.iamkaf.lootrunes.domain.RuneKillFacts;
import com.iamkaf.lootrunes.domain.RuneProfile;
import com.iamkaf.lootrunes.domain.RuneProfileState;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

/** World-scoped profile repository. All mutations pass through here so saves are always dirtied. */
public final class RuneProfileData extends SavedData {
    private static final int SCHEMA_VERSION = 1;
    private static final Codec<Map<String, List<String>>> LEGACY_CODEC = Codec.unboundedMap(
            Codec.STRING,
            Codec.list(Codec.STRING)
    );
    private static final Codec<RuneProfileData> CODEC = Codec.either(ProfileFile.CODEC, LEGACY_CODEC).xmap(
            source -> decode(source.map(profileFile -> profileFile, RuneProfileData::migrateLegacy)),
            data -> Either.left(data.encode())
    );
    private static final SavedDataType<RuneProfileData> TYPE = new SavedDataType<>(
            //? if >=26.1 {
            Constants.resource("profiles"),
            //?} else {
            /*Constants.MOD_ID + "_profiles",*/
            //?}
            RuneProfileData::new,
            CODEC,
            DataFixTypes.SAVED_DATA_COMMAND_STORAGE
    );

    private final Map<UUID, RuneProfile> profiles = new TreeMap<>();

    public static RuneProfileData get(MinecraftServer server) {
        //? if >=26.1
        return server.getDataStorage().computeIfAbsent(TYPE);
        //? if <26.1
        /*return server.overworld().getDataStorage().computeIfAbsent(TYPE);*/
    }

    RuneProfile profile(UUID playerId) {
        return profiles.computeIfAbsent(playerId, ignored -> new RuneProfile());
    }

    RuneProfile.ActivationResult toggle(UUID playerId, RuneId runeId) {
        RuneProfile.ActivationResult result = profile(playerId).toggle(runeId);
        if (result == RuneProfile.ActivationResult.ACTIVATED || result == RuneProfile.ActivationResult.DEACTIVATED) {
            setDirty();
        }
        return result;
    }

    RuneProfile.KillProgress recordKill(UUID playerId, RuneKillFacts facts, List<DropSnapshot> naturalDrops) {
        RuneProfile.KillProgress progress = profile(playerId).recordKill(facts, naturalDrops);
        setDirty();
        return progress;
    }

    private static RuneProfileData decode(ProfileFile file) {
        RuneProfileData data = new RuneProfileData();
        if (file.schemaVersion() > SCHEMA_VERSION) {
            Constants.LOG.warn(
                    "Loot Runes profile data uses schema {}, but this build only knows schema {}",
                    file.schemaVersion(),
                    SCHEMA_VERSION
            );
        }
        file.profiles().forEach((playerId, profile) -> {
            try {
                data.profiles.put(UUID.fromString(playerId), new RuneProfile(profile.toState()));
            } catch (IllegalArgumentException ignored) {
                Constants.LOG.warn("Ignoring malformed Loot Runes player id {}", playerId);
            }
        });
        return data;
    }

    private ProfileFile encode() {
        Map<String, PersistedProfile> encoded = new TreeMap<>();
        profiles.forEach((playerId, profile) -> encoded.put(playerId.toString(), PersistedProfile.from(profile.state())));
        return new ProfileFile(SCHEMA_VERSION, encoded);
    }

    private static ProfileFile migrateLegacy(Map<String, List<String>> source) {
        Map<String, PersistedProfile> profiles = new TreeMap<>();
        LegacyRuneProfileMigration.decode(source)
                .forEach((playerId, state) -> profiles.put(playerId.toString(), PersistedProfile.from(state)));
        return new ProfileFile(SCHEMA_VERSION, profiles);
    }

    private static EnumSet<RuneId> parseRunes(List<String> values) {
        EnumSet<RuneId> result = EnumSet.noneOf(RuneId.class);
        values.forEach(value -> RuneId.byValue(value).ifPresent(result::add));
        return result;
    }

    private static List<RuneId> parseActiveRunes(List<String> values) {
        return values.stream()
                .map(RuneId::byValue)
                .flatMap(Optional::stream)
                .distinct()
                .toList();
    }

    private static List<String> runeIds(Iterable<RuneId> runes) {
        ArrayList<String> result = new ArrayList<>();
        runes.forEach(rune -> result.add(rune.value()));
        return List.copyOf(result);
    }

    private static Set<String> strings(List<String> values) {
        return new LinkedHashSet<>(values);
    }

    private record ProfileFile(int schemaVersion, Map<String, PersistedProfile> profiles) {
        private static final Codec<ProfileFile> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("schema_version").forGetter(ProfileFile::schemaVersion),
                Codec.unboundedMap(Codec.STRING, PersistedProfile.CODEC)
                        .optionalFieldOf("profiles", Map.of())
                        .forGetter(ProfileFile::profiles)
        ).apply(instance, ProfileFile::new));

        private ProfileFile {
            profiles = Map.copyOf(profiles);
        }
    }

    private record PersistedProfile(
            List<String> unlocked,
            List<String> active,
            int totalKills,
            PersistedStreak streak,
            PersistedLastKill lastKill,
            List<String> seenMobs,
            List<String> seenBiomes,
            List<String> seenWeapons,
            Optional<PersistedDrop> rememberedDrop
    ) {
        private static final Codec<PersistedProfile> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.list(Codec.STRING).optionalFieldOf("unlocked", List.of()).forGetter(PersistedProfile::unlocked),
                Codec.list(Codec.STRING).optionalFieldOf("active", List.of()).forGetter(PersistedProfile::active),
                Codec.INT.optionalFieldOf("total_kills", 0).forGetter(PersistedProfile::totalKills),
                PersistedStreak.CODEC.optionalFieldOf("streak", PersistedStreak.EMPTY).forGetter(PersistedProfile::streak),
                PersistedLastKill.CODEC.optionalFieldOf("last_kill", PersistedLastKill.NONE).forGetter(PersistedProfile::lastKill),
                Codec.list(Codec.STRING).optionalFieldOf("seen_mobs", List.of()).forGetter(PersistedProfile::seenMobs),
                Codec.list(Codec.STRING).optionalFieldOf("seen_biomes", List.of()).forGetter(PersistedProfile::seenBiomes),
                Codec.list(Codec.STRING).optionalFieldOf("seen_weapons", List.of()).forGetter(PersistedProfile::seenWeapons),
                PersistedDrop.CODEC.optionalFieldOf("remembered_drop").forGetter(PersistedProfile::rememberedDrop)
        ).apply(instance, PersistedProfile::new));

        private static PersistedProfile from(RuneProfileState state) {
            return new PersistedProfile(
                    runeIds(state.unlocked().stream().sorted().toList()),
                    runeIds(state.active()),
                    state.totalKills(),
                    new PersistedStreak(state.streak().best(), state.streak().current()),
                    new PersistedLastKill(
                            state.lastKill().gameTick(),
                            state.lastKill().mobId(),
                            state.lastKill().biomeId(),
                            state.lastKill().weaponId()
                    ),
                    state.seenMobs().stream().sorted().toList(),
                    state.seenBiomes().stream().sorted().toList(),
                    state.seenWeapons().stream().sorted().toList(),
                    state.rememberedDrop().map(PersistedDrop::from)
            );
        }

        private RuneProfileState toState() {
            return new RuneProfileState(
                    parseRunes(unlocked),
                    parseActiveRunes(active),
                    totalKills,
                    new RuneProfileState.Streak(streak.best(), streak.current()),
                    new RuneProfileState.LastKill(lastKill.gameTick(), lastKill.mobId(), lastKill.biomeId(), lastKill.weaponId()),
                    strings(seenMobs),
                    strings(seenBiomes),
                    strings(seenWeapons),
                    rememberedDrop.map(PersistedDrop::toSnapshot)
            );
        }
    }

    private record PersistedStreak(int best, int current) {
        private static final PersistedStreak EMPTY = new PersistedStreak(0, 0);
        private static final Codec<PersistedStreak> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.optionalFieldOf("best", 0).forGetter(PersistedStreak::best),
                Codec.INT.optionalFieldOf("current", 0).forGetter(PersistedStreak::current)
        ).apply(instance, PersistedStreak::new));
    }

    private record PersistedLastKill(long gameTick, String mobId, String biomeId, String weaponId) {
        private static final PersistedLastKill NONE = new PersistedLastKill(Long.MIN_VALUE, "", "", "");
        private static final Codec<PersistedLastKill> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.LONG.optionalFieldOf("game_tick", Long.MIN_VALUE).forGetter(PersistedLastKill::gameTick),
                Codec.STRING.optionalFieldOf("mob", "").forGetter(PersistedLastKill::mobId),
                Codec.STRING.optionalFieldOf("biome", "").forGetter(PersistedLastKill::biomeId),
                Codec.STRING.optionalFieldOf("weapon", "").forGetter(PersistedLastKill::weaponId)
        ).apply(instance, PersistedLastKill::new));
    }

    private record PersistedDrop(String itemId, int count) {
        private static final Codec<PersistedDrop> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("item").forGetter(PersistedDrop::itemId),
                Codec.INT.fieldOf("count").forGetter(PersistedDrop::count)
        ).apply(instance, PersistedDrop::new));

        private static PersistedDrop from(DropSnapshot drop) {
            return new PersistedDrop(drop.itemId(), drop.count());
        }

        private DropSnapshot toSnapshot() {
            return new DropSnapshot(itemId, count);
        }
    }
}
