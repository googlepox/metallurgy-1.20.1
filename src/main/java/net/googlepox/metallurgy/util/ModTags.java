package net.googlepox.metallurgy.util;

import net.googlepox.metallurgy.Metallurgy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.HashMap;
import java.util.Map;

public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> NEEDS_TIER5_TOOL = tag("needs_tier5_tool");
        public static final TagKey<Block> NEEDS_TIER6_TOOL = tag("needs_tier6_tool");


        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Metallurgy.MODID, name));
        }
    }

    public static class Items {

        public static Map<String, TagKey<Item>> ingotTags = new HashMap<>();
        public static Map<String, TagKey<Item>> nuggetTags = new HashMap<>();
        public static Map<String, TagKey<Item>> dustTags = new HashMap<>();
        public static Map<String, TagKey<Item>> rawTags = new HashMap<>();

        public static final Map<TagKey<Item>, String> TAG_TO_METAL = new HashMap<>();


        public static void createTags() {
            Metallurgy.METALS.forEach((name, stats) -> {
                TagKey<Item> item = createMetalTag("ingots/" + name);
               ingotTags.put(name, item);
               TAG_TO_METAL.put(item, name);
               item = createMetalTag("nuggets/" + name);
               nuggetTags.put(name, item);
               TAG_TO_METAL.put(item, name);
               item = createMetalTag("dusts/" + name);
               dustTags.put(name, item);
               TAG_TO_METAL.put(item, name);
               item = createMetalTag("raw_materials/" + name);
               rawTags.put(name, item);
               TAG_TO_METAL.put(item, name);
            });
            dustTags.put("iron_dust", createMetalTag("dusts/iron"));
            dustTags.put("copper_dust", createMetalTag("dusts/copper"));
            dustTags.put("gold_dust", createMetalTag("dusts/gold"));

        }

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(Metallurgy.MODID, name));
        }

        private static TagKey<Item> createMetalTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Fluids {
        public static Map<String, TagKey<Fluid>> fluidTags = new HashMap<>();

        public static void createTags() {
            Metallurgy.METALS.forEach((name, stats) -> {
                TagKey<Fluid> fluid = tag(name);
                fluidTags.put(name, fluid);
            });
        }

        private static TagKey<Fluid> tag(String name) {
            return FluidTags.create(new ResourceLocation(Metallurgy.MODID, name));
        }
    }
}