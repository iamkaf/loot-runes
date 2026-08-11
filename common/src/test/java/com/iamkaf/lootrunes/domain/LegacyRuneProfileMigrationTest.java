package com.iamkaf.lootrunes.domain;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LegacyRuneProfileMigrationTest {
    private static final UUID PLAYER_ID = UUID.fromString("21d24d15-2679-4b0d-bca0-8a381fbc4c2e");

    @Test
    void preservesProgressAndActivationOrder() {
        Map<String, List<String>> legacy = new LinkedHashMap<>();
        put(legacy, "unlocked", "plenty", "echoes");
        put(legacy, "active", "echoes", "plenty");
        put(legacy, "kills", "12");
        put(legacy, "streak", "3", "5");
        put(legacy, "last", "minecraft:zombie", "minecraft:plains", "minecraft:iron_sword", "240");
        put(legacy, "mobs", "minecraft:zombie");
        put(legacy, "biomes", "minecraft:plains");
        put(legacy, "weapons", "minecraft:iron_sword");
        put(legacy, "remembered", "minecraft:rotten_flesh", "2");

        RuneProfileState state = LegacyRuneProfileMigration.decode(legacy).get(PLAYER_ID);

        assertEquals(12, state.totalKills());
        assertEquals(new RuneProfileState.Streak(5, 3), state.streak());
        assertEquals(List.of(RuneId.ECHOES, RuneId.PLENTY), state.active());
        assertEquals(new DropSnapshot("minecraft:rotten_flesh", 2), state.rememberedDrop().orElseThrow());
    }

    private static void put(Map<String, List<String>> target, String field, String... values) {
        target.put(PLAYER_ID + "/" + field, List.of(values));
    }
}
