package net.googlepox.metallurgy.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.block.ModBlocks;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.googlepox.metallurgy.datagen.integration.tconstruct.TConRecipeHelper;
import net.googlepox.metallurgy.integration.tic.material.MetallurgyTiCStats;
import net.googlepox.metallurgy.item.ModItems;
import net.googlepox.metallurgy.material.MetalStats;
import net.googlepox.metallurgy.recipe.CrusherRecipe;
import net.googlepox.metallurgy.util.ModTags;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.recipe.data.IRecipeHelper;
import slimeknights.mantle.recipe.helper.FluidOutput;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.casting.material.MaterialFluidRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.MaterialMeltingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;
import slimeknights.tconstruct.smeltery.data.SmelteryRecipeProvider;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder, TConRecipeHelper, IMaterialRecipeHelper {

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        Metallurgy.METALS.forEach((name, stats) -> {

            Item itemRaw = MetalRegistry.RAW_ITEMS.get(name).get();
            Item itemIngot = MetalRegistry.INGOTS.get(name).get();
            Item itemNugget = MetalRegistry.NUGGETS.get(name).get();
            Item itemDust = MetalRegistry.DUSTS.get(name).get();

            Block blockMetal = MetalRegistry.METAL_BLOCKS.get(name).get();
            Block blockBricks = MetalRegistry.METAL_BRICKS.get(name).get();

            if (MetalRegistry.ORE_BLOCKS.containsKey(name)) {

                createBlastingRecipe(name, itemRaw, itemIngot, pWriter);
                createSmeltingRecipe(name, itemRaw, itemIngot, pWriter);
                createCrushingRawRecipe(name, itemDust, pWriter);

            }

            if (MetalRegistry.TOOLS_PICKAXE.containsKey(name)) {

                createToolRecipes(name, itemIngot, pWriter);

            }

            createBlockAndIngotRecipes(name, itemIngot, itemNugget, blockMetal, blockBricks, pWriter);

            createCrushingIngotRecipe(name, itemDust, pWriter);

            createTinkerRecipes(name, stats, pWriter);

        });
    }

    private void createBlastingRecipe(String name, Item input, Item output, Consumer<FinishedRecipe> pWriter) {
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(input),
                        RecipeCategory.MISC,
                        output, 0.7f, 100)
                .unlockedBy("has_" + name + "_raw", has(input))
                .save(pWriter, prefix(MetalRegistry.INGOTS.get(name), name + blastingFolder));
    }

    private void createSmeltingRecipe(String name, Item input, Item output, Consumer<FinishedRecipe> pWriter) {
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(input),
                        RecipeCategory.MISC,
                        output, 0.7f, 100)
                .unlockedBy("has_" + name + "_raw", has(input))
                .save(pWriter, prefix(MetalRegistry.INGOTS.get(name), name + smeltingFolder));
    }

    private static void createCrushingRawRecipe(String name, Item dustOutput, Consumer<FinishedRecipe> pWriter) {
        List<TagKey<Item>> inputItemsRaw = new ArrayList<>();
        inputItemsRaw.add(TagKey.create(Registries.ITEM, new ResourceLocation("forge", "raw_materials/" + name)));
        ItemStack outputRaw = dustOutput.getDefaultInstance();
        outputRaw.setCount(2);
        pWriter.accept(new FinishedCrushingRecipe(
                new ResourceLocation(Metallurgy.MODID + ":" + crushingFolder + name + "/" + name + "_dust_from_crushing_raw"),
                inputItemsRaw,
                outputRaw));
    }

    private static void createCrushingIngotRecipe(String name, Item dustOutput, Consumer<FinishedRecipe> pWriter) {
        List<TagKey<Item>> inputItemsIngot = new ArrayList<>();
        inputItemsIngot.add(TagKey.create(Registries.ITEM, new ResourceLocation("forge", "ingots/" + name)));
        ItemStack outputRaw = MetalRegistry.DUSTS.get(name).get().getDefaultInstance();
        pWriter.accept(new FinishedCrushingRecipe(
                new ResourceLocation(Metallurgy.MODID + ":" + crushingFolder + name + "/" + name + "_dust_from_crushing_ingot"),
                inputItemsIngot,
                outputRaw));
    }

    private void createToolRecipes(String name, Item input, Consumer<FinishedRecipe> pWriter) {
        createPickaxeRecipe(name, input, MetalRegistry.TOOLS_PICKAXE.get(name).get(), pWriter);
        createSwordRecipe(name, input, MetalRegistry.TOOLS_SWORD.get(name).get(), pWriter);
        createAxeRecipe(name, input, MetalRegistry.TOOLS_AXE.get(name).get(), pWriter);
        createShovelRecipe(name, input, MetalRegistry.TOOLS_SHOVEL.get(name).get(), pWriter);
        createHoeRecipe(name, input, MetalRegistry.TOOLS_HOE.get(name).get(), pWriter);
    }

    private void createBlockAndIngotRecipes(String name, Item ingot, Item nugget, Block block, Block brick, Consumer<FinishedRecipe> pWriter) {
        createMetalBlockRecipe(name, ingot, block, pWriter);
        createMetalBrickRecipe(name, ingot, brick, pWriter);
        createIngotFromBlockRecipe(name, block, ingot, pWriter);
        createIngotFromBrickRecipe(name, brick, ingot, pWriter);
        createNuggetFromIngotRecipe(name, nugget, ingot, pWriter);
        createIngotFromNuggetRecipe(name, ingot, nugget, pWriter);
    }

    private void createMetalBlockRecipe(String name, Item ingot, Block block, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, block)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ingot)
                .unlockedBy(getHasName(ingot), has(ingot))
                .save(pWriter, prefix(MetalRegistry.METAL_BLOCKS.get(name), name + "/"));
    }

    private void createMetalBrickRecipe(String name, Item ingot, Block brick, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, brick)
                .pattern("SS")
                .pattern("SS")
                .define('S', ingot)
                .unlockedBy(getHasName(ingot), has(ingot))
                .save(pWriter, prefix(MetalRegistry.METAL_BRICKS.get(name), name + "/"));
    }

    private void createIngotFromBlockRecipe(String name, Block block, Item ingot, Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingot, 9)
                .requires(block)
                .unlockedBy(getHasName(block), has(block))
                .save(pWriter, prefix(ResourceLocation.parse(MetalRegistry.INGOTS.get(name).getId() + "_from_block"), name + ingotsFolder));
    }

    private void createIngotFromBrickRecipe(String name, Block brick, Item ingot, Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingot, 4)
                .requires(brick)
                .unlockedBy(getHasName(brick), has(brick))
                .save(pWriter, prefix(ResourceLocation.parse(MetalRegistry.INGOTS.get(name).getId() + "_from_brick"), name + ingotsFolder));
    }

    private void createIngotFromNuggetRecipe(String name, Item ingot, Item nugget, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ingot)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', nugget)
                .unlockedBy(getHasName(nugget), has(nugget))
                .save(pWriter, prefix(ResourceLocation.parse(MetalRegistry.INGOTS.get(name).get() + "_from_nuggets"), name + ingotsFolder));
    }

    private void createNuggetFromIngotRecipe(String name, Item nugget, Item ingot, Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nugget, 9)
                .requires(ingot)
                .unlockedBy(getHasName(ingot), has(ingot))
                .save(pWriter, prefix(ResourceLocation.parse(MetalRegistry.NUGGETS.get(name).getId() + "_from_ingot"), name + nuggetsFolder));
    }

    private void createPickaxeRecipe(String name, Item input, Item output, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("SSS")
                .pattern(" T ")
                .pattern(" T ")
                .define('S', input)
                .define('T', Tags.Items.RODS_WOODEN)
                .unlockedBy(getHasName(input), has(input))
                .save(pWriter, prefix(MetalRegistry.TOOLS_PICKAXE.get(name), toolsFolder));
    }

    private void createSwordRecipe(String name, Item input, Item output, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern(" S ")
                .pattern(" S ")
                .pattern(" T ")
                .define('S', input)
                .define('T', Tags.Items.RODS_WOODEN)
                .unlockedBy(getHasName(input), has(input))
                .save(pWriter, prefix(MetalRegistry.TOOLS_SWORD.get(name), toolsFolder));
    }

    private void createShovelRecipe(String name, Item input, Item output, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern(" S ")
                .pattern(" T ")
                .pattern(" T ")
                .define('S', input)
                .define('T', Tags.Items.RODS_WOODEN)
                .unlockedBy(getHasName(input), has(input))
                .save(pWriter, prefix(MetalRegistry.TOOLS_SHOVEL.get(name), toolsFolder));
    }

    private void createAxeRecipe(String name, Item input, Item output, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("SS ")
                .pattern("ST ")
                .pattern(" T ")
                .define('S', input)
                .define('T', Tags.Items.RODS_WOODEN)
                .unlockedBy(getHasName(input), has(input))
                .save(pWriter, prefix(MetalRegistry.TOOLS_AXE.get(name), toolsFolder));
    }

    private void createHoeRecipe(String name, Item input, Item output, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("SS ")
                .pattern(" T ")
                .pattern(" T ")
                .define('S', input)
                .define('T', Tags.Items.RODS_WOODEN)
                .unlockedBy(getHasName(input), has(input))
                .save(pWriter, prefix(MetalRegistry.TOOLS_HOE.get(name), toolsFolder));
    }

    private void createTinkerRecipes(String name, MetalStats stats, Consumer<FinishedRecipe> pWriter) {
        metalMaterialRecipe(pWriter, MetallurgyTiCStats.MATERIALS.get(name), materialFolder, name, false);
        materialMeltingCasting(pWriter, MetallurgyTiCStats.MATERIALS.get(name), MetalRegistry.METAL_FLUIDS.get(name), materialFolder);
        //buildMaterialRecipes(name, stats, pWriter);
        buildSmelteryRecipes(name, stats, pWriter);
        buildAlloyRecipes(name, stats, pWriter);
    }

    public void buildMaterialRecipes(String name, MetalStats stats, Consumer<FinishedRecipe> pWriter) {
        MaterialFluidRecipeBuilder.material(MetallurgyTiCStats.MATERIALS.get(name))
                .setTemperature(stats.getTemperature())
                .setFluid(ModTags.Fluids.fluidTags.get(name), FluidValues.INGOT)
                .save(pWriter, prefix(MetallurgyTiCStats.MATERIALS.get(name), materialCastingFolder));

        MaterialMeltingRecipeBuilder.material(MetallurgyTiCStats.MATERIALS.get(name), stats.getTemperature(), new FluidStack(MetalRegistry.METAL_FLUIDS.get(name).get(), FluidValues.INGOT))
                .save(pWriter, prefix(MetallurgyTiCStats.MATERIALS.get(name), materialMeltingFolder));

    }

    public void buildSmelteryRecipes(String name, MetalStats stats, Consumer<FinishedRecipe> pWriter) {

        ItemCastingRecipeBuilder.basinRecipe(ItemOutput.fromItem(MetalRegistry.METAL_BLOCKS.get(name).get()))
                .setFluidAndTime(MetalRegistry.METAL_FLUIDS.get(name), 900)
                .setCoolingTime(800)
                .save(pWriter, prefix(MetalRegistry.METAL_BLOCKS.get(name), smelteryCastingFolder + "block_casts/"));

        ItemCastingRecipeBuilder.tableRecipe(MetalRegistry.INGOTS.get(name).get())
                .setFluid(ModTags.Fluids.fluidTags.get(name), 100)
                .setCast(TinkerTags.Items.CASTS, false)
                .setCoolingTime(125)
                .save(pWriter, prefix(MetalRegistry.INGOTS.get(name), smelteryCastingFolder + "ingot_casts/"));

        MeltingRecipeBuilder.melting(Ingredient.of(MetalRegistry.INGOTS.get(name).get()),
                        FluidOutput.fromFluid(MetalRegistry.METAL_FLUIDS.get(name).get(), 100), stats.getTemperature(), (int) 32)
                .save(pWriter, prefix(MetalRegistry.INGOTS.get(name), smelteryMeltingFolder));

        MeltingRecipeBuilder.melting(Ingredient.of(MetalRegistry.DUSTS.get(name).get()),
                        FluidOutput.fromFluid(MetalRegistry.METAL_FLUIDS.get(name).get(), 100), stats.getTemperature(), (int) 32)
                .save(pWriter, prefix(MetalRegistry.DUSTS.get(name), smelteryMeltingFolder));

        if (stats.getOreStats() != null && stats.getOreStats().getHasOre()) {
            MeltingRecipeBuilder.melting(Ingredient.of(MetalRegistry.RAW_ITEMS.get(name).get()),
                            FluidOutput.fromFluid(MetalRegistry.METAL_FLUIDS.get(name).get(), 150), stats.getTemperature(), (int) 32)
                    .save(pWriter, prefix(MetalRegistry.RAW_ITEMS.get(name), smelteryMeltingFolder));
        }

        MeltingRecipeBuilder.melting(Ingredient.of(MetalRegistry.METAL_BLOCKS.get(name).get()),
                        FluidOutput.fromFluid(MetalRegistry.METAL_FLUIDS.get(name).get(), 900), stats.getTemperature(), (int) 32)
                .save(pWriter, prefix(MetalRegistry.METAL_BLOCKS.get(name), smelteryMeltingFolder));

        MeltingRecipeBuilder.melting(Ingredient.of(MetalRegistry.METAL_BRICKS.get(name).get()),
                        FluidOutput.fromFluid(MetalRegistry.METAL_FLUIDS.get(name).get(), 400), stats.getTemperature(), (int) 32)
                .save(pWriter, prefix(MetalRegistry.METAL_BRICKS.get(name), smelteryMeltingFolder));

        MeltingRecipeBuilder.melting(Ingredient.of(MetalRegistry.NUGGETS.get(name).get()),
                        FluidOutput.fromFluid(MetalRegistry.METAL_FLUIDS.get(name).get(), 10), stats.getTemperature(), (int) 32)
                .save(pWriter, prefix(MetalRegistry.NUGGETS.get(name), smelteryMeltingFolder));

        MaterialMeltingRecipeBuilder.material(MetallurgyTiCStats.MATERIALS.get(name),
                        new FluidStack(MetalRegistry.METAL_FLUIDS.get(name).get(), 100))
                .save(pWriter, prefix(MetallurgyTiCStats.MATERIALS.get(name), smelteryMeltingFolder));
    }

    public void buildAlloyRecipes(String name, MetalStats stats, Consumer<FinishedRecipe> pWriter) {
        /*
        AlloyRecipeBuilder.alloy(FluidOutput.fromTag(ModTags.Fluids.ETHERIC, 270), 2500)
                .addInput(TinkerFluids.moltenSlimesteel.get(), FluidValues.INGOT)
                .addInput(TicEXTags.Fluids.HEPATIZON, FluidValues.INGOT)
                .addInput(TicEXTags.Fluids.GOLD, FluidValues.INGOT)
                .addInput(TicEXRegistry.MOLTEN_RECONSTRUCTION_CORE.get(), 250)
                .save(pWriter, prefix(TicEXTags.Fluids.ETHERIC.location(), alloysFolder)); */
    }

    // Custom FinishedRecipe implementation for our recipe type
    private static class FinishedCrushingRecipe implements FinishedRecipe {
        private final ResourceLocation id;
        private final List<TagKey<Item>> ingredients;
        private final ItemStack output;

        public FinishedCrushingRecipe(ResourceLocation id, List<TagKey<Item>> ingredients, ItemStack output) {
            this.id = id;
            this.ingredients = ingredients;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray input = new JsonArray();
            for (TagKey<Item> tag : ingredients) {
                JsonObject ingredientObj = new JsonObject();
                ingredientObj.addProperty("tag", tag.location().toString());
                input.add(ingredientObj);
            }
            json.add("ingredients", input);
            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("item", BuiltInRegistries.ITEM.getKey(output.getItem()).toString());
            resultObj.addProperty("count", output.getCount());
            json.add("result", resultObj);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return CrusherRecipe.Serializer.INSTANCE;
        }

        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}