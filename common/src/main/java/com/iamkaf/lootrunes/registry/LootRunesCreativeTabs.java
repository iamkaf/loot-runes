package com.iamkaf.lootrunes.registry;

import com.iamkaf.amber.api.registry.v1.RegistrySupplier;
import com.iamkaf.amber.api.registry.v1.creativetabs.CreativeModeTabRegistry;
import com.iamkaf.amber.api.registry.v1.creativetabs.TabBuilder;
import com.iamkaf.lootrunes.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class LootRunesCreativeTabs {
    public static final RegistrySupplier<CreativeModeTab> MAIN = CreativeModeTabRegistry.register(builder());

    private LootRunesCreativeTabs() {
    }

    public static void init() {
        Constants.LOG.debug("Registered Loot Runes creative tab {}", MAIN.getId());
    }

    private static TabBuilder builder() {
        return CreativeModeTabRegistry.builder(Constants.resource("main"))
                .title(Component.translatable("creativetab.lootrunes.main"))
                .icon(() -> new ItemStack(LootRunesItems.RUNE_TABLET.get()))
                .addItem(() -> LootRunesItems.RUNE_TABLET.get());
    }
}
