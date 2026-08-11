package com.iamkaf.lootrunes.network;

import com.iamkaf.amber.api.networking.v1.Packet;
import com.iamkaf.amber.api.networking.v1.PacketDecoder;
import com.iamkaf.amber.api.networking.v1.PacketEncoder;
import com.iamkaf.amber.api.networking.v1.PacketHandler;
import com.iamkaf.lootrunes.client.LootRunesClient;
import com.iamkaf.lootrunes.domain.RuneId;
import com.iamkaf.lootrunes.runtime.RuneTabletSnapshot;

import java.util.ArrayList;
import java.util.List;

public record S2CRuneTabletPacket(RuneTabletSnapshot snapshot) implements Packet<S2CRuneTabletPacket> {
    public static final PacketEncoder<S2CRuneTabletPacket> ENCODER = (packet, buffer) -> {
        buffer.writeVarInt(packet.snapshot.activeCount());
        buffer.writeVarInt(packet.snapshot.activeLimit());
        buffer.writeVarInt(packet.snapshot.runes().size());
        for (RuneTabletSnapshot.Entry entry : packet.snapshot.runes()) {
            buffer.writeEnum(entry.id());
            buffer.writeBoolean(entry.unlocked());
            buffer.writeBoolean(entry.active());
            buffer.writeVarInt(entry.progress());
            buffer.writeVarInt(entry.target());
        }
    };
    public static final PacketDecoder<S2CRuneTabletPacket> DECODER = buffer -> {
        int activeCount = buffer.readVarInt();
        int activeLimit = buffer.readVarInt();
        int count = buffer.readVarInt();
        List<RuneTabletSnapshot.Entry> entries = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            entries.add(new RuneTabletSnapshot.Entry(
                    buffer.readEnum(RuneId.class),
                    buffer.readBoolean(),
                    buffer.readBoolean(),
                    buffer.readVarInt(),
                    buffer.readVarInt()
            ));
        }
        return new S2CRuneTabletPacket(new RuneTabletSnapshot(entries, activeCount, activeLimit));
    };
    public static final PacketHandler<S2CRuneTabletPacket> HANDLER = (packet, context) -> {
        if (context.isClientSide()) {
            context.execute(() -> LootRunesClient.openOrUpdate(packet.snapshot));
        }
    };
}
