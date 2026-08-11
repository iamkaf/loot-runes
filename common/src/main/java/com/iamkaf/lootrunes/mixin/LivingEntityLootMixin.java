package com.iamkaf.lootrunes.mixin;

import com.iamkaf.lootrunes.runtime.RuneDropService;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.function.Consumer;

@Mixin(LivingEntity.class)
abstract class LivingEntityLootMixin {
    @Redirect(
            method = "dropFromLootTable(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;ZLnet/minecraft/resources/ResourceKey;Ljava/util/function/Consumer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/storage/loot/LootTable;getRandomItems(Lnet/minecraft/world/level/storage/loot/LootParams;JLjava/util/function/Consumer;)V"
            )
    )
    private void lootrunes$transformNaturalDrops(
            LootTable lootTable,
            LootParams params,
            long seed,
            Consumer<ItemStack> consumer
    ) {
        List<ItemStack> natural = lootTable.getRandomItems(params, seed);
        RuneDropService.transform((LivingEntity) (Object) this, lootTable, params, seed, natural).forEach(consumer);
    }
}
