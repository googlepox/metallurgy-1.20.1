package net.googlepox.metallurgy.datagen;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.googlepox.metallurgy.item.ModItems;
import net.googlepox.metallurgy.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                               CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, Metallurgy.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        Metallurgy.METALS.forEach((name, stats) -> {
            if (stats.getToolStats() != null) {
                this.tag(Tags.Items.TOOLS)
                        .add(MetalRegistry.TOOLS_PICKAXE.get(name).get(),
                                MetalRegistry.TOOLS_SWORD.get(name).get(),
                                MetalRegistry.TOOLS_AXE.get(name).get(),
                                MetalRegistry.TOOLS_SHOVEL.get(name).get(),
                                MetalRegistry.TOOLS_HOE.get(name).get());
            }
            if (stats.getOreStats() != null && stats.getOreStats().getHasOre()) {
                this.tag(Tags.Items.RAW_MATERIALS)
                        .add(MetalRegistry.RAW_ITEMS.get(name).get());
            }
            this.tag(Tags.Items.INGOTS).add(MetalRegistry.INGOTS.get(name).get());
            this.tag(Tags.Items.DUSTS).add(MetalRegistry.DUSTS.get(name).get());
            this.tag(Tags.Items.NUGGETS).add(MetalRegistry.NUGGETS.get(name).get());

            this.tag(ModTags.Items.ingotTags.get(name)).add(MetalRegistry.INGOTS.get(name).get());
            this.tag(ModTags.Items.nuggetTags.get(name)).add(MetalRegistry.NUGGETS.get(name).get());
            this.tag(ModTags.Items.dustTags.get(name)).add(MetalRegistry.DUSTS.get(name).get());
            this.tag(ModTags.Items.rawTags.get(name)).add(MetalRegistry.RAW_ITEMS.get(name).get());
        });
        this.tag(Tags.Items.DUSTS).add(ModItems.COPPER_DUST.get());
        this.tag(Tags.Items.DUSTS).add(ModItems.IRON_DUST.get());
        this.tag(Tags.Items.DUSTS).add(ModItems.GOLD_DUST.get());
        this.tag(Tags.Items.DUSTS).add(ModItems.POTASH_DUST.get());
        this.tag(ModTags.Items.dustTags.get("copper_dust")).add(ModItems.COPPER_DUST.get());
        this.tag(ModTags.Items.dustTags.get("gold_dust")).add(ModItems.GOLD_DUST.get());
        this.tag(ModTags.Items.dustTags.get("iron_dust")).add(ModItems.IRON_DUST.get());
    }
}