package net.googlepox.metallurgy.datagen;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Metallurgy.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.BITUMEN_BLOCK);

        blockWithItem(ModBlocks.ADAMANTINE_BLOCK);
        blockWithItem(ModBlocks.ADAMANTINE_BRICKS);
        blockWithItem(ModBlocks.ADAMANTINE_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_ADAMANTINE_ORE);
        blockWithItem(ModBlocks.NETHER_ADAMANTINE_ORE);
        blockWithItem(ModBlocks.END_STONE_ADAMANTINE_ORE);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
