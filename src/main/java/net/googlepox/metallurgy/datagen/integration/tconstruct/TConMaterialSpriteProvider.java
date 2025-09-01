package net.googlepox.metallurgy.datagen.integration.tconstruct;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.integration.tic.material.MetallurgyTiCStats;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

import java.util.Collection;

public class TConMaterialSpriteProvider extends AbstractMaterialSpriteProvider {
    @Override
    public String getName() {
        return "Metallurgy Material Sprite Provider";
    }

    @Override
    protected void addAllMaterials() {

    }
}
