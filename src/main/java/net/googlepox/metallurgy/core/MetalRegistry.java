package net.googlepox.metallurgy.core;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.fluid.BaseFluidType;
import net.googlepox.metallurgy.item.ModToolTiers;
import net.googlepox.metallurgy.material.MetalStats;
import net.googlepox.metallurgy.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.FluidObject;

import java.util.HashMap;
import java.util.Map;

public class MetalRegistry {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Metallurgy.MODID);
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Metallurgy.MODID);
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(Metallurgy.MODID);

    public static final ResourceLocation METAL_STILL_RL = ResourceLocation.fromNamespaceAndPath(Metallurgy.MODID, "blocks/molten_metal_still");
    public static final ResourceLocation METAL_FLOWING_RL = ResourceLocation.fromNamespaceAndPath(Metallurgy.MODID, "blocks/molten_metal_flow");

    public static final Map<String, RegistryObject<Block>> ORE_BLOCKS = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> ORE_ITEMS = new HashMap<>();
    public static final Map<String, RegistryObject<Block>> DEEPSLATE_ORE_BLOCKS = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> DEEPSLATE_ORE_ITEMS = new HashMap<>();
    public static final Map<String, RegistryObject<Block>> NETHER_ORE_BLOCKS = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> NETHER_ORE_ITEMS = new HashMap<>();
    public static final Map<String, RegistryObject<Block>> ENDSTONE_ORE_BLOCKS = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> ENDSTONE_ORE_ITEMS = new HashMap<>();
    public static final Map<String, RegistryObject<Block>> METAL_BLOCKS = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> METAL_BLOCKS_ITEMS = new HashMap<>();
    public static final Map<String, RegistryObject<Block>> METAL_BRICKS = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> METAL_BRICKS_ITEMS = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> RAW_ITEMS = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> INGOTS = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> NUGGETS = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> DUSTS = new HashMap<>();

    public static final Map<String, RegistryObject<FluidType>> METAL_FLUID_TYPES = new HashMap<>();
    public static final Map<String, FlowingFluidObject<ForgeFlowingFluid>> METAL_FLUIDS = new HashMap<>();

    public static final Map<String, RegistryObject<Item>> TOOLS_PICKAXE = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> TOOLS_SWORD = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> TOOLS_AXE = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> TOOLS_SHOVEL = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> TOOLS_HOE = new HashMap<>();

    public static void registerMetal(MetalStats metalStats) {
        if (metalStats.getOreStats() != null && metalStats.getOreStats().getHasOre()) {
            // Ore block
            RegistryObject<Block> oreBlock = BLOCKS.register(metalStats.getName() + "_ore",
                    () -> new Block(BlockBehaviour.Properties.copy(metalStats.getMaterial())
                            .strength(metalStats.getStrength())
                            .requiresCorrectToolForDrops()));

            // Ore item (BlockItem)
            RegistryObject<Item> oreItem = ITEMS.register(metalStats.getName() + "_ore",
                    () -> new BlockItem(oreBlock.get(), new Item.Properties()));

            ORE_BLOCKS.put(metalStats.getName(), oreBlock);
            ORE_ITEMS.put(metalStats.getName(), oreItem);

            // Deepslate Ore block
            RegistryObject<Block> deepslateOreBlock = BLOCKS.register(metalStats.getName() + "_ore_deepslate",
                    () -> new Block(BlockBehaviour.Properties.copy(metalStats.getMaterial())
                            .strength(metalStats.getStrength())
                            .requiresCorrectToolForDrops()));

            // Deepslate Ore item (BlockItem)
            RegistryObject<Item> deepslateOreItem = ITEMS.register(metalStats.getName() + "_ore_deepslate",
                    () -> new BlockItem(deepslateOreBlock.get(), new Item.Properties()));

            DEEPSLATE_ORE_BLOCKS.put(metalStats.getName(), deepslateOreBlock);
            DEEPSLATE_ORE_ITEMS.put(metalStats.getName(), deepslateOreItem);

            // Nether Ore block
            RegistryObject<Block> netherOreBlock = BLOCKS.register(metalStats.getName() + "_ore_nether",
                    () -> new Block(BlockBehaviour.Properties.copy(metalStats.getMaterial())
                            .strength(metalStats.getStrength())
                            .requiresCorrectToolForDrops()));

            // Nether Ore item (BlockItem)
            RegistryObject<Item> netherOreItem = ITEMS.register(metalStats.getName() + "_ore_nether",
                    () -> new BlockItem(netherOreBlock.get(), new Item.Properties()));

            NETHER_ORE_BLOCKS.put(metalStats.getName(), netherOreBlock);
            NETHER_ORE_ITEMS.put(metalStats.getName(), netherOreItem);

            // End Ore block
            RegistryObject<Block> endOreBlock = BLOCKS.register(metalStats.getName() + "_ore_end_stone",
                    () -> new Block(BlockBehaviour.Properties.copy(metalStats.getMaterial())
                            .strength(metalStats.getStrength())
                            .requiresCorrectToolForDrops()));

            // End Ore item (BlockItem)
            RegistryObject<Item> endOreItem = ITEMS.register(metalStats.getName() + "_ore_end_stone",
                    () -> new BlockItem(endOreBlock.get(), new Item.Properties()));

            ENDSTONE_ORE_BLOCKS.put(metalStats.getName(), endOreBlock);
            ENDSTONE_ORE_ITEMS.put(metalStats.getName(), endOreItem);
        }

        if (metalStats.getToolStats() != null) {
            String name = metalStats.getName();
            ModToolTiers.generateToolTier(name, metalStats.getToolStats());
            RegistryObject<Item> swordItem = ITEMS.register(name + "_sword",
                    () -> new SwordItem(ModToolTiers.TIERS.get(name), 4, 2, new Item.Properties()));
            TOOLS_SWORD.put(name, swordItem);
            RegistryObject<Item> pickaxeItem = ITEMS.register(name + "_pickaxe",
                    () -> new PickaxeItem(ModToolTiers.TIERS.get(name), 1, 1, new Item.Properties()));
            TOOLS_PICKAXE.put(name, pickaxeItem);
            RegistryObject<Item> axeItem = ITEMS.register(name + "_axe",
                    () -> new AxeItem(ModToolTiers.TIERS.get(name), 7, 1, new Item.Properties()));
            TOOLS_AXE.put(name, axeItem);
            RegistryObject<Item> shovelItem = ITEMS.register(name + "_shovel",
                    () -> new ShovelItem(ModToolTiers.TIERS.get(name), 0, 0, new Item.Properties()));
            TOOLS_SHOVEL.put(name, shovelItem);
            RegistryObject<Item> hoeItem = ITEMS.register(name + "_hoe",
                    () -> new HoeItem(ModToolTiers.TIERS.get(name), 0, 0, new Item.Properties()));
            TOOLS_HOE.put(name, hoeItem);

        }

        // Metal Blocks
        RegistryObject<Block> metalBlock = BLOCKS.register(metalStats.getName() + "_block",
                () -> new Block(BlockBehaviour.Properties.copy(metalStats.getMaterial())
                        .strength(metalStats.getStrength())
                        .requiresCorrectToolForDrops()));

        // Metal block item (BlockItem)
        RegistryObject<Item> metalBlockItem = ITEMS.register(metalStats.getName() + "_block",
                () -> new BlockItem(metalBlock.get(), new Item.Properties()));

        // Metal Blocks
        RegistryObject<Block> metalBricks = BLOCKS.register(metalStats.getName() + "_bricks",
                () -> new Block(BlockBehaviour.Properties.copy(metalStats.getMaterial())
                        .strength(metalStats.getStrength())
                        .requiresCorrectToolForDrops()));

        // Metal block item (BlockItem)
        RegistryObject<Item> metalBrickItem = ITEMS.register(metalStats.getName() + "_bricks",
                () -> new BlockItem(metalBricks.get(), new Item.Properties()));
        // Ingot item
        RegistryObject<Item> ingotItem = ITEMS.register(metalStats.getName() + "_ingot",
                () -> new Item(new Item.Properties()));
        // Nugget item
        RegistryObject<Item> nuggetItem = ITEMS.register(metalStats.getName() + "_nugget",
                () -> new Item(new Item.Properties()));
        // Raw item
        RegistryObject<Item> rawItem = ITEMS.register(metalStats.getName() + "_raw",
                () -> new Item(new Item.Properties()));
        // Raw item
        RegistryObject<Item> dustItem = ITEMS.register(metalStats.getName() + "_dust",
                () -> new Item(new Item.Properties()));

        // Fluids

        FlowingFluidObject<ForgeFlowingFluid> fluid =  FLUIDS.register("molten_" + metalStats.getName())
                .type(FluidType.Properties.create()
                        .lightLevel(9)
                        .temperature(metalStats.getTemperature())
                        .viscosity(4000)
                        .density(800))
                .bucket().block(MapColor.METAL, 9).flowing();

        METAL_FLUIDS.put(metalStats.getName(), fluid);

        METAL_BLOCKS.put(metalStats.getName(), metalBlock);
        METAL_BLOCKS_ITEMS.put(metalStats.getName(), metalBlockItem);
        METAL_BRICKS.put(metalStats.getName(), metalBricks);
        METAL_BRICKS_ITEMS.put(metalStats.getName(), metalBrickItem);

        INGOTS.put(metalStats.getName(), ingotItem);
        NUGGETS.put(metalStats.getName(), nuggetItem);
        RAW_ITEMS.put(metalStats.getName(), rawItem);
        DUSTS.put(metalStats.getName(), dustItem);
        Metallurgy.METALS.put(metalStats.getName(), metalStats);
    }
}