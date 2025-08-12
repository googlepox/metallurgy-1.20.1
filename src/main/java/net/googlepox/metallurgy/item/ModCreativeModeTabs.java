package net.googlepox.metallurgy.item;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Metallurgy.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("metallurgy_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BITUMEN.get()))
                    .title(Component.translatable("creativetab.metallurgy_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.BITUMEN.get());
                        pOutput.accept(ModItems.ADAMANTINE_INGOT.get());
                        pOutput.accept(ModItems.ADAMANTINE_RAW.get());

                        pOutput.accept(ModBlocks.BITUMEN_BLOCK.get());
                        pOutput.accept(ModBlocks.ADAMANTINE_BLOCK.get());
                        pOutput.accept(ModBlocks.ADAMANTINE_BRICKS.get());

                        pOutput.accept(ModBlocks.ADAMANTINE_ORE.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_ADAMANTINE_ORE.get());
                        pOutput.accept(ModBlocks.NETHER_ADAMANTINE_ORE.get());
                        pOutput.accept(ModBlocks.END_STONE_ADAMANTINE_ORE.get());


                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
