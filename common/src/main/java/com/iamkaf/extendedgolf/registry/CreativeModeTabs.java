package com.iamkaf.extendedgolf.registry;

import com.iamkaf.extendedgolf.ExtendedGolf;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeModeTabs {
    private static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(ExtendedGolf.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> EXTENDED_GOLF = TABS.register(
            ExtendedGolf.MOD_ID,
            () -> CreativeTabRegistry.create(Component.translatable("creativetab." + ExtendedGolf.MOD_ID + "." + ExtendedGolf.MOD_ID),
                    () -> new ItemStack(Items.EXAMPLE_ITEM.get())
            )
    );

    public static void init() {
        TABS.register();
    }
}