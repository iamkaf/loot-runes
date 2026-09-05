package com.iamkaf.lootrunes.fabric;

import com.iamkaf.lootrunes.fabric.datagen.LootRunesModelProvider;
import com.iamkaf.lootrunes.fabric.datagen.LootRunesRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class LootRunesDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(LootRunesModelProvider::new);
        pack.addProvider(LootRunesRecipeProvider.Runner::new);
    }
}
