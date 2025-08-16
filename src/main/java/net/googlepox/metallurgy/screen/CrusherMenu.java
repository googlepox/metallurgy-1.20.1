package net.googlepox.metallurgy.screen;

import net.googlepox.metallurgy.block.ModBlocks;
import net.googlepox.metallurgy.block.entity.CrusherEntity;
import net.googlepox.metallurgy.util.ModTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class CrusherMenu extends AbstractContainerMenu {
    private final CrusherEntity crusherEntity;
    private final ContainerLevelAccess levelAccess;
    protected ContainerData data;

    // Client Constructor
    public CrusherMenu(int containerId, Inventory playerInv, FriendlyByteBuf additionalData) {
        this(containerId, playerInv, playerInv.player.level().getBlockEntity(additionalData.readBlockPos()), new SimpleContainerData(5));
    }

    // Server Constructor
    public CrusherMenu(int containerId, Inventory playerInv, BlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.CRUSHING_MENU.get(), containerId);
        if (blockEntity instanceof CrusherEntity be) {
            this.crusherEntity = be;
        } else {
            throw new IllegalStateException("Incorrect block entity class (%s) passed into ExampleMenu!"
                    .formatted(blockEntity.getClass().getCanonicalName()));
        }

        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());

        this.data = data;

        addDataSlots(data);

        createPlayerHotbar(playerInv);
        createPlayerInventory(playerInv);
        createBlockEntityInventory(be);
    }

    private void createBlockEntityInventory(CrusherEntity be) {
        be.getSideOptional().ifPresent(inventory ->
                addSlot(new SlotItemHandler(inventory, 0, 129, 48){
                    @Override
                    public boolean mayPlace(ItemStack pStack) {
                        return AbstractFurnaceBlockEntity.isFuel(pStack);
                    }
                }));

        be.getTopOptional().ifPresent(inventory ->
                addSlot(new SlotItemHandler(inventory, 0, 61, -4){
                    @Override
                    public boolean mayPlace(ItemStack pStack) {
                        return pStack.is(Tags.Items.INGOTS) || pStack.is(Tags.Items.RAW_MATERIALS);
                    }
                }));

        be.getBottomOptional().ifPresent(inventory -> {
                addSlot(new SlotItemHandler(inventory, 0, 67, 36));
                addSlot(new SlotItemHandler(inventory, 1, 48, 36));
                addSlot(new SlotItemHandler(inventory, 2, 29, 36));
        });
    }

    private void createPlayerInventory(Inventory playerInv) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInv,
                        9 + column + (row * 9),
                        8 + (column * 18),
                        84 + (row * 18)));
            }
        }
    }

    private void createPlayerHotbar(Inventory playerInv) {
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInv,
                    column,
                    8 + (column * 18),
                    142));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        Slot fromSlot = getSlot(pIndex);
        ItemStack fromStack = fromSlot.getItem();

        if (fromStack.getCount() <= 0)
            fromSlot.set(ItemStack.EMPTY);

        if (!fromSlot.hasItem())
            return ItemStack.EMPTY;

        ItemStack copyFromStack = fromStack.copy();

        if (pIndex < 36) {
            // We are inside of the player's inventory
            if (!moveItemStackTo(fromStack, 36, 39, false))
                return ItemStack.EMPTY;
        } else if (pIndex < 39) {
            // We are inside of the block entity inventory
            if (!moveItemStackTo(fromStack, 0, 36, false))
                return ItemStack.EMPTY;
        } else {
            System.err.println("Invalid slot index: " + pIndex);
            return ItemStack.EMPTY;
        }

        fromSlot.setChanged();
        fromSlot.onTake(pPlayer, fromStack);

        return copyFromStack;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(this.levelAccess, pPlayer, ModBlocks.CRUSHER.get());
    }

    public CrusherEntity getBlockEntity() {
        return this.crusherEntity;
    }
}
