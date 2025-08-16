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
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import org.checkerframework.checker.units.qual.A;

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
            if (MetalRegistry.ORE_BLOCKS.containsKey(name)) {
                SimpleCookingRecipeBuilder.smelting(
                                Ingredient.of(MetalRegistry.RAW_ITEMS.get(name).get()),
                                RecipeCategory.MISC,
                                MetalRegistry.INGOTS.get(name).get(), 0.7f, 200)
                        .unlockedBy("has_" + name + "_raw", has(MetalRegistry.RAW_ITEMS.get(name).get()))
                        .save(pWriter);

                SimpleCookingRecipeBuilder.blasting(
                                Ingredient.of(MetalRegistry.RAW_ITEMS.get(name).get()),
                                RecipeCategory.MISC,
                                MetalRegistry.INGOTS.get(name).get(), 0.7f, 100)
                        .unlockedBy("has_" + name + "_raw", has(MetalRegistry.RAW_ITEMS.get(name).get()))
                        .save(pWriter, Metallurgy.MODID + ":" + name + "_ingot_from_blasting");

                SimpleCookingRecipeBuilder.smelting(
                                Ingredient.of(MetalRegistry.RAW_ITEMS.get(name).get()),
                                RecipeCategory.MISC,
                                MetalRegistry.INGOTS.get(name).get(), 0.5f, 200)
                        .unlockedBy("has_" + name + "_raw", has(MetalRegistry.RAW_ITEMS.get(name).get()))
                        .save(pWriter, Metallurgy.MODID + ":" + name + "_ingot_from_smelting");

                List<TagKey<Item>> inputItemsRaw = new ArrayList<>();
                inputItemsRaw.add(TagKey.create(Registries.ITEM, new ResourceLocation("forge", "raw_materials/" + name)));
                ItemStack outputRaw = MetalRegistry.DUSTS.get(name).get().getDefaultInstance();
                outputRaw.setCount(2);
                pWriter.accept(new FinishedCrushingRecipe(
                        new ResourceLocation(Metallurgy.MODID + ":" + name + "_dust_from_crushing_raw"),
                        inputItemsRaw,
                        outputRaw));

            }

            if (MetalRegistry.TOOLS_PICKAXE.containsKey(name)) {
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, MetalRegistry.TOOLS_PICKAXE.get(name).get())
                        .pattern("SSS")
                        .pattern(" T ")
                        .pattern(" T ")
                        .define('S', MetalRegistry.INGOTS.get(name).get())
                        .define('T', Tags.Items.RODS_WOODEN)
                        .unlockedBy(getHasName(MetalRegistry.INGOTS.get(name).get()), has(MetalRegistry.INGOTS.get(name).get()))
                        .save(pWriter, Metallurgy.MODID + ":" + name + "_pickaxe_from_ingots");

                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, MetalRegistry.TOOLS_AXE.get(name).get())
                        .pattern("SS ")
                        .pattern("ST ")
                        .pattern(" T ")
                        .define('S', MetalRegistry.INGOTS.get(name).get())
                        .define('T', Tags.Items.RODS_WOODEN)
                        .unlockedBy(getHasName(MetalRegistry.INGOTS.get(name).get()), has(MetalRegistry.INGOTS.get(name).get()))
                        .save(pWriter, Metallurgy.MODID + ":" + name + "_axe_from_ingots");

                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, MetalRegistry.TOOLS_SWORD.get(name).get())
                        .pattern(" S ")
                        .pattern(" S ")
                        .pattern(" T ")
                        .define('S', MetalRegistry.INGOTS.get(name).get())
                        .define('T', Tags.Items.RODS_WOODEN)
                        .unlockedBy(getHasName(MetalRegistry.INGOTS.get(name).get()), has(MetalRegistry.INGOTS.get(name).get()))
                        .save(pWriter, Metallurgy.MODID + ":" + name + "_sword_from_ingots");

                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, MetalRegistry.TOOLS_SHOVEL.get(name).get())
                        .pattern(" S ")
                        .pattern(" T ")
                        .pattern(" T ")
                        .define('S', MetalRegistry.INGOTS.get(name).get())
                        .define('T', Tags.Items.RODS_WOODEN)
                        .unlockedBy(getHasName(MetalRegistry.INGOTS.get(name).get()), has(MetalRegistry.INGOTS.get(name).get()))
                        .save(pWriter, Metallurgy.MODID + ":" + name + "_shovel_from_ingots");

                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, MetalRegistry.TOOLS_HOE.get(name).get())
                        .pattern("SS ")
                        .pattern(" T ")
                        .pattern(" T ")
                        .define('S', MetalRegistry.INGOTS.get(name).get())
                        .define('T', Tags.Items.RODS_WOODEN)
                        .unlockedBy(getHasName(MetalRegistry.INGOTS.get(name).get()), has(MetalRegistry.INGOTS.get(name).get()))
                        .save(pWriter, Metallurgy.MODID + ":" + name + "_hoe_from_ingots");
            }

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MetalRegistry.METAL_BLOCKS.get(name).get())
                    .pattern("SSS")
                    .pattern("SSS")
                    .pattern("SSS")
                    .define('S', MetalRegistry.INGOTS.get(name).get())
                    .unlockedBy(getHasName(MetalRegistry.INGOTS.get(name).get()), has(MetalRegistry.INGOTS.get(name).get()))
                    .save(pWriter, Metallurgy.MODID + ":" + name + "_block_from_ingots");

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MetalRegistry.INGOTS.get(name).get())
                    .pattern("SSS")
                    .pattern("SSS")
                    .pattern("SSS")
                    .define('S', MetalRegistry.NUGGETS.get(name).get())
                    .unlockedBy(getHasName(MetalRegistry.NUGGETS.get(name).get()), has(MetalRegistry.NUGGETS.get(name).get()))
                    .save(pWriter, Metallurgy.MODID + ":" + name + "_ingot_from_nuggets");

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MetalRegistry.METAL_BRICKS.get(name).get())
                    .pattern("SS")
                    .pattern("SS")
                    .define('S', MetalRegistry.INGOTS.get(name).get())
                    .unlockedBy(getHasName(MetalRegistry.INGOTS.get(name).get()), has(MetalRegistry.INGOTS.get(name).get()))
                    .save(pWriter, Metallurgy.MODID + ":" + name + "_bricks_from_ingots");

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MetalRegistry.INGOTS.get(name).get(), 9)
                    .requires(MetalRegistry.METAL_BLOCKS.get(name).get())
                    .unlockedBy(getHasName(MetalRegistry.METAL_BLOCKS.get(name).get()), has(MetalRegistry.METAL_BLOCKS.get(name).get()))
                    .save(pWriter, Metallurgy.MODID + ":" + name + "_ingots_from_block");

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MetalRegistry.NUGGETS.get(name).get(), 9)
                    .requires(MetalRegistry.INGOTS.get(name).get())
                    .unlockedBy(getHasName(MetalRegistry.INGOTS.get(name).get()), has(MetalRegistry.INGOTS.get(name).get()))
                    .save(pWriter, Metallurgy.MODID + ":" + name + "_nuggets_from_ingot");

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MetalRegistry.INGOTS.get(name).get(), 4)
                    .requires(MetalRegistry.METAL_BRICKS.get(name).get())
                    .unlockedBy(getHasName(MetalRegistry.METAL_BRICKS.get(name).get()), has(MetalRegistry.METAL_BRICKS.get(name).get()))
                    .save(pWriter, Metallurgy.MODID + ":" + name + "_ingots_from_bricks");


            List<TagKey<Item>> inputItemsIngot = new ArrayList<>();
            inputItemsIngot.add(TagKey.create(Registries.ITEM, new ResourceLocation("forge", "ingots/" + name)));
            ItemStack outputRaw = MetalRegistry.DUSTS.get(name).get().getDefaultInstance();
            pWriter.accept(new FinishedCrushingRecipe(
                    new ResourceLocation(Metallurgy.MODID + ":" + name + "_dust_from_crushing_ingot"),
                    inputItemsIngot,
                    outputRaw));
        });
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