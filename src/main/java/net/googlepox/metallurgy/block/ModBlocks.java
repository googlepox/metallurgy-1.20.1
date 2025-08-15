package net.googlepox.metallurgy.block;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.block.custom.CrusherBlock;
import net.googlepox.metallurgy.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Metallurgy.MODID);

    public static final RegistryObject<Block> BITUMEN_BLOCK = registerBlock("bitumen_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK)));

    public static final RegistryObject<Block> IRON_BRICKS = registerBlock("iron_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> GOLD_BRICKS = registerBlock("gold_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));

    public static final RegistryObject<Block> COPPER_BRICKS = registerBlock("copper_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));

    public static final RegistryObject<Block> PHOSPHORITE_ORE = registerBlock("phosphorite_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK)));

    public static final RegistryObject<Block> PHOSPHORITE_ORE_DEEPSLATE = registerBlock("phosphorite_ore_deepslate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK)));

    public static final RegistryObject<Block> PHOSPHORITE_ORE_NETHER = registerBlock("phosphorite_ore_nether",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK)));

    public static final RegistryObject<Block> PHOSPHORITE_ORE_END_STONE = registerBlock("phosphorite_ore_end_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK)));

    public static final RegistryObject<Block> TAR_ORE = registerBlock("tar_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CLAY)));

    public static final RegistryObject<Block> TAR_ORE_DEEPSLATE = registerBlock("tar_ore_deepslate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CLAY)));

    public static final RegistryObject<Block> TAR_ORE_NETHER = registerBlock("tar_ore_nether",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CLAY)));

    public static final RegistryObject<Block> TAR_ORE_END_STONE = registerBlock("tar_ore_end_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CLAY)));

    public static final RegistryObject<Block> POTASH_ORE = registerBlock("potash_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CLAY)));

    public static final RegistryObject<Block> POTASH_ORE_DEEPSLATE = registerBlock("potash_ore_deepslate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CLAY)));

    public static final RegistryObject<Block> POTASH_ORE_NETHER = registerBlock("potash_ore_nether",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CLAY)));

    public static final RegistryObject<Block> POTASH_ORE_END_STONE = registerBlock("potash_ore_end_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CLAY)));

    public static final RegistryObject<Block> SULFUR_ORE = registerBlock("sulfur_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CLAY)));

    public static final RegistryObject<Block> SULFUR_ORE_DEEPSLATE = registerBlock("sulfur_ore_deepslate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CLAY)));

    public static final RegistryObject<Block> SULFUR_ORE_NETHER = registerBlock("sulfur_ore_nether",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CLAY)));

    public static final RegistryObject<Block> SULFUR_ORE_END_STONE = registerBlock("sulfur_ore_end_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CLAY)));

    public static final RegistryObject<Block> CRUSHER = registerBlock("crusher",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.FURNACE).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
