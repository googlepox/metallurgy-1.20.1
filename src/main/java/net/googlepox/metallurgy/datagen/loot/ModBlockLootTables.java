package net.googlepox.metallurgy.datagen.loot;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.block.ModBlocks;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.googlepox.metallurgy.item.ModItems;
import net.googlepox.metallurgy.util.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        Metallurgy.METALS.keySet().forEach(name -> {
            if (MetalRegistry.ORE_BLOCKS.containsKey(name)) {
                this.add(MetalRegistry.ORE_BLOCKS.get(name).get(),
                        block -> createOreDrop(MetalRegistry.ORE_BLOCKS.get(name).get(), MetalRegistry.RAW_ITEMS.get(name).get()));
                this.add(MetalRegistry.DEEPSLATE_ORE_BLOCKS.get(name).get(),
                        block -> createOreDrop(MetalRegistry.DEEPSLATE_ORE_BLOCKS.get(name).get(), MetalRegistry.RAW_ITEMS.get(name).get()));
                this.add(MetalRegistry.NETHER_ORE_BLOCKS.get(name).get(),
                        block -> createOreDrop(MetalRegistry.NETHER_ORE_BLOCKS.get(name).get(), MetalRegistry.RAW_ITEMS.get(name).get()));
                this.add(MetalRegistry.ENDSTONE_ORE_BLOCKS.get(name).get(),
                        block -> createOreDrop(MetalRegistry.ENDSTONE_ORE_BLOCKS.get(name).get(), MetalRegistry.RAW_ITEMS.get(name).get()));
            }
            dropSelf(MetalRegistry.METAL_BLOCKS.get(name).get());
            dropSelf(MetalRegistry.METAL_BRICKS.get(name).get());
        });

        this.add(ModBlocks.PHOSPHORITE_ORE.get(),
                block -> createOreDrop(ModBlocks.PHOSPHORITE_ORE.get(), ModItems.PHOSPHORUS.get()));
        this.add(ModBlocks.PHOSPHORITE_ORE_DEEPSLATE.get(),
                block -> createOreDrop(ModBlocks.PHOSPHORITE_ORE_DEEPSLATE.get(), ModItems.PHOSPHORUS.get()));
        this.add(ModBlocks.PHOSPHORITE_ORE_NETHER.get(),
                block -> createOreDrop(ModBlocks.PHOSPHORITE_ORE_NETHER.get(), ModItems.PHOSPHORUS.get()));
        this.add(ModBlocks.PHOSPHORITE_ORE_END_STONE.get(),
                block -> createOreDrop(ModBlocks.PHOSPHORITE_ORE_END_STONE.get(), ModItems.PHOSPHORUS.get()));

        this.add(ModBlocks.SULFUR_ORE.get(),
                block -> createOreDrop(ModBlocks.SULFUR_ORE.get(), ModItems.SULFUR.get()));
        this.add(ModBlocks.SULFUR_ORE_DEEPSLATE.get(),
                block -> createOreDrop(ModBlocks.SULFUR_ORE_DEEPSLATE.get(), ModItems.SULFUR.get()));
        this.add(ModBlocks.SULFUR_ORE_NETHER.get(),
                block -> createOreDrop(ModBlocks.SULFUR_ORE_NETHER.get(), ModItems.SULFUR.get()));
        this.add(ModBlocks.SULFUR_ORE_END_STONE.get(),
                block -> createOreDrop(ModBlocks.SULFUR_ORE_END_STONE.get(), ModItems.SULFUR.get()));

        this.add(ModBlocks.POTASH_ORE.get(),
                block -> createOreDrop(ModBlocks.POTASH_ORE.get(), ModItems.POTASH.get()));
        this.add(ModBlocks.POTASH_ORE_DEEPSLATE.get(),
                block -> createOreDrop(ModBlocks.POTASH_ORE_DEEPSLATE.get(), ModItems.POTASH.get()));
        this.add(ModBlocks.POTASH_ORE_NETHER.get(),
                block -> createOreDrop(ModBlocks.POTASH_ORE_NETHER.get(), ModItems.POTASH.get()));
        this.add(ModBlocks.POTASH_ORE_END_STONE.get(),
                block -> createOreDrop(ModBlocks.POTASH_ORE_END_STONE.get(), ModItems.POTASH.get()));

        this.add(ModBlocks.TAR_ORE.get(),
                block -> createOreDrop(ModBlocks.TAR_ORE.get(), ModItems.TAR.get()));
        this.add(ModBlocks.TAR_ORE_DEEPSLATE.get(),
                block -> createOreDrop(ModBlocks.TAR_ORE_DEEPSLATE.get(), ModItems.TAR.get()));
        this.add(ModBlocks.TAR_ORE_NETHER.get(),
                block -> createOreDrop(ModBlocks.TAR_ORE_NETHER.get(), ModItems.TAR.get()));
        this.add(ModBlocks.TAR_ORE_END_STONE.get(),
                block -> createOreDrop(ModBlocks.TAR_ORE_END_STONE.get(), ModItems.TAR.get()));

        dropSelf(ModBlocks.BITUMEN_BLOCK.get());
        dropSelf(ModBlocks.IRON_BRICKS.get());
        dropSelf(ModBlocks.GOLD_BRICKS.get());
        dropSelf(ModBlocks.COPPER_BRICKS.get());
        dropSelf(ModBlocks.CRUSHER.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>();
        blocks.addAll(MetalRegistry.ORE_BLOCKS.values().stream().map(RegistryObject::get).toList());
        blocks.addAll(MetalRegistry.DEEPSLATE_ORE_BLOCKS.values().stream().map(RegistryObject::get).toList());
        blocks.addAll(MetalRegistry.NETHER_ORE_BLOCKS.values().stream().map(RegistryObject::get).toList());
        blocks.addAll(MetalRegistry.ENDSTONE_ORE_BLOCKS.values().stream().map(RegistryObject::get).toList());
        blocks.addAll(MetalRegistry.METAL_BLOCKS.values().stream().map(RegistryObject::get).toList());
        blocks.addAll(MetalRegistry.METAL_BRICKS.values().stream().map(RegistryObject::get).toList());
        blocks.addAll(ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).toList());
        return blocks;
    }
}