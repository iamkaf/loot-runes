package com.iamkaf.lootrunes;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public final class LootRunesForge {
    public LootRunesForge(FMLJavaModLoadingContext context) {
        LootRunesMod.init();
    }
}
