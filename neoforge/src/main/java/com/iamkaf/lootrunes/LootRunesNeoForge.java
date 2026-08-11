package com.iamkaf.lootrunes;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public final class LootRunesNeoForge {
    public LootRunesNeoForge(IEventBus eventBus) {
        LootRunesMod.init();
    }
}
