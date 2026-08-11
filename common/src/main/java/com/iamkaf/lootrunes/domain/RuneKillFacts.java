package com.iamkaf.lootrunes.domain;

/** Facts collected from Minecraft before the game-design rules run. */
public record RuneKillFacts(String mobId, String biomeId, String weaponId, long gameTick) {
    public RuneKillFacts {
        mobId = normalized(mobId, "minecraft:unknown");
        biomeId = normalized(biomeId, "minecraft:unknown");
        weaponId = normalized(weaponId, "minecraft:air");
    }

    private static String normalized(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }
}
