package net.googlepox.metallurgy.datagen.integration.tconstruct;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.integration.tic.material.MetallurgyTiCStats;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;

public class TConMaterialRenderInfoProvider extends AbstractMaterialRenderInfoProvider {
    public TConMaterialRenderInfoProvider(PackOutput packOutput, @Nullable AbstractMaterialSpriteProvider materialSprites, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, materialSprites, existingFileHelper);
    }

    @Override
    protected void addMaterialRenderInfo() {
        MetallurgyTiCStats.MATERIALS.forEach((name, stats) -> {
            buildRenderInfo(stats).color(Metallurgy.METALS.get(name).getColorHex()).fallbacks("metal");
        });
    }

    @Override
    public String getName() {
        return "Metallurgy Material Render Info Provider";
    }
}
