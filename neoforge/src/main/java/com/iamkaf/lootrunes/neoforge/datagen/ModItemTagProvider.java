package com.iamkaf.lootrunes.neoforge.datagen;

import com.iamkaf.lootrunes.LootRunes;
import com.iamkaf.lootrunes.LootRunesTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture,
            CompletableFuture<TagLookup<Block>> completableFuture2,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, completableFuture, completableFuture2, LootRunes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(LootRunesTags.silverfish).add(Items.ANDESITE)
                .add(Items.DIORITE)
                .add(Items.GRANITE)
                .add(Items.COBBLESTONE);

        tag(LootRunesTags.vex).add(Items.GHAST_TEAR).add(Items.SPECTRAL_ARROW).add(Items.GLOW_INK_SAC);

        tag(LootRunesTags.warden).add(Items.ECHO_SHARD).add(Items.MUSIC_DISC_5);

        tag(LootRunesTags.slime).add(Items.LIME_WOOL)
                .add(Items.LIME_CARPET)
                .add(Items.LIME_CANDLE)
                .add(Items.LIME_GLAZED_TERRACOTTA)
                .add(Items.SLIME_BLOCK)
                .add(Items.LIME_DYE);

        tag(LootRunesTags.ghast).add(Items.CRYING_OBSIDIAN)
                .add(Items.QUARTZ_BLOCK)
                .add(Items.SOUL_LANTERN);

        tag(LootRunesTags.zombie).add(Items.DIRT);

        tag(LootRunesTags.tadpole).add(Items.SEAGRASS).add(Items.LILY_PAD).add(Items.KELP);

        tag(LootRunesTags.cat).addTag(ItemTags.FISHES).add(Items.STRING);

        tag(LootRunesTags.illusioner).add(Items.CARVED_PUMPKIN);

        tag(LootRunesTags.magma_cube).add(Items.NETHER_BRICKS);

        tag(LootRunesTags.mule).add(Items.SADDLE).add(Items.CHEST);

        tag(LootRunesTags.camel).add(Items.CACTUS).add(Items.DEAD_BUSH);

        tag(LootRunesTags.hoglin).add(Items.COOKED_PORKCHOP);

        tag(LootRunesTags.witch).add(Items.GLOWSTONE).add(Items.BREWING_STAND);

        tag(LootRunesTags.skeleton_horse).add(Items.SKELETON_SKULL);

        tag(LootRunesTags.zombified_piglin).add(Items.GOLD_INGOT).add(Items.ROTTEN_FLESH);

        tag(LootRunesTags.chicken).add(Items.EGG).add(Items.FEATHER).add(Items.COOKED_CHICKEN);

        tag(LootRunesTags.skeleton).add(Items.BONE_MEAL).add(Items.ARROW);

        // lol
        // tag(LootRunesTags.enderman).add(Items.ENDER_PEARL).add(Items.ENDER_EYE);

        tag(LootRunesTags.shulker).add(Items.PURPUR_BLOCK)
                .add(Items.PURPUR_PILLAR)
                .add(Items.PURPUR_SLAB)
                .add(Items.PURPUR_STAIRS);

        tag(LootRunesTags.snow_golem).add(Items.CARVED_PUMPKIN);

        // lol
        // tag(LootRunesTags.ender_dragon).add(Items.DRAGON_BREATH).add(Items.END_CRYSTAL);

        tag(LootRunesTags.ravager).add(Items.SADDLE).add(Items.EMERALD).add(Items.BAMBOO_CHEST_RAFT);

        tag(LootRunesTags.llama).addTag(ItemTags.WOOL_CARPETS).add(Items.LEAD);

        tag(LootRunesTags.horse).add(Items.HAY_BLOCK).addTag(ItemTags.HORSE_TEMPT_ITEMS);

        // lol
        // tag(LootRunesTags.player).add(Items.DIAMOND_SWORD).add(Items.SHIELD);

        tag(LootRunesTags.cod).add(Items.SEAGRASS);

        tag(LootRunesTags.mooshroom).add(Items.RED_MUSHROOM).add(Items.BOWL).add(Items.MYCELIUM);

        tag(LootRunesTags.cow).add(Items.MILK_BUCKET).add(Items.LEATHER);

        tag(LootRunesTags.pig).add(Items.PORKCHOP).add(Items.CARROT);

        tag(LootRunesTags.donkey).add(Items.CHEST).add(Items.SADDLE);

        tag(LootRunesTags.goat).add(Items.GOAT_HORN).add(Items.WHEAT);

        tag(LootRunesTags.endermite).add(Items.CHORUS_FRUIT);

        tag(LootRunesTags.guardian).add(Items.PRISMARINE_SHARD).add(Items.SPONGE);

        tag(LootRunesTags.salmon).add(Items.SEAGRASS);

        tag(LootRunesTags.sheep).addTag(ItemTags.WOOL);

        tag(LootRunesTags.pillager).addTag(ItemTags.WOOL);

        tag(LootRunesTags.sniffer).addTag(ItemTags.SNIFFER_FOOD).add(Items.FLOWER_POT);

        tag(LootRunesTags.frog).add(Items.OCHRE_FROGLIGHT).addTag(ItemTags.FROG_FOOD);

        tag(LootRunesTags.zoglin).add(Items.COOKED_PORKCHOP);

        tag(LootRunesTags.cave_spider).add(Items.COBWEB);

        tag(LootRunesTags.wither_skeleton).add(Items.SOUL_SAND).add(Items.COAL);

        tag(LootRunesTags.rabbit).add(Items.SAND).add(Items.GOLDEN_CARROT);

        tag(LootRunesTags.strider).add(Items.WARPED_FUNGUS).add(Items.SADDLE);

        tag(LootRunesTags.pufferfish).add(Items.GLASS_BOTTLE);

        tag(LootRunesTags.axolotl).add(Items.AZALEA).add(Items.TROPICAL_FISH);

        // lol
        // tag(LootRunesTags.armor_stand).add(Items.STICK).add(Items.SMOOTH_STONE_SLAB);

        tag(LootRunesTags.elder_guardian).add(Items.PRISMARINE_CRYSTALS).add(Items.TRIDENT);

        // lol
        // tag(LootRunesTags.creeper).add(Items.GUNPOWDER).add(Items.TNT);

        tag(LootRunesTags.allay).add(Items.MUSIC_DISC_OTHERSIDE).add(Items.AMETHYST_SHARD);

        tag(LootRunesTags.stray).add(Items.ICE).add(Items.GRAY_DYE);

        tag(LootRunesTags.dolphin).add(Items.COD).add(Items.SEAGRASS);

        tag(LootRunesTags.spider).add(Items.FERMENTED_SPIDER_EYE).add(Items.RED_DYE).add(Items.BLUE_DYE);

        tag(LootRunesTags.zombie_horse).add(Items.HAY_BLOCK);

        tag(LootRunesTags.drowned).add(Items.NAUTILUS_SHELL).add(Items.TRIDENT);

        tag(LootRunesTags.bat).add(Items.COAL).add(Items.STRING);

        tag(LootRunesTags.trader_llama).add(Items.LEAD).addTag(ItemTags.WOOL_CARPETS);

        tag(LootRunesTags.turtle).add(Items.TURTLE_EGG);

        tag(LootRunesTags.vindicator).add(Items.IRON_AXE).add(Items.LAPIS_BLOCK);

        tag(LootRunesTags.evoker).add(Items.TOTEM_OF_UNDYING).add(Items.BOOK);

        tag(LootRunesTags.zombie_villager).add(Items.COMPASS);

        tag(LootRunesTags.fox).add(Items.SWEET_BERRIES).add(Items.JUKEBOX);

        tag(LootRunesTags.breeze).add(Items.FEATHER).add(Items.PHANTOM_MEMBRANE);

        tag(LootRunesTags.bee).addTag(ItemTags.FLOWERS);

        tag(LootRunesTags.wolf).add(Items.BONE).add(Items.RABBIT_FOOT);

        tag(LootRunesTags.phantom).add(Items.END_CRYSTAL);

        tag(LootRunesTags.parrot).add(Items.COOKIE).add(Items.MELON_SEEDS);

        tag(LootRunesTags.ocelot).addTag(ItemTags.FISHES);

        tag(LootRunesTags.bogged).add(Items.MUD).add(Items.CLAY_BALL);

        tag(LootRunesTags.glow_squid).add(Items.MOSS_BLOCK).add(Items.SEA_LANTERN);

        tag(LootRunesTags.tropical_fish).add(Items.ORANGE_DYE);

        tag(LootRunesTags.villager).add(Items.EMERALD).add(Items.BREAD);

        tag(LootRunesTags.piglin_brute).add(Items.ANCIENT_DEBRIS).add(Items.GOLD_BLOCK);

        tag(LootRunesTags.piglin).add(Items.GILDED_BLACKSTONE).add(Items.NETHER_GOLD_ORE);

        tag(LootRunesTags.blaze).add(Items.TWISTING_VINES).add(Items.BLAZE_POWDER);

        tag(LootRunesTags.wandering_trader).add(Items.LEAD)
                .add(Items.EMERALD)
                .addTag(ItemTags.COPPER_ORES);

        tag(LootRunesTags.wither).add(Items.WITHER_SKELETON_SKULL);

        tag(LootRunesTags.panda).add(Items.BAMBOO).add(Items.CAKE);

        tag(LootRunesTags.squid).add(Items.BLACK_DYE).add(Items.WATER_BUCKET);

        tag(LootRunesTags.polar_bear).add(Items.COD).add(Items.SNOWBALL);

        tag(LootRunesTags.husk).add(Items.SAND).add(Items.ROTTEN_FLESH);

        tag(LootRunesTags.armadillo).add(Items.IRON_INGOT).add(Items.SHIELD);

        tag(LootRunesTags.giant).add(Items.IRON_SWORD).add(Items.IRON_HELMET);

        tag(LootRunesTags.iron_golem).add(Items.REDSTONE_LAMP).add(Items.REDSTONE_ORE);
    }
}
