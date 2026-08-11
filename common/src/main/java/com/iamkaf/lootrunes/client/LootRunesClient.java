package com.iamkaf.lootrunes.client;

import com.iamkaf.lootrunes.runtime.RuneTabletSnapshot;
import net.minecraft.client.Minecraft;

public final class LootRunesClient {
    private LootRunesClient() {
    }

    public static void openOrUpdate(RuneTabletSnapshot snapshot) {
        Minecraft.getInstance().setScreenAndShow(new RuneTabletScreen(snapshot));
    }
}
