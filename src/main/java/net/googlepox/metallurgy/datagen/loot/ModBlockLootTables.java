package net.googlepox.metallurgy.datagen.loot;

import net.googlepox.metallurgy.block.ModBlocks;
import net.googlepox.metallurgy.item.ModItems;
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

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.BITUMEN_BLOCK.get());
        this.dropSelf(ModBlocks.ADAMANTINE_BLOCK.get());
        this.dropSelf(ModBlocks.ADAMANTINE_BRICKS.get());

        this.add(ModBlocks.ADAMANTINE_ORE.get(),
                block -> createOreDrop(ModBlocks.ADAMANTINE_ORE.get(), ModItems.ADAMANTINE_RAW.get()));
        this.add(ModBlocks.DEEPSLATE_ADAMANTINE_ORE.get(),
                block -> createOreDrop(ModBlocks.DEEPSLATE_ADAMANTINE_ORE.get(), ModItems.ADAMANTINE_RAW.get()));
        this.add(ModBlocks.NETHER_ADAMANTINE_ORE.get(),
                block -> createOreDrop(ModBlocks.NETHER_ADAMANTINE_ORE.get(), ModItems.ADAMANTINE_RAW.get()));
        this.add(ModBlocks.END_STONE_ADAMANTINE_ORE.get(),
                block -> createOreDrop(ModBlocks.END_STONE_ADAMANTINE_ORE.get(), ModItems.ADAMANTINE_RAW.get()));

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}