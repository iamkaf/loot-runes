package com.iamkaf.lootrunes.content;

import com.iamkaf.lootrunes.network.C2SOpenRuneTabletPacket;
import com.iamkaf.lootrunes.network.LootRunesNetwork;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public final class RuneTabletItem extends Item {
    public RuneTabletItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            LootRunesNetwork.sendToServer(new C2SOpenRuneTabletPacket());
        } else if (player instanceof ServerPlayer) {
            player.awardStat(Stats.ITEM_USED.get(this));
        }
        return InteractionResult.SUCCESS;
    }
}
