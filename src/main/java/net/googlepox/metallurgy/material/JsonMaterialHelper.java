package net.googlepox.metallurgy.material;

import com.google.gson.*;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.googlepox.metallurgy.util.Constants;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class JsonMaterialHelper {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static final String DEFAULT_CONFIG = "src/main/resources/assets/metallurgy/materials.json";

    /**
     * Reads the JSON config and creates a new MetalStats object for each entry of the JSON Array
     *
     * @param resourcePath The path to the JSON Config
     * @param defaultStats used as fallback when reading user-customized JSON config,
     */
    public static Set<MetalStats> readConfig(String resourcePath, Set<MetalStats> defaultStats) throws JsonSyntaxException
    {
        Set<MetalStats> metalStats = new HashSet<>();

        try
        {
            Path path = Paths.get(Metallurgy.class.getClassLoader()
                    .getResource("assets/metallurgy/materials.json").toURI());

            BufferedReader reader = Files.newBufferedReader(path);
            JsonArray materials = GsonHelper.fromJson(gson, reader, JsonArray.class);

            if (materials != null)
            {
                materials.forEach(jsonElement -> {
                    JsonObject jsonMetal = jsonElement.isJsonObject() ? jsonElement.getAsJsonObject() : null;

                    if (jsonMetal != null)
                    {
                        MetalStats metalStat = readMetalFromJson(jsonMetal, defaultStats);
                        metalStats.add(metalStat);
                    }
                });
            }
        }
        catch (IOException e)
        {

        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return metalStats;
    }

    private static MetalStats readMetalFromJson(JsonObject metalObj, Set<MetalStats> defaultStats) throws JsonSyntaxException
    {

        if (!metalObj.has("name"))
            return null;

        String name = GsonHelper.getAsString(metalObj, "name");

        MetalStats defaultStat = getMetalStatsByName(name, defaultStats);

        float hardness = GsonHelper.getAsFloat(metalObj, "hardness", defaultStat.getStrength());
        float blockBlastResistance = GsonHelper.getAsFloat(metalObj, "blast_resistance", defaultStat.getStrength());
        int oreHarvest = GsonHelper.getAsInt(metalObj, "ore_harvest_level", defaultStat.getOreHarvest());
        int color = Integer.parseInt(GsonHelper.getAsString(metalObj, "color", String.valueOf(defaultStat.getColorHex())), 16);
        int temperature = GsonHelper.getAsInt(metalObj, "temperature", defaultStat.getTemperature());

        ArmorStats armorStats = getArmorStats(name, metalObj, defaultStat.getArmorStats());
        ToolStats toolStats = getToolStats(name, metalObj, defaultStat.getToolStats());
        OreStats oreStats = getOreStats(name, metalObj, defaultStat.getOreStats());

        return new MetalStats(name, hardness, blockBlastResistance, oreHarvest, Blocks.IRON_ORE, armorStats, toolStats, oreStats, color, temperature);
    }

    private static MetalStats getMetalStatsByName(String name, Set<MetalStats> defaultStats) throws JsonSyntaxException
    {

        if (defaultStats != null)
        {
            for (MetalStats stat : defaultStats)
            {
                if (stat.getName().equals(name))
                {
                    return stat;
                }
            }
        }

        return Constants.EMPTY_METAL_STATS;
    }

    private static ArmorStats getArmorStats(String name, JsonObject metalStats, ArmorStats fallback) throws JsonSyntaxException
    {

        if (metalStats.has("armor_stats"))
        {
            JsonObject armorStatsObj = GsonHelper.getAsJsonObject(metalStats, "armor_stats");

            int enchantability = GsonHelper.getAsInt(armorStatsObj, "enchantability", fallback.getEnchantmentValue());
            int durability = GsonHelper.getAsInt(armorStatsObj, "durability", fallback.getDurability());
            float toughness = GsonHelper.getAsFloat(armorStatsObj, "toughness", fallback.getToughness());
            int[] damageReduction = getAsIntArray(armorStatsObj, "damage_reduction", fallback.getProtectionAmounts());
            float knockbackResistance = GsonHelper.getAsFloat(armorStatsObj, "knockback_resistance", (float) fallback.getKnockbackResistance());

            ArmorStats armorStats = new ArmorStats(name, durability, damageReduction, enchantability, SoundEvents.ARMOR_EQUIP_IRON, toughness, knockbackResistance, () -> Ingredient.of(MetalRegistry.INGOTS.get(name).get()));
            return armorStats;
        }

        return null;
    }

    private static ToolStats getToolStats(String name, JsonObject metalStats, ToolStats fallback) throws JsonSyntaxException
    {

        if (metalStats.has("tool_stats"))
        {
            JsonObject toolStatsObj = GsonHelper.getAsJsonObject(metalStats, "tool_stats");

            float efficiency = GsonHelper.getAsFloat(toolStatsObj, "efficiency", fallback.getEfficiency());
            int harvestLevel = GsonHelper.getAsInt(toolStatsObj, "harvest_level", fallback.getHarvestLevel());
            int enchantability = GsonHelper.getAsInt(toolStatsObj, "enchantability", fallback.getToolMagic());
            int durability = GsonHelper.getAsInt(toolStatsObj, "durability", fallback.getMaxUses());
            float damage = GsonHelper.getAsFloat(toolStatsObj, "damage", fallback.getDamage());

            double maxHealth = GsonHelper.getAsFloat(toolStatsObj, "max_health", (float) fallback.getMaxHealth());
            double movementSpeed = GsonHelper.getAsFloat(toolStatsObj, "movement_speed", (float) fallback.getMovementSpeed());
            double attackDamage = GsonHelper.getAsFloat(toolStatsObj, "attack_damage", (float) fallback.getAttackDamageAttribute());
            double attackSpeed = GsonHelper.getAsFloat(toolStatsObj, "attack_speed", (float) fallback.getAttackSpeed());
            double reachDistance = GsonHelper.getAsFloat(toolStatsObj, "reach_distance", (float) fallback.getReachDistance());

            ToolStats toolStats = new ToolStats(enchantability, harvestLevel, durability, efficiency, damage);
            toolStats.setAttributes(maxHealth, movementSpeed, attackDamage, attackSpeed, reachDistance);
            return toolStats;
        }

        return null;
    }

    private static OreStats getOreStats(String name, JsonObject metalStats, OreStats fallback) throws JsonSyntaxException
    {

        if (metalStats.has("ore_stats"))
        {
            JsonObject oreStatsObj = GsonHelper.getAsJsonObject(metalStats, "ore_stats");

            boolean hasOre = GsonHelper.getAsBoolean(oreStatsObj, "hasOre", fallback.getHasOre());
            int veinSize = GsonHelper.getAsInt(oreStatsObj, "veinSize", fallback.getVeinSize());
            int veinsPerChunk = GsonHelper.getAsInt(oreStatsObj, "veinsPerChunk", fallback.getVeinsPerChunk());
            int minY = GsonHelper.getAsInt(oreStatsObj, "minY", fallback.getMinY());
            int maxY = GsonHelper.getAsInt(oreStatsObj, "maxY", fallback.getMaxY());

            return new OreStats(hasOre, veinSize, veinsPerChunk, minY, maxY);
        }

        return null;
    }

    private static int[] getAsIntArray(JsonObject json, String memberName, int[] fallback) throws JsonSyntaxException
    {

        int[] arr = new int[4];

        if (json.has(memberName))
        {
            JsonArray jsonArray = json.getAsJsonArray(memberName);

            for (int i = 0; i < arr.length; i++)
            {
                JsonElement element = jsonArray.get(i);
                if (GsonHelper.isNumberValue(element))
                {
                    arr[i] = element.getAsInt();
                }
                else
                {
                    arr[i] = fallback[i];
                }
            }
        }

        return arr;
    }

}