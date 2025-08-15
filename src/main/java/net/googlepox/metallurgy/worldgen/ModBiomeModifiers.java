package net.googlepox.metallurgy.worldgen;

import net.googlepox.metallurgy.Metallurgy;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class ModBiomeModifiers {

    public static Map<String, ResourceKey<BiomeModifier>> ADD_ORE_KEYS = new HashMap<>();
    public static Map<String, ResourceKey<BiomeModifier>> ADD_NETHER_ORE_KEYS = new HashMap<>();
    public static Map<String, ResourceKey<BiomeModifier>> ADD_END_ORE_KEYS = new HashMap<>();


    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        Metallurgy.METALS.forEach((name, def) -> {
            if (def.getOreStats() != null && def.getOreStats().getHasOre()) {
                ADD_ORE_KEYS.put(name, registerKey(name + "_ore"));
                ADD_NETHER_ORE_KEYS.put(name, registerKey(name + "_ore_nether"));
                ADD_END_ORE_KEYS.put(name, registerKey(name + "_ore_end_stone"));

                context.register(ADD_ORE_KEYS.get(name), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.OVERWORLD_ORE_PLACED_KEYS.get(name))),
                        GenerationStep.Decoration.UNDERGROUND_ORES));

                context.register(ADD_NETHER_ORE_KEYS.get(name), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(BiomeTags.IS_NETHER),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.NETHER_ORE_PLACED_KEYS.get(name))),
                        GenerationStep.Decoration.UNDERGROUND_ORES));

                context.register(ADD_END_ORE_KEYS.get(name), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(BiomeTags.IS_END),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.END_ORE_PLACED_KEYS.get(name))),
                        GenerationStep.Decoration.UNDERGROUND_ORES));
            }
        });
        String name = "potash";
        ADD_ORE_KEYS.put(name, registerKey(name + "_ore"));
        ADD_NETHER_ORE_KEYS.put(name, registerKey(name + "_ore_nether"));
        ADD_END_ORE_KEYS.put(name, registerKey(name + "_ore_end_stone"));

        context.register(ADD_ORE_KEYS.get(name), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.OVERWORLD_ORE_PLACED_KEYS.get(name))),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_NETHER_ORE_KEYS.get(name), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.NETHER_ORE_PLACED_KEYS.get(name))),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_END_ORE_KEYS.get(name), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.END_ORE_PLACED_KEYS.get(name))),
                GenerationStep.Decoration.UNDERGROUND_ORES));


    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Metallurgy.MODID, name));
    }
}