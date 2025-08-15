package net.googlepox.metallurgy.datagen;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.block.ModBlocks;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.googlepox.metallurgy.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;


public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Metallurgy.MODID, existingFileHelper);
    }

    private int getMiningLevel(String metalName) {
        return Metallurgy.METALS.get(metalName).getOreHarvest();
    }

    private void setMiningLevelTag(Map<String, RegistryObject<Block>> blockMap, String name) {
        if (Metallurgy.METALS.get(name).getOreStats() != null && Metallurgy.METALS.get(name).getOreStats().getHasOre()) {
            this.tag(Tags.Blocks.ORES).add(blockMap.get(name).get());
        }
        switch (getMiningLevel(name)) {
            case 0:
                break;
            case 1:
                this.tag(BlockTags.NEEDS_STONE_TOOL)
                        .add(blockMap.get(name).get());
                break;
            case 2:
                this.tag(BlockTags.NEEDS_IRON_TOOL)
                        .add(blockMap.get(name).get());
                break;
            case 3:
                this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                        .add(blockMap.get(name).get());
                break;
            case 4:
                this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                        .add(blockMap.get(name).get());
            case 5:
                this.tag(ModTags.Blocks.NEEDS_TIER5_TOOL)
                        .add(blockMap.get(name).get());
            case 6:
                this.tag(ModTags.Blocks.NEEDS_TIER6_TOOL)
                        .add(blockMap.get(name).get());
                break;

        }
    }

    private void addOreTags(Map<String, RegistryObject<Block>> blockMap) {
        blockMap.keySet().forEach(name -> {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                    .add(blockMap.get(name).get());
            setMiningLevelTag(blockMap, name);
        });
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addOreTags(MetalRegistry.ORE_BLOCKS);
        addOreTags(MetalRegistry.DEEPSLATE_ORE_BLOCKS);
        addOreTags(MetalRegistry.NETHER_ORE_BLOCKS);
        addOreTags(MetalRegistry.ENDSTONE_ORE_BLOCKS);
        addOreTags(MetalRegistry.METAL_BLOCKS);
        addOreTags(MetalRegistry.METAL_BRICKS);
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.BITUMEN_BLOCK.get(),
                        ModBlocks.GOLD_BRICKS.get(),
                        ModBlocks.COPPER_BRICKS.get(),
                        ModBlocks.IRON_BRICKS.get(),
                        ModBlocks.PHOSPHORITE_ORE.get(),
                        ModBlocks.PHOSPHORITE_ORE_DEEPSLATE.get(),
                        ModBlocks.PHOSPHORITE_ORE_NETHER.get(),
                        ModBlocks.PHOSPHORITE_ORE_END_STONE.get(),
                        ModBlocks.SULFUR_ORE.get(),
                        ModBlocks.SULFUR_ORE_DEEPSLATE.get(),
                        ModBlocks.SULFUR_ORE_NETHER.get(),
                        ModBlocks.SULFUR_ORE_END_STONE.get(),
                        ModBlocks.TAR_ORE.get(),
                        ModBlocks.TAR_ORE_DEEPSLATE.get(),
                        ModBlocks.TAR_ORE_NETHER.get(),
                        ModBlocks.TAR_ORE_END_STONE.get(),
                        ModBlocks.POTASH_ORE.get(),
                        ModBlocks.POTASH_ORE_DEEPSLATE.get(),
                        ModBlocks.POTASH_ORE_NETHER.get(),
                        ModBlocks.POTASH_ORE_END_STONE.get());
        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.BITUMEN_BLOCK.get(),
                        ModBlocks.PHOSPHORITE_ORE.get(),
                        ModBlocks.PHOSPHORITE_ORE_DEEPSLATE.get(),
                        ModBlocks.PHOSPHORITE_ORE_NETHER.get(),
                        ModBlocks.PHOSPHORITE_ORE_END_STONE.get(),
                        ModBlocks.SULFUR_ORE.get(),
                        ModBlocks.SULFUR_ORE_DEEPSLATE.get(),
                        ModBlocks.SULFUR_ORE_NETHER.get(),
                        ModBlocks.SULFUR_ORE_END_STONE.get(),
                        ModBlocks.TAR_ORE.get(),
                        ModBlocks.TAR_ORE_DEEPSLATE.get(),
                        ModBlocks.TAR_ORE_NETHER.get(),
                        ModBlocks.TAR_ORE_END_STONE.get(),
                        ModBlocks.POTASH_ORE.get(),
                        ModBlocks.POTASH_ORE_DEEPSLATE.get(),
                        ModBlocks.POTASH_ORE_NETHER.get(),
                        ModBlocks.POTASH_ORE_END_STONE.get(),
                        ModBlocks.CRUSHER.get());

    }
}
