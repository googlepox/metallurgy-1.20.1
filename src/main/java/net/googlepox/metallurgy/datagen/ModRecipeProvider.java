package net.googlepox.metallurgy.datagen;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.block.ModBlocks;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.googlepox.metallurgy.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

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
                    .save(pWriter, Metallurgy.MODID + ":" + name + "block_from_ingots");

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MetalRegistry.INGOTS.get(name).get())
                    .pattern("SSS")
                    .pattern("SSS")
                    .pattern("SSS")
                    .define('S', MetalRegistry.NUGGETS.get(name).get())
                    .unlockedBy(getHasName(MetalRegistry.NUGGETS.get(name).get()), has(MetalRegistry.NUGGETS.get(name).get()))
                    .save(pWriter, Metallurgy.MODID + ":" + name + "ingot_from_nuggets");

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MetalRegistry.METAL_BRICKS.get(name).get())
                    .pattern("SS")
                    .pattern("SS")
                    .define('S', MetalRegistry.INGOTS.get(name).get())
                    .unlockedBy(getHasName(MetalRegistry.INGOTS.get(name).get()), has(MetalRegistry.INGOTS.get(name).get()))
                    .save(pWriter, Metallurgy.MODID + ":" + name + "bricks_from_ingots");

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MetalRegistry.INGOTS.get(name).get(), 9)
                    .requires(MetalRegistry.METAL_BLOCKS.get(name).get())
                    .unlockedBy(getHasName(MetalRegistry.METAL_BLOCKS.get(name).get()), has(MetalRegistry.METAL_BLOCKS.get(name).get()))
                    .save(pWriter, Metallurgy.MODID + ":" + name + "ingots_from_block");

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MetalRegistry.NUGGETS.get(name).get(), 9)
                    .requires(MetalRegistry.INGOTS.get(name).get())
                    .unlockedBy(getHasName(MetalRegistry.INGOTS.get(name).get()), has(MetalRegistry.INGOTS.get(name).get()))
                    .save(pWriter, Metallurgy.MODID + ":" + name + "nuggets_from_ingot");

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MetalRegistry.INGOTS.get(name).get(), 4)
                    .requires(MetalRegistry.METAL_BRICKS.get(name).get())
                    .unlockedBy(getHasName(MetalRegistry.METAL_BRICKS.get(name).get()), has(MetalRegistry.METAL_BRICKS.get(name).get()))
                    .save(pWriter, Metallurgy.MODID + ":" + name + "ingots_from_bricks");
        });
    }
}