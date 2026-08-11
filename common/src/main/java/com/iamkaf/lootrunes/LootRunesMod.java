package com.iamkaf.lootrunes;

import com.iamkaf.amber.api.core.v2.AmberInitializer;
import com.iamkaf.lootrunes.network.LootRunesNetwork;
import com.iamkaf.lootrunes.registry.LootRunesCreativeTabs;
import com.iamkaf.lootrunes.registry.LootRunesItems;

public final class LootRunesMod {
    private static boolean initialized;

    private LootRunesMod() {
    }

    public static synchronized void init() {
        if (initialized) {
            return;
        }
        AmberInitializer.initialize(Constants.MOD_ID);
        LootRunesNetwork.init();
        LootRunesItems.init();
        LootRunesCreativeTabs.init();
        initialized = true;
        Constants.LOG.info("Initializing {}", Constants.MOD_NAME);
    }
}
