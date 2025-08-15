package net.googlepox.metallurgy.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemTagHelper {

    /**
     * Modern replacement for OreDictionary.getOreIDs(stack).
     */
    public static List<ResourceLocation> getItemTags(ItemStack stack) {
        List<ResourceLocation> result = new ArrayList<>();
        Item item = stack.getItem();

        BuiltInRegistries.ITEM.getResourceKey(item)
                .flatMap(BuiltInRegistries.ITEM::getHolder)
                .ifPresent(holder -> {
                    for (TagKey<Item> tag : holder.tags().toList()) {
                        result.add(tag.location());
                    }
                });

        return result;
    }

    /**
     * Modern replacement for OreDictionary.itemMatches.
     *
     * @param stack The ItemStack to check.
     * @param tagId The tag ResourceLocation (e.g. forge:ingots/copper).
     * @param strictNBT Whether to require NBT to match (false = ignore NBT).
     * @return True if the stack matches the given tag.
     */
    public static boolean itemMatchesTag(ItemStack stack, ResourceLocation tagId, boolean strictNBT) {
        if (stack.isEmpty()) return false;

        TagKey<Item> tagKey = TagKey.create(BuiltInRegistries.ITEM.key(), tagId);

        // Does the item have this tag?
        boolean hasTag = stack.is(tagKey);

        if (!hasTag) return false;

        // If strictNBT is true, compare NBT exactly
        if (strictNBT) {
            // Here you could compare with a reference stack's NBT if needed
            return stack.hasTag() && !stack.getTag().isEmpty();
        }

        // Ignore NBT if strictNBT == false
        return true;
    }
}
