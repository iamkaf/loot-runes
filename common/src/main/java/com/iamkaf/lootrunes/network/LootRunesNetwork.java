package com.iamkaf.lootrunes.network;

import com.iamkaf.amber.api.networking.v1.NetworkChannel;
import com.iamkaf.amber.api.networking.v1.Packet;
import com.iamkaf.lootrunes.Constants;
import net.minecraft.server.level.ServerPlayer;

public final class LootRunesNetwork {
    private static final NetworkChannel CHANNEL = NetworkChannel.create(Constants.resource("main"));
    private static boolean initialized;

    private LootRunesNetwork() {
    }

    public static synchronized void init() {
        if (initialized) {
            return;
        }
        CHANNEL.register(
                C2SToggleRunePacket.class,
                C2SToggleRunePacket.ENCODER,
                C2SToggleRunePacket.DECODER,
                C2SToggleRunePacket.HANDLER
        );
        CHANNEL.register(
                S2CRuneTabletPacket.class,
                S2CRuneTabletPacket.ENCODER,
                S2CRuneTabletPacket.DECODER,
                S2CRuneTabletPacket.HANDLER
        );
        initialized = true;
    }

    public static <T extends Packet<T>> void sendToPlayer(T packet, ServerPlayer player) {
        CHANNEL.sendToPlayer(packet, player);
    }

    public static <T extends Packet<T>> void sendToServer(T packet) {
        CHANNEL.sendToServer(packet);
    }
}
