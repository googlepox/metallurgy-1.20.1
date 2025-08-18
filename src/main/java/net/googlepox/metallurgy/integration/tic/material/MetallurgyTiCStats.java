package net.googlepox.metallurgy.integration.tic.material;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.googlepox.metallurgy.item.ModToolTiers;
import net.googlepox.metallurgy.material.MetalStats;
import net.googlepox.metallurgy.material.ToolStats;
import net.googlepox.metallurgy.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.materials.definition.Material;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.tools.stats.*;

import java.util.HashMap;
import java.util.Map;

public class MetallurgyTiCStats {

    public final MetalStats metal;

    public final IMaterialStats stats;

    public static final Map<String, MaterialId> MATERIALS = new HashMap<>();

    public MetallurgyTiCStats(MetalStats metal, IMaterialStats stats)
    {
        this.metal = metal;
        this.stats = stats;
        if (metal != null)
        {
            //TinkerMetals.metalStatsList.add(this);
        }
    }

    public static Tier getTier(MetalStats metal) {
        if (metal.getToolStats() != null) {
            String name = metal.getName();
            int harvestLevel = metal.getToolStats().getHarvestLevel();
            if (harvestLevel > 4) {
                return ModToolTiers.TIERS.get(name);
            }
            else if (harvestLevel > 3) {
                return Tiers.NETHERITE;
            }
            else if (harvestLevel > 2) {
                return Tiers.DIAMOND;
            }
            else if (harvestLevel > 1) {
                return Tiers.IRON;
            }
            else {
                return Tiers.STONE;
            }
        }

        return Tiers.STONE;
    }


    public static HeadMaterialStats getHeadA(MetalStats metal)
    {
        if (metal.getToolStats() != null) {
            int durability = metal.getToolStats().getMaxUses();
            float speed = metal.getToolStats().getEfficiency();
            float attack = metal.getToolStats().getDamage();
            int harvestL = metal.getToolStats().getHarvestLevel();
            Tier tier = getTier(metal);

            return new HeadMaterialStats(durability / 2, speed, tier, attack + 1.25f);
        }

        return new HeadMaterialStats(1,1, getTier(metal),1);
    }

    public static HandleMaterialStats getHandleA(MetalStats metal)
    {
        if (metal.getToolStats() != null) {
            int durability = metal.getToolStats().getMaxUses();
            float speed = metal.getToolStats().getEfficiency();
            float attackSpeed = (float) metal.getToolStats().getAttackSpeed();
            float damage = metal.getToolStats().getDamage();

            float multiplier = 0.07F;
            float modifier = (float) (Math.sqrt(durability) * multiplier);

            return new HandleMaterialStats(modifier > 2 ? modifier * 0.5F : durability / 4.0f, speed * modifier, attackSpeed * modifier, damage * modifier);
        }

        return new HandleMaterialStats(1,1, 1,1);
    }

    public static LimbMaterialStats getLimbA(MetalStats metal)
    {
        if (metal.getToolStats() != null) {
            final int MAX_SPEED = 27;

            int durability = metal.getToolStats().getMaxUses();
            float drawspeed = (float) ((MAX_SPEED - metal.getToolStats().getEfficiency()) / 12.3);
            float range = metal.getToolStats().getEfficiency() / 12;
            float bonusdamage = metal.getToolStats().getDamage() / 8;

            return new LimbMaterialStats(durability, drawspeed, range, bonusdamage);
        }

        return new LimbMaterialStats(1,1, 1,1);
    }

    public static GripMaterialStats getGripA(MetalStats metal)
    {
        if (metal.getToolStats() != null) {
            final int MAX_SPEED = 27;

            int durability = metal.getToolStats().getMaxUses();
            float accuracy = (float) ((MAX_SPEED - metal.getToolStats().getEfficiency()) / 12.3);
            float bonusdamage = metal.getToolStats().getDamage() / 8;

            return new GripMaterialStats(durability, accuracy, bonusdamage);
        }

        return new GripMaterialStats(1,1, 1);
    }

}