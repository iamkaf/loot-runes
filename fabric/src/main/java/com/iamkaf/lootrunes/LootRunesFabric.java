package com.iamkaf.lootrunes;

import net.fabricmc.api.ModInitializer;

public final class LootRunesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        LootRunesMod.init();
    }
}
