package net.googlepox.metallurgy.util;

import net.googlepox.metallurgy.material.ArmorStats;
import net.googlepox.metallurgy.material.OreStats;
import net.googlepox.metallurgy.material.ToolStats;
import net.googlepox.metallurgy.material.MetalStats;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Constants {

    public static final MetalStats EMPTY_METAL_STATS = new MetalStats("", 0, 0, 0, Blocks.IRON_ORE,
            new ArmorStats("", 0, new int[4], 0, SoundEvents.ARMOR_EQUIP_IRON, 0, 0, () -> Ingredient.of(Items.IRON_INGOT)),
            new ToolStats(0, 0, 0, 0, 0),
            new OreStats(false, 0, 0, 0, 0),
            0, -1);

    public static final String[] METAL_NAMES = new String[]{
            "adamantine", "alduorite", "amordrine", "angmallen", "astral_silver", "atlarus", "black_steel",
            "brass", "bronze", "carmot", "celenegil", "ceruclase", "damascus_steel", "deep_iron",
            "desichalkos", "electrum",
            "etherium", "eximite", "haderoth", "hepatizon", "ignatius", "infuscolium", "inolashite", "kalendrite",
            "krik", "lemurite",
            "lutetium", "manganese", "meutoite", "midasium", "mithril", "orichalcum", "osmium", "oureclase", "platinum",
            "prometheum",
            "quicksilver", "rubracium", "sanguinite", "shadow_iron", "shadow_steel", "silver", "steel", "tartarite",
            "tin", "vulcanite", "vyroxeres", "zinc",
    };
    public static final int[] METAL_TIERS = new int[]{
            6, 6, 4, 2, 3, 6, 2, 1, 1, 5, 4, 4, 1, 1, 5, 2, 6, 4, 5, 2, 3, 2, 6, 4, 5, 5, 6, 2, 5, 4, 5, 4, 2, 3, 4,
            1, 3, 3, 5, 3, 5, 2, 2, 6, 1, 4, 5, 1,
    };
    public static final Object2IntMap<String> TIER_MAP = new Object2IntArrayMap<>(METAL_NAMES, METAL_TIERS, METAL_NAMES.length);

    //Vanilla Metals
    public static final String METAL_IRON = "iron";
    public static final String METAL_GOLD = "gold";
    public static final String METAL_COPPER = "copper";


    //Enchantments
    public static final Enchantment[] GAUNTLET_ENCHANTMENTS = {
            Enchantments.BANE_OF_ARTHROPODS,
            Enchantments.MENDING,
            Enchantments.SMITE,
            Enchantments.VANISHING_CURSE,
            Enchantments.UNBREAKING,
            Enchantments.SHARPNESS
    };


    /**
     * Code constants: Tool Categories
     */
    public static final class Tools {

        public static final String AXE = "axe";
        public static final String HOE = "hoe";
        public static final String PICKAXE = "pickaxe";
        public static final String SHOVEL = "shovel";
        public static final String SWORD = "sword";

    }

    /**
     * Blast Resistance Constants<br>
     * Disclaimer: These variables might need a balance update
     * <br><br>
     * (Davoleo isn't responsible for any blast resistance level complains)
     */
    public static final class BlastResistance {

        public static final float LOW_TIER = 6F;                   //or maybe 3, I don't remember
        public static final float MID_TIER = 10F;                  //Cobblestone Level
        public static final float HIGH_TIER = 15F;
        public static final float EXTREME_TIER = 20F;              //Obsidian Level
        public static final float UNBREAKABLE_TIER = 18000000F;    //Bedrock Level

    }

}