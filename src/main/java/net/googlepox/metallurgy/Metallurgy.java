package net.googlepox.metallurgy;

import com.mojang.logging.LogUtils;
import net.googlepox.metallurgy.block.ModBlocks;
import net.googlepox.metallurgy.block.entity.ModBlockEntities;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.googlepox.metallurgy.item.ModCreativeModeTabs;
import net.googlepox.metallurgy.item.ModItems;
import net.googlepox.metallurgy.material.MetalStats;
import net.googlepox.metallurgy.material.ModMetals;
import net.googlepox.metallurgy.recipe.ModRecipes;
import net.googlepox.metallurgy.screen.CrusherScreen;
import net.googlepox.metallurgy.screen.ModMenuTypes;
import net.googlepox.metallurgy.util.ModTags;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;


@Mod(Metallurgy.MODID)
public class Metallurgy {
    public static final String MODID = "metallurgy";
    public static final String NAME = "Metallurgy 5: Unofficial";
    public static final Logger logger = LogUtils.getLogger();
    public static String materialConfig;

    public static final Map<String, MetalStats> METALS = new HashMap<>();


    public Metallurgy() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        logger.info(NAME + " is entering pre-initialization!");

        materialConfig = "src/main/resources/assets/metallurgy/materials.json";

        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModMetals.init();

        ModTags.Items.createTags();
        ModTags.Fluids.createTags();

        MetalRegistry.BLOCKS.register(modEventBus);
        MetalRegistry.ITEMS.register(modEventBus);
        MetalRegistry.FLUIDS.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);


        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.CRUSHING_MENU.get(), CrusherScreen::new);
        }
    }
}
