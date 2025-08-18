package net.googlepox.metallurgy.datagen;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.datagen.integration.tconstruct.TConMaterialProvider;
import net.googlepox.metallurgy.datagen.integration.tconstruct.TConMaterialStatsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Metallurgy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput));

        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));

        generator.addProvider(event.includeClient(), new ModFluidTagProvider(packOutput, lookupProvider));

        ModBlockTagProvider blockTagGenerator = generator.addProvider(event.includeServer(),
                new ModBlockTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagProvider(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));

        generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));

        generator.addProvider(event.includeClient(), new ModLanguageProvider(packOutput, Metallurgy.MODID, "en_us"));

        TConMaterialProvider materialDefinitionProvider = new TConMaterialProvider(packOutput);
        generator.addProvider(event.includeServer(), materialDefinitionProvider);
        generator.addProvider(event.includeServer(), new TConMaterialStatsProvider(packOutput, materialDefinitionProvider));

        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
    }
}