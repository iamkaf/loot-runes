package com.iamkaf.lootrunes.neoforge.datagen;

import com.iamkaf.lootrunes.LootRunes;
import com.iamkaf.lootrunes.LootRunesTags;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, LootRunes.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        for (var entity : LootRunesTags.TAGS) {
            add(String.format("tag.item.%s.%s", LootRunes.MOD_ID, entity), entity + " loot rune rewards");
        }
    }
}
