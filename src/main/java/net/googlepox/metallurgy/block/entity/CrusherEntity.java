package net.googlepox.metallurgy.block.entity;

import net.googlepox.metallurgy.Metallurgy;
import net.googlepox.metallurgy.item.ModItems;
import net.googlepox.metallurgy.recipe.CrusherRecipe;
import net.googlepox.metallurgy.screen.CrusherMenu;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import javax.annotation.Nonnull;
import java.util.Optional;

public class CrusherEntity extends BlockEntity implements MenuProvider {


    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT_0 = 0;
    private static final int OUTPUT_SLOT_1 = 1;
    private static final int OUTPUT_SLOT_2 = 2;
    private static final int FUEL_SLOT = 0;
    // One handler per direction
    private final ItemStackHandler topInventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }
    };      // top
    private final ItemStackHandler bottomInventory = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }
    }; ;    // bottom
    private final ItemStackHandler sideInventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }
    }; ;    // sides

    // LazyOptional wrappers (required by Forge)
    private final LazyOptional<ItemStackHandler> topOpt = LazyOptional.of(() -> topInventory);
    private final LazyOptional<ItemStackHandler> bottomOpt = LazyOptional.of(() -> bottomInventory);
    private final LazyOptional<ItemStackHandler> sideOpt = LazyOptional.of(() -> sideInventory);

    private String customName;

    private int burnTime;
    private int totalBurnTime;
    private int crushTime;
    public static final int TOTAL_CRUSHING_TIME = 140;

    private int fuelSpeedBoost;

    private int ambienceTick;

    public static boolean isItemFuel(ItemStack fuel)
    {
        return getBurnTime(fuel, CrusherRecipe.Type.INSTANCE) > 0;
    }

    public boolean isBurning()
    {
        return this.burnTime > 0;
    }

    private static int getBurnTime(ItemStack fuel, CrusherRecipe.Type instance) {
        return ForgeHooks.getBurnTime(fuel, instance);
    }

    public final ContainerData data;

    public CrusherEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CRUSHER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> CrusherEntity.this.burnTime;
                    case 1 -> CrusherEntity.this.totalBurnTime;
                    case 2 -> CrusherEntity.this.crushTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> CrusherEntity.this.burnTime = pValue;
                    case 1 -> CrusherEntity.this.totalBurnTime = pValue;
                    case 2 -> CrusherEntity.this.crushTime = pValue;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (side != null && cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == Direction.DOWN)
                return bottomOpt.cast();
            else if (side == Direction.UP)
                return topOpt.cast();
            else
                return sideOpt.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setChanged() {
        super.setChanged();

        if (this.level != null && this.level.isClientSide())
            this.level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    public LazyOptional<ItemStackHandler> getTopOptional() {
        return this.topOpt;
    }

    public LazyOptional<ItemStackHandler> getSideOptional() {
        return this.sideOpt;
    }

    public LazyOptional<ItemStackHandler> getBottomOptional() {
        return this.bottomOpt;
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.topOpt.invalidate();
        this.sideOpt.invalidate();
        this.bottomOpt.invalidate();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.metallurgy.crusher");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        return new CrusherMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("TopInventory", this.topInventory.serializeNBT());
        pTag.put("SideInventory", this.sideInventory.serializeNBT());
        pTag.put("BottomInventory", this.bottomInventory.serializeNBT());
        pTag.putShort("burn_time", (short) this.burnTime);
        pTag.putInt("total_burn_time", (short) this.totalBurnTime);
        pTag.putInt("crush_time", (short) this.crushTime);
        pTag.putInt("fuel_boost", fuelSpeedBoost);
        pTag.putInt("ambience_tick", ambienceTick);
        super.saveAdditional(pTag);
        Metallurgy.logger.info("saved inventory");
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        if(pTag.contains("TopInventory", Tag.TAG_COMPOUND)) {
            Metallurgy.logger.info("contains topinventory");
            this.topInventory.deserializeNBT(pTag.getCompound("TopInventory"));
        }

        if(pTag.contains("SideInventory", Tag.TAG_COMPOUND)) {
            this.sideInventory.deserializeNBT(pTag.getCompound("SideInventory"));
        }

        if(pTag.contains("BottomInventory", Tag.TAG_COMPOUND)) {
            this.bottomInventory.deserializeNBT(pTag.getCompound("BottomInventory"));
        }
        Metallurgy.logger.info("loaded inventory");
        this.burnTime = pTag.getInt("burn_time");
        this.crushTime = pTag.getInt("crush_time");
        this.totalBurnTime = pTag.getInt("total_burn_time");
        this.fuelSpeedBoost = pTag.getInt("fuel_boost");
        this.ambienceTick = pTag.getInt("ambience_tick");

    }

    public void drops() {
        SimpleContainer inventoryTop = new SimpleContainer(topInventory.getSlots());
        for(int i = 0; i < topInventory.getSlots(); i++) {
            inventoryTop.setItem(i, topInventory.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventoryTop);

        SimpleContainer inventoryBottom = new SimpleContainer(bottomInventory.getSlots());
        for(int i = 0; i < bottomInventory.getSlots(); i++) {
            inventoryBottom.setItem(i, bottomInventory.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventoryBottom);

        SimpleContainer inventorySide = new SimpleContainer(sideInventory.getSlots());
        for(int i = 0; i < sideInventory.getSlots(); i++) {
            inventorySide.setItem(i, sideInventory.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventorySide);
    }

    private Optional<CrusherRecipe> getCurrentRecipe() {
        SimpleContainer inventoryTop = new SimpleContainer(this.topInventory.getSlots());
        for(int i = 0; i < topInventory.getSlots(); i++) {
            inventoryTop.setItem(i, this.topInventory.getStackInSlot(i));
            Metallurgy.logger.info("item: " + inventoryTop.getItem(i));
        }
        return this.level.getRecipeManager().getRecipeFor(CrusherRecipe.Type.INSTANCE, inventoryTop, level);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        boolean oldBurningState = isBurning();

        if (isBurning())
        {
            this.burnTime--;

            if (canCrush())
            {
                // TODO
                //if (this.ambienceTick == 0)
                    //level.playSound(null, pPos, ModSounds.CRUSHER_WINDUP, SoundSource.BLOCKS, 1F, 1F);

                //Make machine tick faster if fuel is one of the enhanced ones
                if (fuelSpeedBoost > 0)
                    this.crushTime = Math.min(this.crushTime + fuelSpeedBoost, TOTAL_CRUSHING_TIME);
                else
                    this.crushTime++;

                //Handles ambience sound loop
                this.ambienceTick++;

                //After 6 seconds OR every 16.5 seconds from the offset start
                if (!level.isClientSide)
                {
                    if (ambienceTick == 120 || ambienceTick % 330 == 120)
                    {
                        // TODO
                        /*PacketDistributor.NEAR.with(PacketDistributor.NEAR.with(
                            () -> new PacketDistributor.TargetPoint(
                                    pPos.getX(),
                                    pPos.getY(),
                                    pPos.getZ(),
                                    64.0, // radius
                                    level.dimension()
                            )
                                ),
                                new PacketStartStopAmbienceSound(ModSounds.CRUSHER_AMBIENCE, SoundSource.BLOCKS, pPos)
                        ); */
                    }
                }

                if (crushTime >= TOTAL_CRUSHING_TIME)
                {
                    crushItem();
                    this.crushTime = 0;
                    setChanged();
                }
            }
            else if (!level.isClientSide && crushTime == 0 && ambienceTick != 0)
            {
                ambienceTick = 0;

                // TODO
                /*PacketDistributor.NEAR.with(PacketDistributor.NEAR.with(
                                () -> new PacketDistributor.TargetPoint(
                                        pPos.getX(),
                                        pPos.getY(),
                                        pPos.getZ(),
                                        64.0, // radius
                                        level.dimension()
                                )
                        ),
                        new PacketStartStopAmbienceSound(pPos)
                ); */
            }
            else if (crushTime > 0 && getCurrentRecipe().isEmpty()) {
                this.crushTime = 0;
            }
        }
        else
        {
            ItemStack fuelStack = sideInventory.getStackInSlot(0);
            Item fuelItem = fuelStack.getItem();

            if (!fuelStack.isEmpty() && canCrush())
            {
                if (fuelItem == ModItems.THERMITE_DUST.get())
                    this.fuelSpeedBoost = 2;
                else if (fuelItem == ModItems.IGNATIUS_FUEL.get())
                    this.fuelSpeedBoost = 3;
                else
                    this.fuelSpeedBoost = 1;

                //200 = total furnace cook time
                //machineTime = furnaceTime*140/200
                this.burnTime = getBurnTime(fuelStack, CrusherRecipe.Type.INSTANCE) * TOTAL_CRUSHING_TIME / 200;
                this.totalBurnTime = this.burnTime;
                fuelStack.shrink(1);

                //In case the fuel has a container item set it in the fuel slot after shrinking the fuel (i.e. lava bucket)
                if (fuelStack.isEmpty())
                {
                    ItemStack containerItem = fuelItem.getCraftingRemainingItem(fuelStack);
                    sideInventory.setStackInSlot(0, containerItem);
                }

                setChanged();
            }

            if (this.crushTime > 0)
            {
                //if crushing time is negative then crushing time should be zero, bitch
                this.crushTime = Math.max(this.crushTime - 2, 0);
            }
        }

        if (oldBurningState != isBurning())
        {
            //BlockCrusher.setState(isBurning(), this.world, this.pos);
        }
    }

    private void crushItem()
    {
        if (this.canCrush())
        {
            Optional<CrusherRecipe> recipe = getCurrentRecipe();

            ItemStack input = this.topInventory.getStackInSlot(INPUT_SLOT);
            ItemStack recipeResult = recipe.get().getResultItem(null);
            ItemStack output = this.bottomInventory.getStackInSlot(OUTPUT_SLOT_0);
            ItemStack output1 = this.bottomInventory.getStackInSlot(OUTPUT_SLOT_1);
            ItemStack output2 = this.bottomInventory.getStackInSlot(OUTPUT_SLOT_2);

            int limit = output.getCount() + recipeResult.getCount();
            int limit1 = output1.getCount() + recipeResult.getCount();
            int limit2 = output2.getCount() + recipeResult.getCount();

            if (output.isEmpty())
                this.bottomInventory.setStackInSlot(OUTPUT_SLOT_0, recipeResult.copy());
            else if (ItemStack.isSameItemSameTags(output, recipeResult) && limit <= this.getInventoryStackLimit() && limit <= output.getMaxStackSize())
                output.grow(recipeResult.getCount());

            else if (output1.isEmpty())
                this.bottomInventory.setStackInSlot(OUTPUT_SLOT_1, recipeResult.copy());
            else if (ItemStack.isSameItemSameTags(output1, recipeResult) && limit1 <= this.getInventoryStackLimit() && limit1 <= output1.getMaxStackSize())
                output1.grow(recipeResult.getCount());

            else if (output2.isEmpty())
                this.bottomInventory.setStackInSlot(OUTPUT_SLOT_2, recipeResult.copy());
            else if (ItemStack.isSameItemSameTags(output2, recipeResult) && limit2 <= this.getInventoryStackLimit() && limit2 <= output2.getMaxStackSize())
                output2.grow(recipeResult.getCount());

            input.shrink(1);
            //world.playSound(null, pos, ModSounds.CRUSHER_IMPACT, SoundCategory.BLOCKS, 1F, 1F);
        }
    }

    private boolean canCrush()
    {

        Optional<CrusherRecipe> recipe = getCurrentRecipe();
        Metallurgy.logger.info("test1");
        ItemStack input = this.topInventory.getStackInSlot(INPUT_SLOT);
        Metallurgy.logger.info("test2");
        if (input.isEmpty() || recipe.isEmpty())
            return false;
        else
        {
            Metallurgy.logger.info("test3");
            ItemStack result = recipe.get().getResultItem(null);
            Metallurgy.logger.info("test4");
            if (result.isEmpty())
                return false;
            else
            {
                Metallurgy.logger.info("test5");
                ItemStack output = this.bottomInventory.getStackInSlot(OUTPUT_SLOT_0);
                ItemStack output1 = this.bottomInventory.getStackInSlot(OUTPUT_SLOT_1);
                ItemStack output2 = this.bottomInventory.getStackInSlot(OUTPUT_SLOT_2);
                Metallurgy.logger.info("test6");
                int totalAmount = output.getCount() + result.getCount();
                int totalAmount1 = output1.getCount() + result.getCount();
                int totalAmount2 = output2.getCount() + result.getCount();
                Metallurgy.logger.info("test7");
                //can crush if one of the various outputs is empty
                if (output.isEmpty() || output1.isEmpty() || output2.isEmpty())
                    return true;
                Metallurgy.logger.info("test8");
                //Can't crush if all of the output slots are filled with an item that is different from the recipe result
                if (!ItemStack.isSameItem(output, result) && !ItemStack.isSameItem(output1, result) && !ItemStack.isSameItem(output2, result))
                    return false;
                    //if the previous condition is not met check whether the sum of the recipe result count and the slot amount doesn't overcome the stack limit
                else if (totalAmount <= this.getInventoryStackLimit() && totalAmount <= output.getMaxStackSize())
                    return true;
                else if (totalAmount1 <= this.getInventoryStackLimit() && totalAmount1 <= output1.getMaxStackSize())
                    return true;
                else
                    return (totalAmount2 <= this.getInventoryStackLimit() && totalAmount2 <= output2.getMaxStackSize());

            }
        }
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }
}
