package com.iamkaf.lootrunes.registry;

import com.iamkaf.lootrunes.LootRunes;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class Items {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(LootRunes.MOD_ID, Registries.ITEM);

    public static RegistrySupplier<Item> EXAMPLE_ITEM = ITEMS.register("example_item",
            () -> new Item(new Item.Properties().arch$tab(CreativeModeTabs.LOOT_RUNES))
    );

    public static void init() {
        ITEMS.register();
    }
}