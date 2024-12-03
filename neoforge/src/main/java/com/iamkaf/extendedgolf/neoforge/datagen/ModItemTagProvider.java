package com.iamkaf.extendedgolf.neoforge.datagen;

import com.iamkaf.extendedgolf.ExtendedGolf;
import com.iamkaf.extendedgolf.ExtendedGolfTags;
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
        super(arg, completableFuture, completableFuture2, ExtendedGolf.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ExtendedGolfTags.silverfish).add(Items.ANDESITE)
                .add(Items.DIORITE)
                .add(Items.GRANITE)
                .add(Items.COBBLESTONE);

        tag(ExtendedGolfTags.vex).add(Items.GHAST_TEAR).add(Items.SPECTRAL_ARROW).add(Items.GLOW_INK_SAC);

        tag(ExtendedGolfTags.warden).add(Items.ECHO_SHARD).add(Items.MUSIC_DISC_5);

        tag(ExtendedGolfTags.slime).add(Items.LIME_WOOL)
                .add(Items.LIME_CARPET)
                .add(Items.LIME_CANDLE)
                .add(Items.LIME_GLAZED_TERRACOTTA)
                .add(Items.SLIME_BLOCK)
                .add(Items.LIME_DYE);

        tag(ExtendedGolfTags.ghast).add(Items.CRYING_OBSIDIAN)
                .add(Items.QUARTZ_BLOCK)
                .add(Items.SOUL_LANTERN);

        tag(ExtendedGolfTags.zombie).add(Items.DIRT);

        tag(ExtendedGolfTags.tadpole).add(Items.SEAGRASS).add(Items.LILY_PAD).add(Items.KELP);

        tag(ExtendedGolfTags.cat).addTag(ItemTags.FISHES).add(Items.STRING);

        tag(ExtendedGolfTags.illusioner).add(Items.CARVED_PUMPKIN);

        tag(ExtendedGolfTags.magma_cube).add(Items.NETHER_BRICKS);

        tag(ExtendedGolfTags.mule).add(Items.SADDLE).add(Items.CHEST);

        tag(ExtendedGolfTags.camel).add(Items.CACTUS).add(Items.DEAD_BUSH);

        tag(ExtendedGolfTags.hoglin).add(Items.COOKED_PORKCHOP);

        tag(ExtendedGolfTags.witch).add(Items.GLOWSTONE).add(Items.BREWING_STAND);

        tag(ExtendedGolfTags.skeleton_horse).add(Items.SKELETON_SKULL);

        tag(ExtendedGolfTags.zombified_piglin).add(Items.GOLD_INGOT).add(Items.ROTTEN_FLESH);

        tag(ExtendedGolfTags.chicken).add(Items.EGG).add(Items.FEATHER).add(Items.COOKED_CHICKEN);

        tag(ExtendedGolfTags.skeleton).add(Items.BONE_MEAL).add(Items.ARROW);

        // lol
        // tag(ExtendedGolfTags.enderman).add(Items.ENDER_PEARL).add(Items.ENDER_EYE);

        tag(ExtendedGolfTags.shulker).add(Items.PURPUR_BLOCK)
                .add(Items.PURPUR_PILLAR)
                .add(Items.PURPUR_SLAB)
                .add(Items.PURPUR_STAIRS);

        tag(ExtendedGolfTags.snow_golem).add(Items.CARVED_PUMPKIN);

        // lol
        // tag(ExtendedGolfTags.ender_dragon).add(Items.DRAGON_BREATH).add(Items.END_CRYSTAL);

        tag(ExtendedGolfTags.ravager).add(Items.SADDLE).add(Items.EMERALD).add(Items.BAMBOO_CHEST_RAFT);

        tag(ExtendedGolfTags.llama).addTag(ItemTags.WOOL_CARPETS).add(Items.LEAD);

        tag(ExtendedGolfTags.horse).add(Items.HAY_BLOCK).addTag(ItemTags.HORSE_TEMPT_ITEMS);

        // lol
        // tag(ExtendedGolfTags.player).add(Items.DIAMOND_SWORD).add(Items.SHIELD);

        tag(ExtendedGolfTags.cod).add(Items.SEAGRASS);

        tag(ExtendedGolfTags.mooshroom).add(Items.RED_MUSHROOM).add(Items.BOWL).add(Items.MYCELIUM);

        tag(ExtendedGolfTags.cow).add(Items.MILK_BUCKET).add(Items.LEATHER);

        tag(ExtendedGolfTags.pig).add(Items.PORKCHOP).add(Items.CARROT);

        tag(ExtendedGolfTags.donkey).add(Items.CHEST).add(Items.SADDLE);

        tag(ExtendedGolfTags.goat).add(Items.GOAT_HORN).add(Items.WHEAT);

        tag(ExtendedGolfTags.endermite).add(Items.CHORUS_FRUIT);

        tag(ExtendedGolfTags.guardian).add(Items.PRISMARINE_SHARD).add(Items.SPONGE);

        tag(ExtendedGolfTags.salmon).add(Items.SEAGRASS);

        tag(ExtendedGolfTags.sheep).addTag(ItemTags.WOOL);

        tag(ExtendedGolfTags.pillager).addTag(ItemTags.WOOL);

        tag(ExtendedGolfTags.sniffer).addTag(ItemTags.SNIFFER_FOOD).add(Items.FLOWER_POT);

        tag(ExtendedGolfTags.frog).add(Items.OCHRE_FROGLIGHT).addTag(ItemTags.FROG_FOOD);

        tag(ExtendedGolfTags.zoglin).add(Items.COOKED_PORKCHOP);

        tag(ExtendedGolfTags.cave_spider).add(Items.COBWEB);

        tag(ExtendedGolfTags.wither_skeleton).add(Items.SOUL_SAND).add(Items.COAL);

        tag(ExtendedGolfTags.rabbit).add(Items.SAND).add(Items.GOLDEN_CARROT);

        tag(ExtendedGolfTags.strider).add(Items.WARPED_FUNGUS).add(Items.SADDLE);

        tag(ExtendedGolfTags.pufferfish).add(Items.GLASS_BOTTLE);

        tag(ExtendedGolfTags.axolotl).add(Items.AZALEA).add(Items.TROPICAL_FISH);

        // lol
        // tag(ExtendedGolfTags.armor_stand).add(Items.STICK).add(Items.SMOOTH_STONE_SLAB);

        tag(ExtendedGolfTags.elder_guardian).add(Items.PRISMARINE_CRYSTALS).add(Items.TRIDENT);

        // lol
        // tag(ExtendedGolfTags.creeper).add(Items.GUNPOWDER).add(Items.TNT);

        tag(ExtendedGolfTags.allay).add(Items.MUSIC_DISC_OTHERSIDE).add(Items.AMETHYST_SHARD);

        tag(ExtendedGolfTags.stray).add(Items.ICE).add(Items.GRAY_DYE);

        tag(ExtendedGolfTags.dolphin).add(Items.COD).add(Items.SEAGRASS);

        tag(ExtendedGolfTags.spider).add(Items.FERMENTED_SPIDER_EYE).add(Items.RED_DYE).add(Items.BLUE_DYE);

        tag(ExtendedGolfTags.zombie_horse).add(Items.HAY_BLOCK);

        tag(ExtendedGolfTags.drowned).add(Items.NAUTILUS_SHELL).add(Items.TRIDENT);

        tag(ExtendedGolfTags.bat).add(Items.COAL).add(Items.STRING);

        tag(ExtendedGolfTags.trader_llama).add(Items.LEAD).addTag(ItemTags.WOOL_CARPETS);

        tag(ExtendedGolfTags.turtle).add(Items.TURTLE_EGG);

        tag(ExtendedGolfTags.vindicator).add(Items.IRON_AXE).add(Items.LAPIS_BLOCK);

        tag(ExtendedGolfTags.evoker).add(Items.TOTEM_OF_UNDYING).add(Items.BOOK);

        tag(ExtendedGolfTags.zombie_villager).add(Items.COMPASS);

        tag(ExtendedGolfTags.fox).add(Items.SWEET_BERRIES).add(Items.JUKEBOX);

        tag(ExtendedGolfTags.breeze).add(Items.FEATHER).add(Items.PHANTOM_MEMBRANE);

        tag(ExtendedGolfTags.bee).addTag(ItemTags.FLOWERS);

        tag(ExtendedGolfTags.wolf).add(Items.BONE).add(Items.RABBIT_FOOT);

        tag(ExtendedGolfTags.phantom).add(Items.END_CRYSTAL);

        tag(ExtendedGolfTags.parrot).add(Items.COOKIE).add(Items.MELON_SEEDS);

        tag(ExtendedGolfTags.ocelot).addTag(ItemTags.FISHES);

        tag(ExtendedGolfTags.bogged).add(Items.MUD).add(Items.CLAY_BALL);

        tag(ExtendedGolfTags.glow_squid).add(Items.MOSS_BLOCK).add(Items.SEA_LANTERN);

        tag(ExtendedGolfTags.tropical_fish).add(Items.ORANGE_DYE);

        tag(ExtendedGolfTags.villager).add(Items.EMERALD).add(Items.BREAD);

        tag(ExtendedGolfTags.piglin_brute).add(Items.ANCIENT_DEBRIS).add(Items.GOLD_BLOCK);

        tag(ExtendedGolfTags.piglin).add(Items.GILDED_BLACKSTONE).add(Items.NETHER_GOLD_ORE);

        tag(ExtendedGolfTags.blaze).add(Items.TWISTING_VINES).add(Items.BLAZE_POWDER);

        tag(ExtendedGolfTags.wandering_trader).add(Items.LEAD)
                .add(Items.EMERALD)
                .addTag(ItemTags.COPPER_ORES);

        tag(ExtendedGolfTags.wither).add(Items.WITHER_SKELETON_SKULL);

        tag(ExtendedGolfTags.panda).add(Items.BAMBOO).add(Items.CAKE);

        tag(ExtendedGolfTags.squid).add(Items.BLACK_DYE).add(Items.WATER_BUCKET);

        tag(ExtendedGolfTags.polar_bear).add(Items.COD).add(Items.SNOWBALL);

        tag(ExtendedGolfTags.husk).add(Items.SAND).add(Items.ROTTEN_FLESH);

        tag(ExtendedGolfTags.armadillo).add(Items.IRON_INGOT).add(Items.SHIELD);

        tag(ExtendedGolfTags.giant).add(Items.IRON_SWORD).add(Items.IRON_HELMET);

        tag(ExtendedGolfTags.iron_golem).add(Items.REDSTONE_LAMP).add(Items.REDSTONE_ORE);
    }
}
