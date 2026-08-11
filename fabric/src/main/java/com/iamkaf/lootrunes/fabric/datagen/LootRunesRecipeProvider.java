package com.iamkaf.lootrunes.fabric.datagen;

import com.iamkaf.lootrunes.Constants;
import com.iamkaf.lootrunes.registry.LootRunesItems;
//? if >=26.1
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
//? if <26.1
/*import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;*/
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public final class LootRunesRecipeProvider extends RecipeProvider {
    private LootRunesRecipeProvider(HolderLookup.Provider lookup, RecipeOutput output) {
        super(lookup, output);
    }

    @Override
    public void buildRecipes() {
        shaped(RecipeCategory.TOOLS, LootRunesItems.RUNE_TABLET.get())
                .define('A', Items.AMETHYST_SHARD)
                .define('C', Items.COPPER_INGOT)
                .define('S', Items.STONE)
                .pattern("ACA")
                .pattern("CSC")
                .pattern("ACA")
                .unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
                .save(output, ResourceKey.create(Registries.RECIPE, Constants.resource("rune_tablet")));
    }

    public static final class Runner extends FabricRecipeProvider {
        //? if >=26.1
        public Runner(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        //? if <26.1
        /*public Runner(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {*/
            super(output, lookup);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider lookup, @NotNull RecipeOutput output) {
            return new LootRunesRecipeProvider(lookup, output);
        }

        @Override
        public @NotNull String getName() {
            return "Loot Runes recipes";
        }
    }
}
