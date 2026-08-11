package com.iamkaf.lootrunes.fabric.datagen;

import com.iamkaf.lootrunes.registry.LootRunesItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
//? if >=26.1
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
//? if <26.1
/*import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;*/
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

public final class LootRunesModelProvider extends FabricModelProvider {
    //? if >=26.1
    public LootRunesModelProvider(FabricPackOutput output) {
    //? if <26.1
    /*public LootRunesModelProvider(FabricDataOutput output) {*/
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generators) {
    }

    @Override
    public void generateItemModels(ItemModelGenerators generators) {
        generators.generateFlatItem(LootRunesItems.RUNE_TABLET.get(), ModelTemplates.FLAT_ITEM);
    }
}
