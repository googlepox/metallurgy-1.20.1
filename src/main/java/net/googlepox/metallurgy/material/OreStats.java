package net.googlepox.metallurgy.material;

import net.googlepox.metallurgy.util.Constants;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

public class OreStats {

    private final boolean hasOre;
    private final int veinSize;
    private final int veinsPerChunk;
    private final int minY;
    private final int maxY;

    public OreStats(boolean hasOre, int veinSize, int veinsPerChunk, int minY, int maxY)
    {
        this.hasOre = hasOre;
        this.veinSize = veinSize;
        this.veinsPerChunk = veinsPerChunk;
        this.minY = minY;
        this.maxY = maxY;
    }

    public boolean getHasOre() {
        return hasOre;
    }

    public int getVeinSize() {
        return veinSize;
    }

    public int getVeinsPerChunk() {
        return veinsPerChunk;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

}
