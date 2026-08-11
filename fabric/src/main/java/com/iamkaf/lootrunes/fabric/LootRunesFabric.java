package com.iamkaf.lootrunes.fabric;

import net.fabricmc.api.ModInitializer;

import com.iamkaf.lootrunes.LootRunes;

public final class LootRunesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        LootRunes.init();
    }
}
