package com.iamkaf.extendedgolf.fabric;

import net.fabricmc.api.ModInitializer;

import com.iamkaf.extendedgolf.ExtendedGolf;

public final class ExtendedGolfFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ExtendedGolf.init();
    }
}
