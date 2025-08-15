package net.googlepox.metallurgy.item;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.block.ModBlocks;
import net.googlepox.metallurgy.core.MetalRegistry;
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
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Metallurgy.MODID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("metallurgy_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(MetalRegistry.INGOTS.get("adamantine").get()))
                    .title(Component.translatable("creativetab.metallurgy_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        Metallurgy.METALS.keySet().forEach(name -> {
                            if (MetalRegistry.ORE_BLOCKS.containsKey(name)) {
                                pOutput.accept(MetalRegistry.ORE_BLOCKS.get(name).get());
                                pOutput.accept(MetalRegistry.DEEPSLATE_ORE_BLOCKS.get(name).get());
                                pOutput.accept(MetalRegistry.NETHER_ORE_BLOCKS.get(name).get());
                                pOutput.accept(MetalRegistry.ENDSTONE_ORE_BLOCKS.get(name).get());
                                pOutput.accept(MetalRegistry.RAW_ITEMS.get(name).get());
                            }
                            if (MetalRegistry.TOOLS_PICKAXE.containsKey(name)) {
                                pOutput.accept(MetalRegistry.TOOLS_PICKAXE.get(name).get());
                                pOutput.accept(MetalRegistry.TOOLS_SWORD.get(name).get());
                                pOutput.accept(MetalRegistry.TOOLS_AXE.get(name).get());
                                pOutput.accept(MetalRegistry.TOOLS_SHOVEL.get(name).get());
                                pOutput.accept(MetalRegistry.TOOLS_HOE.get(name).get());
                            }
                            pOutput.accept(MetalRegistry.INGOTS.get(name).get());
                            pOutput.accept(MetalRegistry.NUGGETS.get(name).get());
                            pOutput.accept(MetalRegistry.DUSTS.get(name).get());
                            pOutput.accept(MetalRegistry.METAL_BLOCKS.get(name).get());
                            pOutput.accept(MetalRegistry.METAL_BRICKS.get(name).get());

                        });
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
