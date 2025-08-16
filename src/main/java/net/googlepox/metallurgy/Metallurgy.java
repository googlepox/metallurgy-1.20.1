package net.googlepox.metallurgy;

import com.mojang.logging.LogUtils;
import net.googlepox.metallurgy.block.ModBlocks;
import net.googlepox.metallurgy.block.entity.ModBlockEntities;
import net.googlepox.metallurgy.config.CommonConfig;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.googlepox.metallurgy.datagen.ModItemTagProvider;
import net.googlepox.metallurgy.item.ModCreativeModeTabs;
import net.googlepox.metallurgy.item.ModItems;
import net.googlepox.metallurgy.material.MetalStats;
import net.googlepox.metallurgy.material.ModMetals;
import net.googlepox.metallurgy.recipe.ModRecipes;
import net.googlepox.metallurgy.screen.CrusherScreen;
import net.googlepox.metallurgy.screen.ModMenuTypes;
import net.googlepox.metallurgy.util.ModTags;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
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

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC, "metallurgy-common.toml");

        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModMetals.init();

        ModTags.Items.createTags();

        MetalRegistry.BLOCKS.register(modEventBus);
        MetalRegistry.ITEMS.register(modEventBus);

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

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.CRUSHING_MENU.get(), CrusherScreen::new);
        }
    }
}
