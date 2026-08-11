package com.iamkaf.lootrunes.runtime;

import com.iamkaf.lootrunes.domain.DropSnapshot;
import com.iamkaf.lootrunes.domain.RuneCatalog;
import com.iamkaf.lootrunes.domain.RuneId;
import com.iamkaf.lootrunes.domain.RuneKillFacts;
import com.iamkaf.lootrunes.domain.RunePipeline;
import com.iamkaf.lootrunes.domain.RunePlan;
import com.iamkaf.lootrunes.domain.RuneProfile;
import com.iamkaf.lootrunes.registry.LootRunesItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.ArrayList;
import java.util.List;

/** Minecraft adapter for the loader-free rules. Called only from the natural entity loot seam. */
public final class RuneDropService {
    private static final RunePipeline PIPELINE = new RunePipeline();

    private RuneDropService() {
    }

    public static List<ItemStack> transform(
            LivingEntity target,
            LootTable lootTable,
            LootParams params,
            long seed,
            List<ItemStack> naturalDrops
    ) {
        ServerPlayer player = responsiblePlayer(params);
        if (player == null || target instanceof net.minecraft.world.entity.player.Player || !carriesTablet(player)) {
            return naturalDrops;
        }

        RuneProfileData data = RuneProfileData.get(player.level().getServer());
        RuneProfile profile = data.profile(player.getUUID());
        RuneKillFacts facts = facts(target, player);
        List<DropSnapshot> snapshots = naturalDrops.stream().map(RuneDropService::snapshot).filter(drop -> !drop.isEmpty()).toList();
        RunePlan plan = PIPELINE.evaluate(profile, facts, snapshots);

        List<ItemStack> result = new ArrayList<>();
        if (plan.keepNaturalDrops()) {
            naturalDrops.forEach(stack -> result.add(stack.copy()));
        }
        for (int roll = 0; roll < plan.bonusRolls(); roll++) {
            long rollSeed = mixSeed(seed, roll + 1);
            lootTable.getRandomItems(params, rollSeed).forEach(stack -> result.add(stack.copy()));
        }
        plan.echoedDrop().flatMap(RuneDropService::toItemStack).ifPresent(result::add);

        RuneProfile.KillProgress progress = data.recordKill(player.getUUID(), facts, snapshots);
        for (RuneId unlocked : progress.newlyUnlocked()) {
            player.sendSystemMessage(
                    Component.translatable("message.lootrunes.unlocked", Component.translatable(RuneCatalog.get(unlocked).nameKey())),
                    false
            );
        }
        return result;
    }

    private static ServerPlayer responsiblePlayer(LootParams params) {
        var lastDamagePlayer = params.contextMap().getOptional(LootContextParams.LAST_DAMAGE_PLAYER);
        if (lastDamagePlayer instanceof ServerPlayer serverPlayer) {
            return serverPlayer;
        }
        var attackingEntity = params.contextMap().getOptional(LootContextParams.ATTACKING_ENTITY);
        return attackingEntity instanceof ServerPlayer serverPlayer ? serverPlayer : null;
    }

    private static boolean carriesTablet(ServerPlayer player) {
        return player.getInventory().contains(stack -> stack.is(LootRunesItems.RUNE_TABLET.get()));
    }

    private static RuneKillFacts facts(LivingEntity target, ServerPlayer player) {
        String mobId = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType()).toString();
        String biomeId = target.level().getBiome(target.blockPosition())
                .unwrapKey()
                .map(key -> key.identifier().toString())
                .orElse("minecraft:unknown");
        String weaponId = BuiltInRegistries.ITEM.getKey(player.getMainHandItem().getItem()).toString();
        return new RuneKillFacts(mobId, biomeId, weaponId, target.level().getGameTime());
    }

    private static DropSnapshot snapshot(ItemStack stack) {
        if (stack.isEmpty()) {
            return new DropSnapshot("", 0);
        }
        return new DropSnapshot(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString(), stack.getCount());
    }

    private static java.util.Optional<ItemStack> toItemStack(DropSnapshot snapshot) {
        Identifier id = Identifier.tryParse(snapshot.itemId());
        if (id == null) {
            return java.util.Optional.empty();
        }
        return BuiltInRegistries.ITEM.getOptional(id)
                .filter(item -> item != net.minecraft.world.item.Items.AIR)
                .map(item -> new ItemStack(item, snapshot.count()));
    }

    private static long mixSeed(long seed, int roll) {
        long value = seed + 0x9E3779B97F4A7C15L * roll;
        value = (value ^ (value >>> 30)) * 0xBF58476D1CE4E5B9L;
        value = (value ^ (value >>> 27)) * 0x94D049BB133111EBL;
        return value ^ (value >>> 31);
    }
}
