package net.googlepox.metallurgy.item;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.googlepox.metallurgy.material.ToolStats;
import net.googlepox.metallurgy.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.*;

public class ModToolTiers {
    public static final Map<String, Tier> TIERS = new HashMap<>();
    public static final List<Object> TIER5_TOOLS = new ArrayList<>() {
    };
    public static final List<Object> TIER6_TOOLS = new ArrayList<>() {
    };

    public static void generateToolTier(String name, ToolStats toolStats){
        Tier newTier;
        int harvestLevel = toolStats.getHarvestLevel();
        if (harvestLevel > 5) {
            newTier = TierSortingRegistry.registerTier(
                    new ForgeTier(harvestLevel, toolStats.getMaxUses(), (float) toolStats.getAttackSpeed(), toolStats.getDamage(), toolStats.getToolMagic(),
                            ModTags.Blocks.NEEDS_TIER6_TOOL, () -> Ingredient.of(MetalRegistry.INGOTS.get(name).get())),
                    new ResourceLocation(Metallurgy.MODID, name), TIER5_TOOLS, List.of());
            TIER6_TOOLS.add(newTier);
        }
        else if (harvestLevel > 4) {
            newTier = TierSortingRegistry.registerTier(
                    new ForgeTier(harvestLevel, toolStats.getMaxUses(), (float) toolStats.getAttackSpeed(), toolStats.getDamage(), toolStats.getToolMagic(),
                            ModTags.Blocks.NEEDS_TIER5_TOOL, () -> Ingredient.of(MetalRegistry.INGOTS.get(name).get())),
                    new ResourceLocation(Metallurgy.MODID, name), List.of(Tiers.NETHERITE), TIER6_TOOLS);
            TIER5_TOOLS.add(newTier);

        }
        else if (harvestLevel > 3) {
            newTier = TierSortingRegistry.registerTier(
                    new ForgeTier(harvestLevel, toolStats.getMaxUses(), (float) toolStats.getAttackSpeed(), toolStats.getDamage(), toolStats.getToolMagic(),
                            Tags.Blocks.NEEDS_NETHERITE_TOOL, () -> Ingredient.of(MetalRegistry.INGOTS.get(name).get())),
                    new ResourceLocation(Metallurgy.MODID, name), List.of(Tiers.DIAMOND), TIER5_TOOLS);
        }
        else if (harvestLevel > 2) {
            newTier = TierSortingRegistry.registerTier(
                    new ForgeTier(harvestLevel, toolStats.getMaxUses(), (float) toolStats.getAttackSpeed(), toolStats.getDamage(), toolStats.getToolMagic(),
                            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(MetalRegistry.INGOTS.get(name).get())),
                    new ResourceLocation(Metallurgy.MODID, name), List.of(Tiers.IRON), List.of(Tiers.NETHERITE));
        }
        else if (harvestLevel > 1) {
            newTier = TierSortingRegistry.registerTier(
                    new ForgeTier(harvestLevel, toolStats.getMaxUses(), (float) toolStats.getAttackSpeed(), toolStats.getDamage(), toolStats.getToolMagic(),
                            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(MetalRegistry.INGOTS.get(name).get())),
                    new ResourceLocation(Metallurgy.MODID, name), List.of(Tiers.STONE), List.of(Tiers.DIAMOND));
        }
        else {
            newTier = TierSortingRegistry.registerTier(
                    new ForgeTier(harvestLevel, toolStats.getMaxUses(), (float) toolStats.getAttackSpeed(), toolStats.getDamage(), toolStats.getToolMagic(),
                            BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(MetalRegistry.INGOTS.get(name).get())),
                    new ResourceLocation(Metallurgy.MODID, name), List.of(), List.of(Tiers.IRON));
        }
        TIERS.put(name, newTier);

    }
}