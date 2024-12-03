package com.iamkaf.extendedgolf.neoforge.datagen;

import com.iamkaf.extendedgolf.ExtendedGolf;
import com.iamkaf.extendedgolf.ExtendedGolfTags;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, ExtendedGolf.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        for (var entity : ExtendedGolfTags.TAGS) {
            add(String.format("tag.item.%s.%s", ExtendedGolf.MOD_ID, entity), entity + " golf loot");
        }
    }
}
