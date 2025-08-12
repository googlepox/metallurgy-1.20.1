package net.googlepox.metallurgy.datagen;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.block.ModBlocks;
import net.googlepox.metallurgy.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> ADAMANTINE_SMELTABLES = List.of(ModItems.ADAMANTINE_RAW.get(),
            ModBlocks.ADAMANTINE_ORE.get(), ModBlocks.DEEPSLATE_ADAMANTINE_ORE.get(), ModBlocks.NETHER_ADAMANTINE_ORE.get(),
            ModBlocks.END_STONE_ADAMANTINE_ORE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreSmelting(pWriter, ADAMANTINE_SMELTABLES, RecipeCategory.MISC, ModItems.ADAMANTINE_INGOT.get(), 0.25f, 200, "sapphire");
        oreBlasting(pWriter, ADAMANTINE_SMELTABLES, RecipeCategory.MISC, ModItems.ADAMANTINE_INGOT.get(), 0.25f, 100, "sapphire");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ADAMANTINE_BLOCK.get())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.ADAMANTINE_INGOT.get())
                .unlockedBy(getHasName(ModItems.ADAMANTINE_INGOT.get()), has(ModItems.ADAMANTINE_INGOT.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ADAMANTINE_BRICKS.get())
                .pattern("SS")
                .pattern("SS")
                .define('S', ModItems.ADAMANTINE_INGOT.get())
                .unlockedBy(getHasName(ModItems.ADAMANTINE_INGOT.get()), has(ModItems.ADAMANTINE_INGOT.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ADAMANTINE_INGOT.get(), 9)
                .requires(ModBlocks.ADAMANTINE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.ADAMANTINE_BLOCK.get()), has(ModBlocks.ADAMANTINE_BLOCK.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ADAMANTINE_INGOT.get(), 4)
                .requires(ModBlocks.ADAMANTINE_BRICKS.get())
                .unlockedBy(getHasName(ModBlocks.ADAMANTINE_BRICKS.get()), has(ModBlocks.ADAMANTINE_BRICKS.get()))
                .save(pWriter);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                            pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  Metallurgy.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}