package net.googlepox.metallurgy.datagen;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.core.MetalRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import slimeknights.mantle.fluid.UnplaceableFluid;
import slimeknights.mantle.fluid.texture.AbstractFluidTextureProvider;
import slimeknights.mantle.fluid.texture.FluidTexture;
import slimeknights.mantle.registration.object.FluidObject;

public class ModFluidTextureProvider extends AbstractFluidTextureProvider {

    private static final int MOLTEN_LENGTH = "molten_".length();

    public ModFluidTextureProvider(PackOutput packOutput) {
        super(packOutput, Metallurgy.MODID);
    }

    @Override
    public String getName() {
        return "Metallurgy Fluid textures";
    }

    @Override
    public void addTextures() {
        MetalRegistry.METAL_FLUIDS.forEach((name, fluid) -> {
            molten(fluid);
        });
    }

    private FluidTexture.Builder fuels(FluidObject<?> fluid) {
        return this.texture(fluid.getType()).textures(
                new ResourceLocation(Metallurgy.MODID, "fluid/rf_furnace_fuels/"),
                false,
                false
        );
    }

    private FluidTexture.Builder named(FluidObject<?> fluid, String name) {
        return texture(fluid).textures(MetalRegistry.METAL_RL, false, false).color(Metallurgy.METALS.get(withoutMolten(fluid)).getColorHex());
    }

    private FluidTexture.Builder molten(FluidObject<?> fluid) {
        return named(fluid, "molten/" + withoutMolten(fluid));
    }

    public static String withoutMolten(FluidObject<?> fluid) {
        return fluid.getId().getPath().substring(MOLTEN_LENGTH);
    }
}
