package net.googlepox.metallurgy.worldgen;

import net.googlepox.metallurgy.Metallurgy;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModPlacedFeatures {

    public static Map<String, ResourceKey<PlacedFeature>> OVERWORLD_ORE_PLACED_KEYS = new HashMap<>();
    public static Map<String, ResourceKey<PlacedFeature>> NETHER_ORE_PLACED_KEYS = new HashMap<>();
    public static Map<String, ResourceKey<PlacedFeature>> END_ORE_PLACED_KEYS = new HashMap<>();

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        Metallurgy.METALS.forEach((name, def) -> {
            if (def.getOreStats() != null && def.getOreStats().getHasOre()) {
                OVERWORLD_ORE_PLACED_KEYS.put(name, registerKey(name + "_ore"));
                NETHER_ORE_PLACED_KEYS.put(name, registerKey(name + "_ore_nether"));
                END_ORE_PLACED_KEYS.put(name, registerKey(name + "_ore_end_stone"));

                register(context, OVERWORLD_ORE_PLACED_KEYS.get(name), configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_ORE_KEYS.get(name)),
                        ModOrePlacement.commonOrePlacement(def.getOreStats().getVeinsPerChunk(),
                                HeightRangePlacement.uniform(VerticalAnchor.absolute(def.getOreStats().getMinY()), VerticalAnchor.absolute(def.getOreStats().getMaxY()))));
                register(context, NETHER_ORE_PLACED_KEYS.get(name), configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_ORE_KEYS.get(name)),
                        ModOrePlacement.commonOrePlacement(def.getOreStats().getVeinsPerChunk(),
                                HeightRangePlacement.uniform(VerticalAnchor.absolute(def.getOreStats().getMinY()), VerticalAnchor.absolute(def.getOreStats().getMaxY()))));
                register(context, END_ORE_PLACED_KEYS.get(name), configuredFeatures.getOrThrow(ModConfiguredFeatures.END_ORE_KEYS.get(name)),
                        ModOrePlacement.commonOrePlacement(def.getOreStats().getVeinsPerChunk(),
                                HeightRangePlacement.uniform(VerticalAnchor.absolute(def.getOreStats().getMinY()), VerticalAnchor.absolute(def.getOreStats().getMaxY()))));
            }
        });

        String name = "potash";
        OVERWORLD_ORE_PLACED_KEYS.put(name, registerKey(name + "_ore"));
        NETHER_ORE_PLACED_KEYS.put(name, registerKey(name + "_ore_nether"));
        END_ORE_PLACED_KEYS.put(name, registerKey(name + "_ore_end_stone"));

        register(context, OVERWORLD_ORE_PLACED_KEYS.get(name), configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_ORE_KEYS.get(name)),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(72))));
        register(context, NETHER_ORE_PLACED_KEYS.get(name), configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_ORE_KEYS.get(name)),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(72))));
        register(context, END_ORE_PLACED_KEYS.get(name), configuredFeatures.getOrThrow(ModConfiguredFeatures.END_ORE_KEYS.get(name)),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(72))));

        name = "phosphorite";
        OVERWORLD_ORE_PLACED_KEYS.put(name, registerKey(name + "_ore"));
        NETHER_ORE_PLACED_KEYS.put(name, registerKey(name + "_ore_nether"));
        END_ORE_PLACED_KEYS.put(name, registerKey(name + "_ore_end_stone"));

        register(context, OVERWORLD_ORE_PLACED_KEYS.get(name), configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_ORE_KEYS.get(name)),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(100))));
        register(context, NETHER_ORE_PLACED_KEYS.get(name), configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_ORE_KEYS.get(name)),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(100))));
        register(context, END_ORE_PLACED_KEYS.get(name), configuredFeatures.getOrThrow(ModConfiguredFeatures.END_ORE_KEYS.get(name)),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(100))));

        name = "sulfur";
        OVERWORLD_ORE_PLACED_KEYS.put(name, registerKey(name + "_ore"));
        NETHER_ORE_PLACED_KEYS.put(name, registerKey(name + "_ore_nether"));
        END_ORE_PLACED_KEYS.put(name, registerKey(name + "_ore_end_stone"));

        register(context, OVERWORLD_ORE_PLACED_KEYS.get(name), configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_ORE_KEYS.get(name)),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(15))));
        register(context, NETHER_ORE_PLACED_KEYS.get(name), configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_ORE_KEYS.get(name)),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(15))));
        register(context, END_ORE_PLACED_KEYS.get(name), configuredFeatures.getOrThrow(ModConfiguredFeatures.END_ORE_KEYS.get(name)),
                ModOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(15))));
    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Metallurgy.MODID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}