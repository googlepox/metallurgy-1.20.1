package net.googlepox.metallurgy.block.entity;

import net.googlepox.metallurgy.item.ModItems;
import net.googlepox.metallurgy.recipe.CrusherRecipe;
import net.googlepox.metallurgy.screen.CrusherMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.Arrays;
import java.util.Optional;

public class CrusherEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(2);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT_0 = 2;
    private static final int OUTPUT_SLOT_1 = 3;
    private static final int OUTPUT_SLOT_2 = 4;
    private static final int FUEL_SLOT = 1;
    private final IItemHandler handlerTop = new SidedInvWrapper((WorldlyContainer) this, Direction.UP);
    private final IItemHandler handlerBottom = new SidedInvWrapper((WorldlyContainer) this, Direction.DOWN);
    private final IItemHandler handlerSide = new SidedInvWrapper((WorldlyContainer) this, Direction.WEST);

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


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

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

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (side != null && cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == Direction.DOWN)
                return (LazyOptional<T>) handlerBottom;
            else if (side == Direction.UP)
                return (LazyOptional<T>) handlerTop;
            else
                return (LazyOptional<T>) handlerSide;
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.metallurgy.crusher");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CrusherMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {

        pTag.putShort("burn_time", (short) this.burnTime);
        pTag.putInt("total_burn_time", (short) this.totalBurnTime);
        pTag.putInt("crush_time", (short) this.crushTime);

        pTag.putInt("fuel_boost", fuelSpeedBoost);

        pTag.putInt("ambience_tick", ambienceTick);


        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        this.burnTime = pTag.getInt("burn_time");
        this.crushTime = pTag.getInt("crush_time");
        this.totalBurnTime = pTag.getInt("total_burn_time");

        this.fuelSpeedBoost = pTag.getInt("fuel_boost");

        this.ambienceTick = pTag.getInt("ambience_tick");

    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    private Optional<CrusherRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(CrusherRecipe.Type.INSTANCE, inventory, level);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        boolean oldBurningState = isBurning();

        if (isBurning())
        {
            this.burnTime--;

            if (canCrush())
            {
                if (this.ambienceTick == 0)
                    //pLevel.playSound(null, pPos, ModSounds.CRUSHER_WINDUP, SoundSource.BLOCKS, 1F, 1F);

                //Make machine tick faster if fuel is one of the enhanced ones
                if (fuelSpeedBoost > 0)
                    this.crushTime = Math.min(this.crushTime + fuelSpeedBoost, TOTAL_CRUSHING_TIME);
                else
                    this.crushTime++;

                //Handles ambience sound loop
                this.ambienceTick++;

                setChanged(pLevel, pPos, pState);

                if (crushTime >= TOTAL_CRUSHING_TIME)
                {
                    crushItem();
                    this.crushTime = 0;
                    //markDirty();
                }
            }

            else if (crushTime == 0 && ambienceTick != 0)
            {
                ambienceTick = 0;
                //Stop
                PacketDistributor.NEAR.with( () ->
                    new PacketDistributor.TargetPoint(
                            pPos.getX(),
                            pPos.getY(),
                            pPos.getZ(),
                            64.0, // radius
                            level.dimension()
                    )
                        //new PacketStartStopAmbienceSound(pos)
                );
            }
        }
        else
        {
            ItemStack fuelStack = itemHandler.getStackInSlot(FUEL_SLOT);
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
                int correctFuel = getBurnTime(fuelStack, CrusherRecipe.Type.INSTANCE) * TOTAL_CRUSHING_TIME / 200;
                this.totalBurnTime = this.burnTime = correctFuel;
                fuelStack.shrink(1);

                //In case the fuel has a container item set it in the fuel slot after shrinking the fuel (i.e. lava bucket)
                if (fuelStack.isEmpty())
                {
                    ItemStack containerItem = fuelItem.getCraftingRemainingItem(fuelStack);
                    itemHandler.setStackInSlot(FUEL_SLOT, containerItem);
                }

                //markDirty();
            }

            if (this.crushTime > 0)
            {
                //if crushing time is negative then crushing time should be zero, bitch
                this.crushTime = Math.max(this.crushTime - 2, 0);
            }
        }

        if (oldBurningState != isBurning())
        {
            //BlockState current = level.getBlockState(pPos);
            //BlockState updated = current.setValue(this.isBurning(), isBurning());
        }
    }

    private void crushItem()
    {
        if (this.canCrush())
        {
            Optional<CrusherRecipe> recipe = getCurrentRecipe();

            ItemStack input = this.itemHandler.getStackInSlot(INPUT_SLOT);
            ItemStack recipeResult = recipe.get().getResultItem(null);
            ItemStack output = this.itemHandler.getStackInSlot(OUTPUT_SLOT_0);
            ItemStack output1 = this.itemHandler.getStackInSlot(OUTPUT_SLOT_1);
            ItemStack output2 = this.itemHandler.getStackInSlot(OUTPUT_SLOT_2);

            int limit = output.getCount() + recipeResult.getCount();
            int limit1 = output1.getCount() + recipeResult.getCount();
            int limit2 = output2.getCount() + recipeResult.getCount();

            if (output.isEmpty())
                this.itemHandler.setStackInSlot(OUTPUT_SLOT_0, recipeResult.copy());
            else if (ItemStack.isSameItemSameTags(output, recipeResult) && limit <= this.getInventoryStackLimit() && limit <= output.getMaxStackSize())
                output.grow(recipeResult.getCount());

            else if (output1.isEmpty())
                this.itemHandler.setStackInSlot(OUTPUT_SLOT_1, recipeResult.copy());
            else if (ItemStack.isSameItemSameTags(output1, recipeResult) && limit1 <= this.getInventoryStackLimit() && limit1 <= output1.getMaxStackSize())
                output1.grow(recipeResult.getCount());

            else if (output2.isEmpty())
                this.itemHandler.setStackInSlot(OUTPUT_SLOT_2, recipeResult.copy());
            else if (ItemStack.isSameItemSameTags(output2, recipeResult) && limit2 <= this.getInventoryStackLimit() && limit2 <= output2.getMaxStackSize())
                output2.grow(recipeResult.getCount());

            input.shrink(1);
            //world.playSound(null, pos, ModSounds.CRUSHER_IMPACT, SoundCategory.BLOCKS, 1F, 1F);
        }
    }

    private boolean canCrush()
    {
        Optional<CrusherRecipe> recipe = getCurrentRecipe();

        ItemStack input = this.itemHandler.extractItem(INPUT_SLOT, 1, false);

        if (input.isEmpty())
            return false;
        else
        {
            ItemStack result = recipe.get().getResultItem(null);
            if (result.isEmpty())
                return false;
            else
            {
                ItemStack output = this.itemHandler.getStackInSlot(OUTPUT_SLOT_0);
                ItemStack output1 = this.itemHandler.getStackInSlot(OUTPUT_SLOT_1);
                ItemStack output2 = this.itemHandler.getStackInSlot(OUTPUT_SLOT_2);

                int totalAmount = output.getCount() + result.getCount();
                int totalAmount1 = output1.getCount() + result.getCount();
                int totalAmount2 = output2.getCount() + result.getCount();

                //can crush if one of the various outputs is empty
                if (output.isEmpty() || output1.isEmpty() || output2.isEmpty())
                    return true;

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
