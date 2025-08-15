package net.googlepox.metallurgy.datagen;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.block.ModBlocks;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, Metallurgy.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        Metallurgy.METALS.keySet().forEach(name -> {
            if (MetalRegistry.ORE_BLOCKS.containsKey(name)) {
                var oreBlock = MetalRegistry.ORE_BLOCKS.get(name).get();
                ModelFile oreModel = models().cubeAll(name + "_ore", modLoc("block/" + name + "_ore"));
                simpleBlock(oreBlock, oreModel);
                simpleBlockItem(oreBlock, oreModel);

                oreBlock = MetalRegistry.DEEPSLATE_ORE_BLOCKS.get(name).get();
                oreModel = models().cubeAll(name + "_ore_deepslate", modLoc("block/" + name + "_ore_deepslate"));
                simpleBlock(oreBlock, oreModel);
                simpleBlockItem(oreBlock, oreModel);

                oreBlock = MetalRegistry.NETHER_ORE_BLOCKS.get(name).get();
                oreModel = models().cubeAll(name + "_ore_nether", modLoc("block/" + name + "_ore_nether"));
                simpleBlock(oreBlock, oreModel);
                simpleBlockItem(oreBlock, oreModel);

                oreBlock = MetalRegistry.ENDSTONE_ORE_BLOCKS.get(name).get();
                oreModel = models().cubeAll(name + "_ore_end_stone", modLoc("block/" + name + "_ore_end_stone"));
                simpleBlock(oreBlock, oreModel);
                simpleBlockItem(oreBlock, oreModel);
            }

            var block = MetalRegistry.METAL_BLOCKS.get(name).get();
            ModelFile model = models().cubeAll(name + "_block", modLoc("block/" + name + "_block"));
            simpleBlock(block, model);
            simpleBlockItem(block, model);
            block = MetalRegistry.METAL_BRICKS.get(name).get();
            model = models().cubeAll(name + "_bricks", modLoc("block/" + name + "_bricks"));
            simpleBlock(block, model);
            simpleBlockItem(block, model);
        });
        blockWithItem(ModBlocks.BITUMEN_BLOCK);
        blockWithItem(ModBlocks.GOLD_BRICKS);
        blockWithItem(ModBlocks.IRON_BRICKS);
        blockWithItem(ModBlocks.COPPER_BRICKS);

        blockWithItem(ModBlocks.TAR_ORE);
        blockWithItem(ModBlocks.TAR_ORE_DEEPSLATE);
        blockWithItem(ModBlocks.TAR_ORE_NETHER);
        blockWithItem(ModBlocks.TAR_ORE_END_STONE);

        blockWithItem(ModBlocks.PHOSPHORITE_ORE);
        blockWithItem(ModBlocks.PHOSPHORITE_ORE_DEEPSLATE);
        blockWithItem(ModBlocks.PHOSPHORITE_ORE_NETHER);
        blockWithItem(ModBlocks.PHOSPHORITE_ORE_END_STONE);

        blockWithItem(ModBlocks.SULFUR_ORE);
        blockWithItem(ModBlocks.SULFUR_ORE_DEEPSLATE);
        blockWithItem(ModBlocks.SULFUR_ORE_NETHER);
        blockWithItem(ModBlocks.SULFUR_ORE_END_STONE);

        blockWithItem(ModBlocks.POTASH_ORE);
        blockWithItem(ModBlocks.POTASH_ORE_DEEPSLATE);
        blockWithItem(ModBlocks.POTASH_ORE_NETHER);
        blockWithItem(ModBlocks.POTASH_ORE_END_STONE);

        simpleBlockWithItem(ModBlocks.CRUSHER.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/crusher")));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}