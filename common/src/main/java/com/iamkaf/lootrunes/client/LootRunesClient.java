package com.iamkaf.lootrunes.client;

import com.iamkaf.lootrunes.runtime.RuneTabletSnapshot;
import net.minecraft.client.Minecraft;

public final class LootRunesClient {
    private LootRunesClient() {
    }

    public static void openOrUpdate(RuneTabletSnapshot snapshot) {
        Minecraft minecraft = Minecraft.getInstance();
        //? if >=26.2 {
        if (minecraft.gui.screen() instanceof RuneTabletScreen screen) {
            screen.update(snapshot);
            return;
        }
        minecraft.setScreenAndShow(new RuneTabletScreen(snapshot));
        //?} else {
        /*if (minecraft.screen instanceof RuneTabletScreen screen) {
            screen.update(snapshot);
            return;
        }
        minecraft.setScreen(new RuneTabletScreen(snapshot));*/
        //?}
    }
}
