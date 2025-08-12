package net.googlepox.metallurgy.datagen;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;


public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Metallurgy.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.BITUMEN_BLOCK.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.BITUMEN_BLOCK.get(),
                        ModBlocks.ADAMANTINE_BLOCK.get(),
                        ModBlocks.ADAMANTINE_BRICKS.get());

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.ADAMANTINE_ORE.get(),
                    ModBlocks.DEEPSLATE_ADAMANTINE_ORE.get(),
                    ModBlocks.NETHER_ADAMANTINE_ORE.get());

        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .add(ModBlocks.END_STONE_ADAMANTINE_ORE.get());
    }
}
