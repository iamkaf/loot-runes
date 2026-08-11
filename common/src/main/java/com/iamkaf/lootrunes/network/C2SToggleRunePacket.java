package com.iamkaf.lootrunes.network;

import com.iamkaf.amber.api.networking.v1.Packet;
import com.iamkaf.amber.api.networking.v1.PacketDecoder;
import com.iamkaf.amber.api.networking.v1.PacketEncoder;
import com.iamkaf.amber.api.networking.v1.PacketHandler;
import com.iamkaf.lootrunes.domain.RuneId;
import com.iamkaf.lootrunes.runtime.RuneTabletService;
import net.minecraft.server.level.ServerPlayer;

public record C2SToggleRunePacket(RuneId runeId) implements Packet<C2SToggleRunePacket> {
    public static final PacketEncoder<C2SToggleRunePacket> ENCODER = (packet, buffer) -> buffer.writeEnum(packet.runeId);
    public static final PacketDecoder<C2SToggleRunePacket> DECODER = buffer -> new C2SToggleRunePacket(buffer.readEnum(RuneId.class));
    public static final PacketHandler<C2SToggleRunePacket> HANDLER = (packet, context) -> {
        if (!context.isServerSide()) {
            return;
        }
        context.execute(() -> {
            ServerPlayer player = context.getServerPlayer();
            if (player != null) {
                RuneTabletService.toggle(player, packet.runeId);
            }
        });
    };
}
