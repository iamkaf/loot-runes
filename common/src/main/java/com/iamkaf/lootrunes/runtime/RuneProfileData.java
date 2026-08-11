package com.iamkaf.lootrunes.runtime;

import com.iamkaf.lootrunes.Constants;
import com.iamkaf.lootrunes.domain.DropSnapshot;
import com.iamkaf.lootrunes.domain.RuneId;
import com.iamkaf.lootrunes.domain.RuneProfile;
import com.mojang.serialization.Codec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/** World-scoped, server-owned rune profiles. The wire/storage shape is intentionally boring and durable. */
public final class RuneProfileData extends SavedData {
    private static final Codec<Map<String, List<String>>> MAP_CODEC = Codec.unboundedMap(Codec.STRING, Codec.list(Codec.STRING));
    private static final Codec<RuneProfileData> CODEC = MAP_CODEC.xmap(RuneProfileData::decode, RuneProfileData::encode);
    private static final SavedDataType<RuneProfileData> TYPE = new SavedDataType<>(
            Constants.resource("profiles"),
            RuneProfileData::new,
            CODEC,
            DataFixTypes.SAVED_DATA_COMMAND_STORAGE
    );

    private final Map<UUID, RuneProfile> profiles = new HashMap<>();

    public static RuneProfileData get(MinecraftServer server) {
        return server.getDataStorage().computeIfAbsent(TYPE);
    }

    public RuneProfile profile(UUID playerId) {
        return profiles.computeIfAbsent(playerId, ignored -> new RuneProfile());
    }

    public void changed() {
        setDirty();
    }

    private static RuneProfileData decode(Map<String, List<String>> source) {
        RuneProfileData data = new RuneProfileData();
        Map<UUID, Map<String, List<String>>> grouped = new HashMap<>();
        source.forEach((key, value) -> {
            int separator = key.indexOf('/');
            if (separator <= 0 || separator == key.length() - 1) {
                return;
            }
            try {
                UUID playerId = UUID.fromString(key.substring(0, separator));
                grouped.computeIfAbsent(playerId, ignored -> new HashMap<>()).put(key.substring(separator + 1), value);
            } catch (IllegalArgumentException ignored) {
                Constants.LOG.warn("Ignoring malformed Loot Runes profile key {}", key);
            }
        });
        grouped.forEach((playerId, fields) -> data.profiles.put(playerId, decodeProfile(fields)));
        return data;
    }

    private Map<String, List<String>> encode() {
        Map<String, List<String>> encoded = new LinkedHashMap<>();
        profiles.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            String prefix = entry.getKey() + "/";
            RuneProfile profile = entry.getValue();
            encoded.put(prefix + "unlocked", ids(profile.unlocked()));
            encoded.put(prefix + "active", ids(profile.active()));
            encoded.put(prefix + "kills", List.of(Integer.toString(profile.totalKills())));
            encoded.put(prefix + "streak", List.of(Integer.toString(profile.currentStreak()), Integer.toString(profile.bestStreak())));
            encoded.put(prefix + "last", List.of(
                    profile.lastMobId(), profile.lastBiomeId(), profile.lastWeaponId(), Long.toString(profile.lastKillTick())
            ));
            encoded.put(prefix + "mobs", profile.seenMobs().stream().sorted().toList());
            encoded.put(prefix + "biomes", profile.seenBiomes().stream().sorted().toList());
            encoded.put(prefix + "weapons", profile.seenWeapons().stream().sorted().toList());
            DropSnapshot remembered = profile.rememberedDrop();
            if (remembered != null && !remembered.isEmpty()) {
                encoded.put(prefix + "remembered", List.of(remembered.itemId(), Integer.toString(remembered.count())));
            }
        });
        return encoded;
    }

    private static RuneProfile decodeProfile(Map<String, List<String>> fields) {
        RuneProfile profile = new RuneProfile();
        List<String> streak = fields.getOrDefault("streak", List.of());
        List<String> last = fields.getOrDefault("last", List.of());
        List<String> remembered = fields.getOrDefault("remembered", List.of());
        profile.restore(
                parseIds(fields.getOrDefault("unlocked", List.of())),
                parseIds(fields.getOrDefault("active", List.of())),
                parseInt(first(fields, "kills"), 0),
                parseInt(at(streak, 1), 0),
                parseInt(at(streak, 0), 0),
                parseLong(at(last, 3), Long.MIN_VALUE),
                fields.getOrDefault("mobs", List.of()),
                fields.getOrDefault("biomes", List.of()),
                fields.getOrDefault("weapons", List.of()),
                at(last, 0),
                at(last, 1),
                at(last, 2),
                remembered.size() >= 2 ? new DropSnapshot(remembered.get(0), parseInt(remembered.get(1), 0)) : null
        );
        return profile;
    }

    private static List<String> ids(Collection<RuneId> ids) {
        return ids.stream().map(RuneId::value).toList();
    }

    private static EnumSet<RuneId> parseIds(List<String> values) {
        EnumSet<RuneId> result = EnumSet.noneOf(RuneId.class);
        values.forEach(value -> RuneId.byValue(value).ifPresent(result::add));
        return result;
    }

    private static String first(Map<String, List<String>> fields, String key) {
        return at(fields.getOrDefault(key, List.of()), 0);
    }

    private static String at(List<String> values, int index) {
        return index >= 0 && index < values.size() ? values.get(index) : "";
    }

    private static int parseInt(String value, int fallback) {
        try { return Integer.parseInt(value); } catch (NumberFormatException ignored) { return fallback; }
    }

    private static long parseLong(String value, long fallback) {
        try { return Long.parseLong(value); } catch (NumberFormatException ignored) { return fallback; }
    }
}
