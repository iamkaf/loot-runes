package com.iamkaf.lootrunes.runtime;

import com.iamkaf.lootrunes.domain.RuneId;
import com.iamkaf.lootrunes.domain.RuneProfile;
import com.iamkaf.lootrunes.network.LootRunesNetwork;
import com.iamkaf.lootrunes.network.S2CRuneTabletPacket;
import com.iamkaf.lootrunes.registry.LootRunesItems;
import net.minecraft.server.level.ServerPlayer;

public final class RuneTabletService {
    private RuneTabletService() {
    }

    public static void open(ServerPlayer player) {
        if (holdsTablet(player)) {
            send(player);
        }
    }

    public static void toggle(ServerPlayer player, RuneId runeId) {
        if (!holdsTablet(player)) {
            return;
        }
        RuneProfileData data = RuneProfileData.get(player.level().getServer());
        RuneProfile.ActivationResult result = data.profile(player.getUUID()).toggle(runeId);
        if (result == RuneProfile.ActivationResult.ACTIVATED || result == RuneProfile.ActivationResult.DEACTIVATED) {
            data.changed();
        }
        send(player);
    }

    private static void send(ServerPlayer player) {
        RuneProfile profile = RuneProfileData.get(player.level().getServer()).profile(player.getUUID());
        LootRunesNetwork.sendToPlayer(new S2CRuneTabletPacket(RuneTabletSnapshot.from(profile)), player);
    }

    private static boolean holdsTablet(ServerPlayer player) {
        return player.getMainHandItem().is(LootRunesItems.RUNE_TABLET.get())
                || player.getOffhandItem().is(LootRunesItems.RUNE_TABLET.get());
    }
}
