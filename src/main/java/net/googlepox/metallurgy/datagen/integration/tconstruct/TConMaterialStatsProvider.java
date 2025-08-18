package net.googlepox.metallurgy.datagen.integration.tconstruct;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.integration.tic.material.MetallurgyTiCStats;
import net.googlepox.metallurgy.material.MetalStats;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;
import slimeknights.tconstruct.tools.stats.PlatingMaterialStats;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

import java.util.List;

public class TConMaterialStatsProvider extends AbstractMaterialStatsDataProvider {

    public TConMaterialStatsProvider(PackOutput packOutput, AbstractMaterialDataProvider materials) {
        super(packOutput, materials);
    }

    @Override
    protected void addMaterialStats() {
        Metallurgy.METALS.forEach((name, stats) -> {
            Metallurgy.logger.info("adding {} stats", name);
            addMeleeHarvest(name, stats);
            addRanged(name, stats);
            addArmor(name, stats);
            //addMisc(name, stats);
        });
    }

    private void addMeleeHarvest(String name, MetalStats stats) {
        addMaterialStats(
                MetallurgyTiCStats.MATERIALS.get(name),
                MetallurgyTiCStats.getHeadA(stats),
                MetallurgyTiCStats.getHandleA(stats),
                StatlessMaterialStats.BINDING
        );
    }

    private void addRanged(String name, MetalStats stats) {
        addMaterialStats(
                MetallurgyTiCStats.MATERIALS.get(name),
                MetallurgyTiCStats.getLimbA(stats),
                MetallurgyTiCStats.getGripA(stats)
        );
    }

    private void addArmor(String name, MetalStats stats) {
        if (stats.getArmorStats() != null) {
            addArmorShieldStats(
                    MetallurgyTiCStats.MATERIALS.get(name),
                    PlatingMaterialStats.builder()
                            .durabilityFactor(stats.getArmorStats().getDurability())
                            .armor(stats.getArmorStats().getDefenseForType(ArmorItem.Type.BOOTS),
                                    stats.getArmorStats().getDefenseForType(ArmorItem.Type.LEGGINGS),
                                    stats.getArmorStats().getDefenseForType(ArmorItem.Type.CHESTPLATE),
                                    stats.getArmorStats().getDefenseForType(ArmorItem.Type.HELMET))
                            .toughness(stats.getArmorStats().getToughness())
                            .knockbackResistance(stats.getArmorStats().getKnockbackResistance()),
                    StatlessMaterialStats.MAILLE
            );
        }
    }

    @Override
    public String getName() {
        return "Metallurgy Material Stats Data Provider";
    }
}
