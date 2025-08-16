package net.googlepox.metallurgy.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.googlepox.metallurgy.Metallurgy;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CrusherRecipe implements Recipe<SimpleContainer> {
    private final List<TagKey<Item>> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int count;

    public CrusherRecipe(List<TagKey<Item>> inputItems, ItemStack output, int count, ResourceLocation id) {
        this.inputItems = inputItems;
        this.output = output;
        this.count = output.getCount();
        this.id = id;
    }

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        for (TagKey<Item> tag : inputItems) {
            ingredients.add(Ingredient.of(tag));
        }
        return ingredients;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        List<Ingredient> ingredients = getIngredients();

        // Make a mutable list of ingredients to track which have been matched
        List<Ingredient> remaining = new ArrayList<>(ingredients);

        for (int i = 0; i < pContainer.getContainerSize(); i++) {
            ItemStack stack = pContainer.getItem(i);
            if (stack.isEmpty()) continue;

            // Find an ingredient that matches this stack
            boolean matched = false;
            for (Iterator<Ingredient> it = remaining.iterator(); it.hasNext();) {
                Ingredient ing = it.next();
                if (ing.test(stack)) {
                    matched = true;
                    it.remove(); // remove matched ingredient
                    break;
                }
            }

            // If the stack didn't match any remaining ingredient, recipe doesn't match
            if (!matched) return false;
        }

        // Recipe matches if all ingredients were matched
        return remaining.isEmpty();
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<CrusherRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "crushing";
    }

    public static class Serializer implements RecipeSerializer<CrusherRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Metallurgy.MODID, "crushing");

        @Override
        public CrusherRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            JsonObject result = GsonHelper.getAsJsonObject(pSerializedRecipe, "result");
            ItemStack output = ShapedRecipe.itemStackFromJson(result);

            int count = output.getCount();

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            List<TagKey<Item>> inputs = new ArrayList<>();

            for (JsonElement elem : ingredients) {
                JsonObject obj = elem.getAsJsonObject();
                ResourceLocation tagLocation = new ResourceLocation(GsonHelper.getAsString(obj, "tag"));
                TagKey<Item> tag = TagKey.create(Registries.ITEM, tagLocation);
                inputs.add(tag);
            }

            return new CrusherRecipe(inputs, output, count, pRecipeId);
        }

        @Override
        public @Nullable CrusherRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            int size = pBuffer.readVarInt();
            List<TagKey<Item>> inputs = new ArrayList<>();

            for(int i = 0; i < inputs.size(); i++) {
                inputs.add(TagKey.create(Registries.ITEM, pBuffer.readResourceLocation()));
            }

            ItemStack output = pBuffer.readItem();
            int count = pBuffer.readItem().getCount();
            return new CrusherRecipe(inputs, output, count, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CrusherRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}