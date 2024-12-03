package com.iamkaf.extendedgolf;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.HashSet;
import java.util.Set;

public class ExtendedGolfTags {
    public static final Set<String> TAGS = new HashSet<>();

    public static final TagKey<Item> silverfish = createItemTag("silverfish");
    public static final TagKey<Item> vex = createItemTag("vex");
    public static final TagKey<Item> warden = createItemTag("warden");
    public static final TagKey<Item> slime = createItemTag("slime");
    public static final TagKey<Item> ghast = createItemTag("ghast");
    public static final TagKey<Item> zombie = createItemTag("zombie");
    public static final TagKey<Item> tadpole = createItemTag("tadpole");
    public static final TagKey<Item> cat = createItemTag("cat");
    public static final TagKey<Item> illusioner = createItemTag("illusioner");
    public static final TagKey<Item> magma_cube = createItemTag("magma_cube");
    public static final TagKey<Item> mule = createItemTag("mule");
    public static final TagKey<Item> camel = createItemTag("camel");
    public static final TagKey<Item> hoglin = createItemTag("hoglin");
    public static final TagKey<Item> witch = createItemTag("witch");
    public static final TagKey<Item> skeleton_horse = createItemTag("skeleton_horse");
    public static final TagKey<Item> zombified_piglin = createItemTag("zombified_piglin");
    public static final TagKey<Item> chicken = createItemTag("chicken");
    public static final TagKey<Item> skeleton = createItemTag("skeleton");
    public static final TagKey<Item> enderman = createItemTag("enderman");
    public static final TagKey<Item> shulker = createItemTag("shulker");
    public static final TagKey<Item> snow_golem = createItemTag("snow_golem");
    public static final TagKey<Item> ender_dragon = createItemTag("ender_dragon");
    public static final TagKey<Item> ravager = createItemTag("ravager");
    public static final TagKey<Item> llama = createItemTag("llama");
    public static final TagKey<Item> horse = createItemTag("horse");
    public static final TagKey<Item> player = createItemTag("player");
    public static final TagKey<Item> cod = createItemTag("cod");
    public static final TagKey<Item> mooshroom = createItemTag("mooshroom");
    public static final TagKey<Item> cow = createItemTag("cow");
    public static final TagKey<Item> pig = createItemTag("pig");
    public static final TagKey<Item> donkey = createItemTag("donkey");
    public static final TagKey<Item> goat = createItemTag("goat");
    public static final TagKey<Item> endermite = createItemTag("endermite");
    public static final TagKey<Item> guardian = createItemTag("guardian");
    public static final TagKey<Item> salmon = createItemTag("salmon");
    public static final TagKey<Item> sheep = createItemTag("sheep");
    public static final TagKey<Item> pillager = createItemTag("pillager");
    public static final TagKey<Item> sniffer = createItemTag("sniffer");
    public static final TagKey<Item> frog = createItemTag("frog");
    public static final TagKey<Item> zoglin = createItemTag("zoglin");
    public static final TagKey<Item> cave_spider = createItemTag("cave_spider");
    public static final TagKey<Item> wither_skeleton = createItemTag("wither_skeleton");
    public static final TagKey<Item> rabbit = createItemTag("rabbit");
    public static final TagKey<Item> strider = createItemTag("strider");
    public static final TagKey<Item> pufferfish = createItemTag("pufferfish");
    public static final TagKey<Item> axolotl = createItemTag("axolotl");
    public static final TagKey<Item> armor_stand = createItemTag("armor_stand");
    public static final TagKey<Item> elder_guardian = createItemTag("elder_guardian");
    public static final TagKey<Item> creeper = createItemTag("creeper");
    public static final TagKey<Item> allay = createItemTag("allay");
    public static final TagKey<Item> stray = createItemTag("stray");
    public static final TagKey<Item> dolphin = createItemTag("dolphin");
    public static final TagKey<Item> spider = createItemTag("spider");
    public static final TagKey<Item> zombie_horse = createItemTag("zombie_horse");
    public static final TagKey<Item> drowned = createItemTag("drowned");
    public static final TagKey<Item> bat = createItemTag("bat");
    public static final TagKey<Item> trader_llama = createItemTag("trader_llama");
    public static final TagKey<Item> turtle = createItemTag("turtle");
    public static final TagKey<Item> vindicator = createItemTag("vindicator");
    public static final TagKey<Item> evoker = createItemTag("evoker");
    public static final TagKey<Item> zombie_villager = createItemTag("zombie_villager");
    public static final TagKey<Item> fox = createItemTag("fox");
    public static final TagKey<Item> breeze = createItemTag("breeze");
    public static final TagKey<Item> bee = createItemTag("bee");
    public static final TagKey<Item> wolf = createItemTag("wolf");
    public static final TagKey<Item> phantom = createItemTag("phantom");
    public static final TagKey<Item> parrot = createItemTag("parrot");
    public static final TagKey<Item> ocelot = createItemTag("ocelot");
    public static final TagKey<Item> bogged = createItemTag("bogged");
    public static final TagKey<Item> glow_squid = createItemTag("glow_squid");
    public static final TagKey<Item> tropical_fish = createItemTag("tropical_fish");
    public static final TagKey<Item> villager = createItemTag("villager");
    public static final TagKey<Item> piglin_brute = createItemTag("piglin_brute");
    public static final TagKey<Item> piglin = createItemTag("piglin");
    public static final TagKey<Item> blaze = createItemTag("blaze");
    public static final TagKey<Item> wandering_trader = createItemTag("wandering_trader");
    public static final TagKey<Item> wither = createItemTag("wither");
    public static final TagKey<Item> panda = createItemTag("panda");
    public static final TagKey<Item> squid = createItemTag("squid");
    public static final TagKey<Item> polar_bear = createItemTag("polar_bear");
    public static final TagKey<Item> husk = createItemTag("husk");
    public static final TagKey<Item> armadillo = createItemTag("armadillo");
    public static final TagKey<Item> giant = createItemTag("giant");
    public static final TagKey<Item> iron_golem = createItemTag("iron_golem");

    public static TagKey<Item> createItemTag(String name) {
        TAGS.add(name);
        return TagKey.create(Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath(ExtendedGolf.MOD_ID, name)
        );
    }
}