package com.iamkaf.extendedgolf;

import com.iamkaf.amber.api.core.AmberMod;
import com.mojang.logging.LogUtils;
import dev.architectury.event.events.common.LootEvent;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class ExtendedGolf extends AmberMod {
    public static final String MOD_ID = "extendedgolf";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ExtendedGolf() {
        super(MOD_ID);
    }

    public static void init() {
        LOGGER.info("Extended Golf Initializing");

        LootEvent.MODIFY_LOOT_TABLE.register(ExtendedGolf::modifyLootTables);
    }

    private static void modifyLootTables(ResourceKey<LootTable> table,
            LootEvent.LootTableModificationContext context, boolean builtIn) {
        if (!builtIn) {
            return;
        }

        if (is(table, "entities/")) {
            context.addPool(makeLootTable(ExtendedGolfTags.createItemTag(table.location()
                    .getPath()
                    .replace("entities/", ""))));
        }
    }

    private static boolean is(ResourceKey<LootTable> lootTableResourceKey, String key) {
        return lootTableResourceKey.location().getPath().startsWith(key);
    }

    private static LootPool.Builder makeLootTable(TagKey<Item> loot) {
        return makeLootTable(TagEntry.expandTag(loot));
    }

    private static LootPool.@NotNull Builder makeLootTable(LootPoolSingletonContainer.Builder<?> entry) {
        return LootPool.lootPool().add(entry).when(LootItemEntityPropertyCondition.hasProperties(
                LootContext.EntityTarget.ATTACKER,
                EntityPredicate.Builder.entity().of(EntityTypeTags.SKELETONS)
        ));
    }
}
