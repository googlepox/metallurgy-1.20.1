package net.googlepox.metallurgy.datagen.integration.tconstruct;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.integration.tic.material.MetallurgyTiCStats;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;


public class TConMaterialProvider extends AbstractMaterialDataProvider {
    public TConMaterialProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void addMaterials() {
        Metallurgy.METALS.forEach((name, stats) -> {
            Metallurgy.logger.info("adding {} to materials", name);
            MaterialId material = new MaterialId(Metallurgy.MODID + ":" + name);
            MetallurgyTiCStats.MATERIALS.put(name, material);
            addMaterial(material, stats.getOreHarvest() - 2, 0, false);
        });
    }

    @Override
    public String getName() {
        return "Metallurgy Material Provider";
    }
}
