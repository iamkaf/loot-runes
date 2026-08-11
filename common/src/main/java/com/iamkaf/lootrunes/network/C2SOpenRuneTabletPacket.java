package com.iamkaf.lootrunes.network;

import com.iamkaf.amber.api.networking.v1.Packet;
import com.iamkaf.amber.api.networking.v1.PacketDecoder;
import com.iamkaf.amber.api.networking.v1.PacketEncoder;
import com.iamkaf.amber.api.networking.v1.PacketHandler;
import com.iamkaf.lootrunes.runtime.RuneTabletService;
import net.minecraft.server.level.ServerPlayer;

public record C2SOpenRuneTabletPacket() implements Packet<C2SOpenRuneTabletPacket> {
    public static final PacketEncoder<C2SOpenRuneTabletPacket> ENCODER = (packet, buffer) -> {
    };
    public static final PacketDecoder<C2SOpenRuneTabletPacket> DECODER = buffer -> new C2SOpenRuneTabletPacket();
    public static final PacketHandler<C2SOpenRuneTabletPacket> HANDLER = (packet, context) -> {
        if (!context.isServerSide()) {
            return;
        }
        context.execute(() -> {
            ServerPlayer player = context.getServerPlayer();
            if (player != null) {
                RuneTabletService.open(player);
            }
        });
    };
}
