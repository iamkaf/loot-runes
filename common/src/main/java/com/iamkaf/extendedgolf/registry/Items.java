package com.iamkaf.extendedgolf.registry;

import com.iamkaf.extendedgolf.ExtendedGolf;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class Items {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ExtendedGolf.MOD_ID, Registries.ITEM);

    public static RegistrySupplier<Item> EXAMPLE_ITEM = ITEMS.register("example_item",
            () -> new Item(new Item.Properties().arch$tab(CreativeModeTabs.EXTENDED_GOLF))
    );

    public static void init() {
        ITEMS.register();
    }
}