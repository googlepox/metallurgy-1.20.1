package net.googlepox.metallurgy.item;

import net.googlepox.metallurgy.Metallurgy;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Metallurgy.MOD_ID);

    public static final RegistryObject<Item> BITUMEN = ITEMS.register("bitumen",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ADAMANTINE_INGOT = ITEMS.register("adamantine_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ADAMANTINE_RAW = ITEMS.register("adamantine_raw",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
