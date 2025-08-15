package net.googlepox.metallurgy.datagen;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.googlepox.metallurgy.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, Metallurgy.MODID, helper);
    }

    @Override
    protected void registerModels() {
        Metallurgy.METALS.keySet().forEach(name -> {
            if (MetalRegistry.ORE_BLOCKS.containsKey(name)) {
                withExistingParent(name + "_ore", modLoc("block/" + name + "_ore"));
                singleTexture(name + "_raw", mcLoc("item/generated"),
                        "layer0", modLoc("item/" + name + "_raw"));
            }
            if (MetalRegistry.TOOLS_PICKAXE.containsKey(name)) {
                handheldItem(MetalRegistry.TOOLS_PICKAXE.get(name));
                handheldItem(MetalRegistry.TOOLS_SWORD.get(name));
                handheldItem(MetalRegistry.TOOLS_AXE.get(name));
                handheldItem(MetalRegistry.TOOLS_SHOVEL.get(name));
                handheldItem(MetalRegistry.TOOLS_HOE.get(name));
            }
            singleTexture(name + "_ingot", mcLoc("item/generated"),
                    "layer0", modLoc("item/" + name + "_ingot"));
            singleTexture(name + "_nugget", mcLoc("item/generated"),
                    "layer0", modLoc("item/" + name + "_nugget"));
            singleTexture(name + "_dust", mcLoc("item/generated"),
                    "layer0", modLoc("item/" + name + "_dust"));

        });

        simpleItem(ModItems.BITUMEN);
        simpleItem(ModItems.PHOSPHORUS);
        simpleItem(ModItems.SULFUR);
        simpleItem(ModItems.TAR);
        simpleItem(ModItems.POTASH);
        simpleItem(ModItems.POTASH_DUST);
        simpleItem(ModItems.GOLD_DUST);
        simpleItem(ModItems.IRON_DUST);
        simpleItem(ModItems.COPPER_DUST);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Metallurgy.MODID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(Metallurgy.MODID,"item/tools/" + item.getId().getPath()));
    }
}