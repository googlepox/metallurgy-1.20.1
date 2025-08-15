package net.googlepox.metallurgy.worldgen;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.block.ModBlocks;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModConfiguredFeatures {
    public static Map<String, ResourceKey<ConfiguredFeature<?, ?>>> OVERWORLD_ORE_KEYS = new HashMap<>();
    public static Map<String, ResourceKey<ConfiguredFeature<?, ?>>> NETHER_ORE_KEYS = new HashMap<>();
    public static Map<String, ResourceKey<ConfiguredFeature<?, ?>>> END_ORE_KEYS = new HashMap<>();

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplacables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);



        Metallurgy.METALS.forEach((name, def) -> {
            if (def.getOreStats() != null && def.getOreStats().getHasOre()) {
                List<OreConfiguration.TargetBlockState> overworldOres = List.of(OreConfiguration.target(stoneReplaceable,
                                MetalRegistry.ORE_BLOCKS.get(name).get().defaultBlockState()),
                        OreConfiguration.target(deepslateReplaceables, MetalRegistry.DEEPSLATE_ORE_BLOCKS.get(name).get().defaultBlockState()));

                OVERWORLD_ORE_KEYS.put(name, registerKey(name + "_ore"));
                NETHER_ORE_KEYS.put(name, registerKey(name + "_ore_nether"));
                END_ORE_KEYS.put(name, registerKey(name + "_ore_end_stone"));

                register(context, OVERWORLD_ORE_KEYS.get(name), Feature.ORE, new OreConfiguration(overworldOres, def.getOreStats().getVeinSize()));
                register(context, NETHER_ORE_KEYS.get(name), Feature.ORE, new OreConfiguration(netherrackReplacables,
                        MetalRegistry.NETHER_ORE_BLOCKS.get(name).get().defaultBlockState(), def.getOreStats().getVeinSize()));
                register(context, END_ORE_KEYS.get(name), Feature.ORE, new OreConfiguration(endReplaceables,
                        MetalRegistry.ENDSTONE_ORE_BLOCKS.get(name).get().defaultBlockState(), def.getOreStats().getVeinSize()));
            }
        });
        String name = "potash";
        List<OreConfiguration.TargetBlockState> overworldOres = List.of(OreConfiguration.target(stoneReplaceable,
                        ModBlocks.POTASH_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.POTASH_ORE_DEEPSLATE.get().defaultBlockState()));

        OVERWORLD_ORE_KEYS.put(name, registerKey(name + "_ore"));
        NETHER_ORE_KEYS.put(name, registerKey(name + "_ore_nether"));
        END_ORE_KEYS.put(name, registerKey(name + "_ore_end_stone"));

        register(context, OVERWORLD_ORE_KEYS.get(name), Feature.ORE, new OreConfiguration(overworldOres, 7));
        register(context, NETHER_ORE_KEYS.get(name), Feature.ORE, new OreConfiguration(netherrackReplacables,
                MetalRegistry.NETHER_ORE_BLOCKS.get(name).get().defaultBlockState(), 7));
        register(context, END_ORE_KEYS.get(name), Feature.ORE, new OreConfiguration(endReplaceables,
                MetalRegistry.ENDSTONE_ORE_BLOCKS.get(name).get().defaultBlockState(), 7));

        name = "phosphorite";
        overworldOres = List.of(OreConfiguration.target(stoneReplaceable,
                        ModBlocks.PHOSPHORITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.PHOSPHORITE_ORE_DEEPSLATE.get().defaultBlockState()));

        OVERWORLD_ORE_KEYS.put(name, registerKey(name + "_ore"));
        NETHER_ORE_KEYS.put(name, registerKey(name + "_ore_nether"));
        END_ORE_KEYS.put(name, registerKey(name + "_ore_end_stone"));

        register(context, OVERWORLD_ORE_KEYS.get(name), Feature.ORE, new OreConfiguration(overworldOres, 5));
        register(context, NETHER_ORE_KEYS.get(name), Feature.ORE, new OreConfiguration(netherrackReplacables,
                MetalRegistry.NETHER_ORE_BLOCKS.get(name).get().defaultBlockState(), 5));
        register(context, END_ORE_KEYS.get(name), Feature.ORE, new OreConfiguration(endReplaceables,
                MetalRegistry.ENDSTONE_ORE_BLOCKS.get(name).get().defaultBlockState(), 5));


        name = "sulfur";
        overworldOres = List.of(OreConfiguration.target(stoneReplaceable,
                        ModBlocks.SULFUR_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.SULFUR_ORE_DEEPSLATE.get().defaultBlockState()));

        OVERWORLD_ORE_KEYS.put(name, registerKey(name + "_ore"));
        NETHER_ORE_KEYS.put(name, registerKey(name + "_ore_nether"));
        END_ORE_KEYS.put(name, registerKey(name + "_ore_end_stone"));

        register(context, OVERWORLD_ORE_KEYS.get(name), Feature.ORE, new OreConfiguration(overworldOres, 6));
        register(context, NETHER_ORE_KEYS.get(name), Feature.ORE, new OreConfiguration(netherrackReplacables,
                MetalRegistry.NETHER_ORE_BLOCKS.get(name).get().defaultBlockState(), 6));
        register(context, END_ORE_KEYS.get(name), Feature.ORE, new OreConfiguration(endReplaceables,
                MetalRegistry.ENDSTONE_ORE_BLOCKS.get(name).get().defaultBlockState(), 6));

    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Metallurgy.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
