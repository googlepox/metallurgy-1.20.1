package net.googlepox.metallurgy.material;

import net.googlepox.metallurgy.core.MetalRegistry;
import net.googlepox.metallurgy.util.Constants;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

public class MetalStats {

    private final String name;
    private final float strength;
    private final float blockBlastResistance;
    private final int oreHarvest;
    private final Block material;
    private final int color;
    private final int temperature;
    private final ArmorStats armor;
    private final ToolStats tool;
    private final OreStats ore;

    public MetalStats(String name, float strength, float blockBlastResistance, int oreHarvest, Block material, ArmorStats armor, ToolStats tool, OreStats ore, int color, int temperature)
    {
        this.name = name;
        this.strength = strength;
        this.blockBlastResistance = blockBlastResistance;
        this.oreHarvest = oreHarvest;
        this.material = material;
        this.color = color;
        this.temperature = temperature;
        this.armor = armor;
        this.tool = tool;
        this.ore = ore;
    }

    private int automaticTemperature()
    {
        int tier = oreHarvest;

        if (tier == -1)
            tier = Constants.TIER_MAP.get(name);

        return 1000 + tier * 200;
    }

    public String getName()
    {
        return name;
    }

    public float getStrength()
    {
        return strength;
    }

    /**
     * @return the harvest level of the ore related to the block | -1 if the metal is an alloy
     */
    public int getOreHarvest()
    {
        return oreHarvest;
    }

    public float getBlockBlastResistance()
    {
        return blockBlastResistance;
    }

    @Nullable
    public ArmorStats getArmorStats()
    {
        return armor;
    }

    @Nullable
    public ToolStats getToolStats()
    {
        return tool;
    }

    public int getColorHex()
    {
        return color;
    }

    public long getColorIntWithAlpha()
    {
        String colorWoAlpha = Integer.toHexString(color);
        String colorWAlpha = "0xff" + colorWoAlpha;
        return Long.decode(colorWAlpha);
    }

    public float[] getColorRGBValues()
    {
        float[] rgb = new float[3];
        //Shift (8 * rgb_index bits) to the right, and take the right-most byte via bit-wise AND
        //Divide 0..255 range by 255F to get a float 0..1 and add it to the right place in the rgb array
        rgb[0] = (color >> 16 & 0xFF) / 255F;
        rgb[1] = (color >> 8 & 0xFF) / 255F;
        rgb[2] = (color & 0xFF) / 255F;
        return rgb;
    }

    public Block getMaterial() {
        return material;
    }

    public int getTemperature()
    {
        return 1000;
        //return automaticTemperature();
    }

    public OreStats getOreStats() {
        return ore;
    }

    public Item getOre() {
        return MetalRegistry.RAW_ITEMS.get(this.name).get();
    }

    public Item getDust() {
        return MetalRegistry.DUSTS.get(this.name).get();
    }

    public Item getIngot() {
        return MetalRegistry.INGOTS.get(this.name).get();
    }
}