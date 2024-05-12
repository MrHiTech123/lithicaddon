package com.redstoneguy10ls.lithicaddon.common.blockentities;

import com.redstoneguy10ls.lithicaddon.common.blocks.mothboxBlock;
import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.IMoth;
import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.MothCapability;
import com.redstoneguy10ls.lithicaddon.common.container.mothboxContainer;
import com.redstoneguy10ls.lithicaddon.util.lithicTags;
import net.dries007.tfc.common.blockentities.TickableInventoryBlockEntity;
import net.dries007.tfc.common.capabilities.PartialItemHandler;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.ICalendarTickable;
import net.dries007.tfc.util.climate.Climate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import static com.redstoneguy10ls.lithicaddon.LithicAddon.MOD_ID;

public class mothBlockEntity extends TickableInventoryBlockEntity<ItemStackHandler> implements ICalendarTickable {


    public static void serverTick(Level level, BlockPos pos, BlockState state, mothBlockEntity box)
    {
        box.checkForLastTickSync();
        box.checkForCalendarUpdate();

        if (level.getGameTime() % 60 == 0)
        {
            box.updateState();
        }
    }

    public static final int MIN_LIGHT_LEVEL = 11;

    public static final int MIN_LIGHTS = 10;

    public static final float MIN_TEMP = -19;

    public static final float MAX_TEMP = 26;

    public static final float MIN_RAIN = 10;

    public static final float MAX_RAIN = 500;

    public static final int UPDATE_INTERVAL = ICalendar.TICKS_IN_DAY;
    public static final int SLOTS = 5;

    private static final Component NAME = Component.translatable(MOD_ID + ".block_entity.mothbox");

    private final IMoth[] cachedMoths;

    private long lastPlayerTick, lastAreaTick;

    private int leaves;

    public mothBlockEntity(BlockPos pos, BlockState state) {
        super(lithicBlockEntities.MOTHBOX.get(), pos, state, defaultInventory(SLOTS), NAME);
        lastPlayerTick = Integer.MIN_VALUE;
        lastAreaTick = Calendars.SERVER.getTicks();
        cachedMoths = new IMoth[] {null,null,null,null,null};
        leaves = 0;

        sidedInventory
                .on(new PartialItemHandler(inventory).insert(0, 1, 2, 3), Direction.Plane.HORIZONTAL)
                .on(new PartialItemHandler(inventory).extract(0, 1, 2, 3), Direction.DOWN);
    }

    @Override
    public void saveAdditional(CompoundTag nbt)
    {
        super.saveAdditional(nbt);
        nbt.putLong("lastTick", lastPlayerTick);
        nbt.putLong("lastAreaTick", lastAreaTick);
        nbt.putInt("leaves", leaves);
    }

    @Override
    public void loadAdditional(CompoundTag nbt)
    {
        super.loadAdditional(nbt);
        updateCache();
        lastPlayerTick = nbt.getLong("lastTick");
        lastAreaTick = nbt.getLong("lastAreaTick");
        leaves = nbt.getInt("leaves");

    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int windowID, Inventory inv, Player player) {
        return mothboxContainer.create(this, inv, windowID);
    }

    @Override
    public void onCalendarUpdate(long ticks) {tryPeriodicUpdate();    }

    public void tryPeriodicUpdate()
    {
        long now = Calendars.SERVER.getTicks();
        if (now > (lastAreaTick + UPDATE_INTERVAL))
        {
            while (lastAreaTick < now)
            {
                updateTick();
                lastAreaTick += UPDATE_INTERVAL;
            }
            markForSync();
        }
    }
    public boolean isSlotEmpty(int slot)
    {
        return inventory.getStackInSlot(slot).isEmpty();
    }

    @Override
    public void setAndUpdateSlots(int slot)
    {
        super.setAndUpdateSlots(slot);
        updateCache();
    }

    private void updateCache()
    {
        for(int i = 1; i < SLOTS; i++)
        {
            cachedMoths[i] = getMoth(i);
        }
    }

    public IMoth[] getCachedMoths()
    {
        if (level != null && level.isClientSide) updateCache();
        return cachedMoths;
    }

