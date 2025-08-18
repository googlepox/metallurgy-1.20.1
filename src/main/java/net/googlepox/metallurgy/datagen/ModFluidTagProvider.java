package net.googlepox.metallurgy.datagen;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.googlepox.metallurgy.item.ModItems;
import net.googlepox.metallurgy.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagProvider extends FluidTagsProvider {


    public ModFluidTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider) {
        super(pOutput, pProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        Metallurgy.METALS.forEach((name, stats) -> {
            this.fluidTag(ModTags.Fluids.fluidTags.get(name), new ResourceLocation(Metallurgy.MODID, "molten_" + name));
        });
    }

    private void fluidTag(TagKey<Fluid> tagKey, ResourceLocation... rls) {
        for (ResourceLocation rl : rls) {
            this.tag(tagKey).addOptional(rl);
        }
    }
}
