package net.googlepox.metallurgy.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.block.ModBlocks;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.googlepox.metallurgy.item.ModItems;
import net.googlepox.metallurgy.recipe.CrusherRecipe;
import net.googlepox.metallurgy.util.ModTags;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
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
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        Metallurgy.METALS.keySet().forEach(name -> {

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

        });
    }

    private static void createBlastingRecipe(String name, Item input, Item output, Consumer<FinishedRecipe> pWriter) {
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(input),
                        RecipeCategory.MISC,
                        output, 0.7f, 100)
                .unlockedBy("has_" + name + "_raw", has(input))
                .save(pWriter, Metallurgy.MODID + ":" + name + "_ingot_from_blasting");
    }

    private static void createSmeltingRecipe(String name, Item input, Item output, Consumer<FinishedRecipe> pWriter) {
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(input),
                        RecipeCategory.MISC,
                        output, 0.7f, 100)
                .unlockedBy("has_" + name + "_raw", has(input))
                .save(pWriter, Metallurgy.MODID + ":" + name + "_ingot_from_smelting");
    }

    private static void createCrushingRawRecipe(String name, Item dustOutput, Consumer<FinishedRecipe> pWriter) {
        List<TagKey<Item>> inputItemsRaw = new ArrayList<>();
        inputItemsRaw.add(TagKey.create(Registries.ITEM, new ResourceLocation("forge", "raw_materials/" + name)));
        ItemStack outputRaw = dustOutput.getDefaultInstance();
        outputRaw.setCount(2);
        pWriter.accept(new FinishedCrushingRecipe(
                new ResourceLocation(Metallurgy.MODID + ":" + name + "_dust_from_crushing_raw"),
                inputItemsRaw,
                outputRaw));
    }

    private static void createCrushingIngotRecipe(String name, Item dustOutput, Consumer<FinishedRecipe> pWriter) {
        List<TagKey<Item>> inputItemsIngot = new ArrayList<>();
        inputItemsIngot.add(TagKey.create(Registries.ITEM, new ResourceLocation("forge", "ingots/" + name)));
        ItemStack outputRaw = MetalRegistry.DUSTS.get(name).get().getDefaultInstance();
        pWriter.accept(new FinishedCrushingRecipe(
                new ResourceLocation(Metallurgy.MODID + ":" + name + "_dust_from_crushing_ingot"),
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
                .save(pWriter, Metallurgy.MODID + ":" + name + "_block_from_ingots");
    }

    private void createMetalBrickRecipe(String name, Item ingot, Block brick, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, brick)
                .pattern("SS")
                .pattern("SS")
                .define('S', ingot)
                .unlockedBy(getHasName(ingot), has(ingot))
                .save(pWriter, Metallurgy.MODID + ":" + name + "_bricks_from_ingots");
    }

    private void createIngotFromBlockRecipe(String name, Block block, Item ingot, Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingot, 9)
                .requires(block)
                .unlockedBy(getHasName(block), has(block))
                .save(pWriter, Metallurgy.MODID + ":" + name + "_ingots_from_block");
    }

    private void createIngotFromBrickRecipe(String name, Block brick, Item ingot, Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingot, 4)
                .requires(brick)
                .unlockedBy(getHasName(brick), has(brick))
                .save(pWriter, Metallurgy.MODID + ":" + name + "_ingots_from_bricks");
    }

    private void createIngotFromNuggetRecipe(String name, Item ingot, Item nugget, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ingot)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', nugget)
                .unlockedBy(getHasName(nugget), has(nugget))
                .save(pWriter, Metallurgy.MODID + ":" + name + "_ingot_from_nuggets");
    }

    private void createNuggetFromIngotRecipe(String name, Item nugget, Item ingot, Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nugget, 9)
                .requires(ingot)
                .unlockedBy(getHasName(ingot), has(ingot))
                .save(pWriter, Metallurgy.MODID + ":" + name + "_nuggets_from_ingot");
    }

    private static void createPickaxeRecipe(String name, Item input, Item output, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("SSS")
                .pattern(" T ")
                .pattern(" T ")
                .define('S', input)
                .define('T', Tags.Items.RODS_WOODEN)
                .unlockedBy(getHasName(input), has(input))
                .save(pWriter, Metallurgy.MODID + ":" + name + "_pickaxe_from_ingots");
    }

    private static void createSwordRecipe(String name, Item input, Item output, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern(" S ")
                .pattern(" S ")
                .pattern(" T ")
                .define('S', input)
                .define('T', Tags.Items.RODS_WOODEN)
                .unlockedBy(getHasName(input), has(input))
                .save(pWriter, Metallurgy.MODID + ":" + name + "_sword_from_ingots");
    }

    private static void createShovelRecipe(String name, Item input, Item output, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern(" S ")
                .pattern(" T ")
                .pattern(" T ")
                .define('S', input)
                .define('T', Tags.Items.RODS_WOODEN)
                .unlockedBy(getHasName(input), has(input))
                .save(pWriter, Metallurgy.MODID + ":" + name + "_shovel_from_ingots");
    }

    private static void createAxeRecipe(String name, Item input, Item output, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("SS ")
                .pattern("ST ")
                .pattern(" T ")
                .define('S', input)
                .define('T', Tags.Items.RODS_WOODEN)
                .unlockedBy(getHasName(input), has(input))
                .save(pWriter, Metallurgy.MODID + ":" + name + "_axe_from_ingots");
    }

    private static void createHoeRecipe(String name, Item input, Item output, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, output)
                .pattern("SS ")
                .pattern(" T ")
                .pattern(" T ")
                .define('S', input)
                .define('T', Tags.Items.RODS_WOODEN)
                .unlockedBy(getHasName(input), has(input))
                .save(pWriter, Metallurgy.MODID + ":" + name + "_hoe_from_ingots");
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