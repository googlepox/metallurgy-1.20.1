package net.googlepox.metallurgy.datagen;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.datagen.integration.tconstruct.TConMaterialProvider;
import net.googlepox.metallurgy.datagen.integration.tconstruct.TConMaterialRenderInfoProvider;
import net.googlepox.metallurgy.datagen.integration.tconstruct.TConMaterialSpriteProvider;
import net.googlepox.metallurgy.datagen.integration.tconstruct.TConMaterialStatsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.client.data.material.MaterialPaletteDebugGenerator;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Metallurgy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        boolean server = event.includeServer();
        boolean client = event.includeClient();

        generator.addProvider(server, ModLootTableProvider.create(packOutput));

        generator.addProvider(client, new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(client, new ModItemModelProvider(packOutput, existingFileHelper));

        generator.addProvider(client, new ModFluidTagProvider(packOutput, lookupProvider));

        ModBlockTagProvider blockTagGenerator = generator.addProvider(server,
                new ModBlockTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(server, new ModItemTagProvider(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));

        generator.addProvider(server, new ModWorldGenProvider(packOutput, lookupProvider));

        generator.addProvider(client, new ModLanguageProvider(packOutput, Metallurgy.MODID, "en_us"));

        TConMaterialProvider materialDefinitionProvider = new TConMaterialProvider(packOutput);
        generator.addProvider(server, materialDefinitionProvider);
        generator.addProvider(server, new TConMaterialStatsProvider(packOutput, materialDefinitionProvider));
        TConMaterialSpriteProvider materialSprites = new TConMaterialSpriteProvider();

        generator.addProvider(client, new TConMaterialRenderInfoProvider(packOutput, materialSprites, existingFileHelper));

        generator.addProvider(server, new ModFluidTextureProvider(packOutput));

        generator.addProvider(server, new ModRecipeProvider(packOutput));
    }
}