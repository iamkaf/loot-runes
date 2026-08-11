package com.iamkaf.lootrunes.fabric.datagen;

//? if >=26.1
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
//? if <26.1
/*import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;*/
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public final class LootRunesLanguageProvider extends FabricLanguageProvider {
    //? if >=26.1
    public LootRunesLanguageProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
    //? if <26.1
    /*public LootRunesLanguageProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {*/
        super(output, lookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translations) {
        translations.add("item.lootrunes.rune_tablet", "Rune Tablet");
        translations.add("creativetab.lootrunes.main", "Loot Runes");
        translations.add("screen.lootrunes.rune_tablet", "Rune Tablet");
        translations.add("screen.lootrunes.active", "Active runes: %s / %s");
        translations.add("screen.lootrunes.click_activate", "Click to activate");
        translations.add("screen.lootrunes.click_deactivate", "Click to deactivate");
        translations.add("message.lootrunes.unlocked", "Rune unlocked: %s");

        rune(translations, "plenty", "Plenty", "Adds one extra roll from the mob's own loot table.", "Available from the start (%s/%s)");
        rune(translations, "sacrifice", "Sacrifice", "Trades the normal drops for two fresh rolls.", "Defeat %2$s mobs (%1$s/%2$s)");
        rune(translations, "echoes", "Echoes", "Repeats one drop remembered from your previous kill.", "Defeat %2$s kinds of mob (%1$s/%2$s)");
        rune(translations, "ascendance", "Ascendance", "Long kill streaks add increasingly many extra rolls.", "Reach a streak of %2$s (%1$s/%2$s)");
        rune(translations, "migration", "Migration", "Changing biome between kills adds another loot roll.", "Hunt in %2$s biomes (%1$s/%2$s)");
        rune(translations, "improvisation", "Improvisation", "Changing finishing weapon between kills adds another loot roll.", "Use %2$s finishing weapons (%1$s/%2$s)");
    }

    private static void rune(TranslationBuilder translations, String id, String name, String description, String unlock) {
        String key = "rune.lootrunes." + id;
        translations.add(key, name);
        translations.add(key + ".description", description);
        translations.add(key + ".unlock", unlock);
    }
}