    private void updateTick()
    {
        assert level != null;
        final float temp = Climate.getTemperature(level, worldPosition);
        final float rainfall = Climate.getRainfall(level, worldPosition);



        setLeaves(calculateLeaves());

        final int light = getLight();
        final int breedTickChanceInverted = getBreedTickChanceInverted(light);

        if(temp <= MAX_TEMP && temp >= MIN_TEMP && rainfall <= MAX_RAIN && rainfall >= MIN_RAIN) {
            if (light > MIN_LIGHTS && (breedTickChanceInverted == 0 || level.random.nextInt(breedTickChanceInverted) == 0) && leaves > 0) {
                IMoth uninitializedMoth = null;

                for (int i = 1; i < SLOTS; i++) {

                    final IMoth moth = inventory.getStackInSlot(i).getCapability(MothCapability.CAPABILITY).resolve().orElse(null);

                    if (moth != null) {
                        if (moth.hasLarva()) {
                            if((moth.getDaysTillCocoon() > moth.daysAlive())) {
                                removeLeaves();
                            }
                            moth.setDaysAlive(moth.daysAlive() + 1);
                        } else if (uninitializedMoth == null) {
                            uninitializedMoth = moth;
                        }
                    }
                }
                if (uninitializedMoth != null) {
                    uninitializedMoth.initLarva();
                }
            }
        }
    }

    public int getLight()
    {
        assert level != null;
        int lights = 0;
        final BlockPos min = worldPosition.offset(-5, -5, -5);
        final BlockPos max = worldPosition.offset(5, 5, 5);
        if(level.hasChunksAt(min, max))
        {
            for (BlockPos pos : BlockPos.betweenClosed(min,max))
            {
                final BlockState state = level.getBlockState(pos);

                if(level.getBrightness(LightLayer.BLOCK, pos) >= MIN_LIGHT_LEVEL){
                    lights++;

                }
            }

        }
        return lights;
    }

    public int getDimLight()
    {
        assert level != null;
        int dimlights = 0;
        final BlockPos min = worldPosition.offset(-5, -5, -5);
        final BlockPos max = worldPosition.offset(5, 5, 5);
        if(level.hasChunksAt(min, max))
        {
            for (BlockPos pos : BlockPos.betweenClosed(min,max))
            {
                final BlockState state = level.getBlockState(pos);
                if(level.getBrightness(LightLayer.BLOCK, pos) < MIN_LIGHT_LEVEL){
                    dimlights++;
                }
            }

        }
        return dimlights;
    }

    public void setLeaves(int value)
    {
        leaves = value;
    }

    public void removeLeaves()
    {
        inventory.getStackInSlot(0).shrink(1);
        setLeaves(calculateLeaves());
    }

    public int calculateLeaves()
    {
        return inventory.getStackInSlot(0).getCount();
    }

    public int getLeaves()
    {
        calculateLeaves();
        return leaves;}

    public int getBreedTickChanceInverted(int lights)
    {
        int chance = 60;

        return Math.max(0, chance - Math.min(lights, 60));
    }

    public void updateState()
    {
        assert level != null;
        final boolean moths = hasMoths();
        final BlockState state = level.getBlockState(worldPosition);
        boolean hasLeaves = leaves > 0;
        if(hasLeaves != state.getValue(mothboxBlock.LARVA))
        {
            level.setBlockAndUpdate(worldPosition, state.setValue(mothboxBlock.LARVA, hasLeaves));
            markForSync();
        }
    }

    private boolean hasMoths()
    {
        for (int i = 1; i < SLOTS; i++)
        {
            if (cachedMoths[i] != null && cachedMoths[i].hasLarva())
            {
                return true;
            }
        }
        return false;
    }

    @Nullable
    private IMoth getMoth(int slot)
    {
        final ItemStack stack = inventory.getStackInSlot(slot);
        if(!stack.isEmpty())
        {
            var opt = stack.getCapability(MothCapability.CAPABILITY).resolve();
            if(opt.isPresent())
            {
                return opt.get();
            }
        }
        return null;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack)
    {
        if(slot != 0)
        {
            return stack.getCapability(MothCapability.CAPABILITY).isPresent();
        }
        else
        {
            return Helpers.isItem(stack, lithicTags.Items.MOTH_FOOD);
        }
    }

    @Override
    public long getLastCalendarUpdateTick() {
        return 0;
    }

    @Override
    public void setLastCalendarUpdateTick(long l) {

    }
}
