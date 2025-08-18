package net.googlepox.metallurgy.datagen;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.googlepox.metallurgy.util.Utils;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String modid, String enUs) {
        super(output, modid, enUs);

    }

    @Override
    protected void addTranslations() {
        this.add("creativetab.metallurgy_tab", "Metallurgy Tab");

        this.add("block.metallurgy.bitumen_block", "Block of Bitumen");

        this.add("block.metallurgy.gold_bricks", "Gold Bricks");
        this.add("block.metallurgy.copper_bricks", "Copper Bricks");
        this.add("block.metallurgy.iron_bricks", "Iron Bricks");

        this.add("block.metallurgy.phosphorite_ore", "Phosphorite Ore");
        this.add("block.metallurgy.phosphorite_ore_deepslate", "Deepslate Phosphorite Ore");
        this.add("block.metallurgy.phosphorite_ore_nether", "Nether Phosphorite Ore");
        this.add("block.metallurgy.phosphorite_ore_end_stone", "Endstone Phosphorite Ore");

        this.add("block.metallurgy.sulfur_ore", "Sulfur Ore");
        this.add("block.metallurgy.sulfur_ore_deepslate", "Deepslate Sulfur Ore");
        this.add("block.metallurgy.sulfur_ore_nether", "Nether Sulfur Ore");
        this.add("block.metallurgy.sulfur_ore_end_stone", "Endstone Sulfur Ore");

        this.add("block.metallurgy.tar_ore", "Tar");
        this.add("block.metallurgy.tar_ore_deepslate", "Deepslate Tar");
        this.add("block.metallurgy.tar_ore_nether", "Nether Tar");
        this.add("block.metallurgy.tar_ore_end_stone", "Endstone Tar");

        this.add("block.metallurgy.potash_ore", "Potash Ore");
        this.add("block.metallurgy.potash_ore_deepslate", "Deepslate Potash Ore");
        this.add("block.metallurgy.potash_ore_nether", "Nether Potash Ore");
        this.add("block.metallurgy.potash_ore_end_stone", "Endstone Potash Ore");

        this.add("block.metallurgy.crusher", "Crusher");

        this.add("item.metallurgy.bitumen", "Bitumen");
        this.add("item.metallurgy.potash", "Potash");
        this.add("item.metallurgy.potash_dust", "Potash Dust");
        this.add("item.metallurgy.copper_dust", "Copper Dust");
        this.add("item.metallurgy.iron_dust", "Iron Dust");
        this.add("item.metallurgy.gold_dust", "Gold Dust");
        this.add("item.metallurgy.phosphorus", "Phosphorus");
        this.add("item.metallurgy.sulfur", "Sulfur");
        this.add("item.metallurgy.tar", "Tar");

        Metallurgy.METALS.keySet().forEach(name -> {
            String translatedName = name;
            if (translatedName.contains("_")) {
                String[] splitString = name.split("_");
                translatedName = Utils.capitalize(splitString[0]) + " " + Utils.capitalize(splitString[1]);
            }

            this.add("item.metallurgy." + name + "_ingot", Utils.capitalize(translatedName) + " Ingot");
            this.add("item.metallurgy." + name + "_nugget", Utils.capitalize(translatedName) + " Nugget");
            this.add("item.metallurgy." + name + "_dust", Utils.capitalize(translatedName) + " Dust");
            this.add("item.metallurgy." + name + "_raw", "Raw " + Utils.capitalize(translatedName));

            this.add("item.metallurgy." + name + "_pickaxe", Utils.capitalize(translatedName) + " Pickaxe");
            this.add("item.metallurgy." + name + "_sword", Utils.capitalize(translatedName) + " Sword");
            this.add("item.metallurgy." + name + "_axe", Utils.capitalize(translatedName) + " Axe");
            this.add("item.metallurgy." + name + "_shovel", Utils.capitalize(translatedName) + " Shovel");
            this.add("item.metallurgy." + name + "_hoe", Utils.capitalize(translatedName) + " Hoe");

            this.add("block.metallurgy." + name + "_ore", Utils.capitalize(translatedName) + " Ore");
            this.add("block.metallurgy." + name + "_ore_deepslate", "Deepslate " + Utils.capitalize(translatedName) + " Ore");
            this.add("block.metallurgy." + name + "_ore_nether", "Nether " + Utils.capitalize(translatedName) + " Ore");
            this.add("block.metallurgy." + name + "_ore_end_stone", "Endstone " + Utils.capitalize(translatedName) + " Ore");

            this.add("block.metallurgy." + name + "_block", "Block of " + Utils.capitalize(translatedName));
            this.add("block.metallurgy." + name + "_bricks", Utils.capitalize(translatedName) + " Bricks");

            this.add("fluid_type.metallurgy.molten_" + name, "Molten " + Utils.capitalize(translatedName));
        });
    }
}
