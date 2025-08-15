package net.googlepox.metallurgy.item;

import net.googlepox.metallurgy.Metallurgy;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Metallurgy.MODID);

    public static final RegistryObject<Item> BITUMEN = ITEMS.register("bitumen",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GOLD_DUST = ITEMS.register("gold_dust",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COPPER_DUST = ITEMS.register("copper_dust",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> IRON_DUST = ITEMS.register("iron_dust",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SULFUR = ITEMS.register("dust_sulfur",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> POTASH = ITEMS.register("potash",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PHOSPHORUS = ITEMS.register("phosphorus",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> POTASH_DUST = ITEMS.register("potash_fertilizer",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TAR = ITEMS.register("tar",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> THERMITE_DUST = ITEMS.register("thermite_dust",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> IGNATIUS_FUEL = ITEMS.register("ignatius_fuel",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
