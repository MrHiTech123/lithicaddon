package com.redstoneguy10ls.lithicaddon.common.container;

import net.dries007.tfc.common.blockentities.InventoryBlockEntity;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.common.container.CallbackSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class Container<T extends InventoryBlockEntity<?>> extends BlockEntityContainer<T> {

    public Container(MenuType<?> containerType, int windowId, T blockEntity)
    {
        super(containerType, windowId, blockEntity);
    }

    @Override
    protected boolean moveStack(ItemStack stack,  int slotIndex)
    {
        return switch (typeOf(slotIndex))
        {
            case MAIN_INVENTORY, HOTBAR -> !moveItemStackTo(stack, 0, 5, false);
            case CONTAINER -> !moveItemStackTo(stack, containerSlots, slots.size(), false);
        };
    }

    @Override
    protected void addContainerSlots()
    {
        blockEntity.getCapability(Capabilities.ITEM).ifPresent(handler -> {
            addSlot(new CallbackSlot(blockEntity, handler, 0, 80, 14));
            addSlot(new CallbackSlot(blockEntity, handler, 1, 72, 50));
            addSlot(new CallbackSlot(blockEntity, handler, 2, 90, 50));
            addSlot(new CallbackSlot(blockEntity, handler, 3, 72, 68));
            addSlot(new CallbackSlot(blockEntity, handler, 4, 90, 68));
        });
    }
}
