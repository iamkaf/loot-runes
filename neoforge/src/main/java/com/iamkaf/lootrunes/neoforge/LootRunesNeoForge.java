package com.iamkaf.lootrunes.neoforge;

import net.neoforged.fml.common.Mod;

import com.iamkaf.lootrunes.LootRunes;

@Mod(LootRunes.MOD_ID)
public final class LootRunesNeoForge {
    public LootRunesNeoForge() {
        LootRunes.init();
    }
}
