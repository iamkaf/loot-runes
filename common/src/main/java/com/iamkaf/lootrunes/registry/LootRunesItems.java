package com.iamkaf.lootrunes.registry;

import com.iamkaf.amber.api.registry.v1.DeferredRegister;
import com.iamkaf.amber.api.registry.v1.RegistrySupplier;
import com.iamkaf.lootrunes.Constants;
import com.iamkaf.lootrunes.content.RuneTabletItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public final class LootRunesItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Constants.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> RUNE_TABLET = ITEMS.register(
            "rune_tablet",
            key -> new RuneTabletItem(new Item.Properties().setId(key).stacksTo(1).rarity(Rarity.UNCOMMON))
    );

    private LootRunesItems() {
    }

    public static void init() {
        ITEMS.register();
    }
}
