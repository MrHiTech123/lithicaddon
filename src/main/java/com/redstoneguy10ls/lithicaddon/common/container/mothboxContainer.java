package com.redstoneguy10ls.lithicaddon.common.container;

import com.redstoneguy10ls.lithicaddon.common.blockentities.mothBlockEntity;
import net.minecraft.world.entity.player.Inventory;

public class mothboxContainer extends Container<mothBlockEntity>{

    public static mothboxContainer create(mothBlockEntity box, Inventory playerInventory, int windowId)
    {
        return new mothboxContainer(box, playerInventory, windowId).init(playerInventory, 20);
    }
    public mothboxContainer(mothBlockEntity blockEntity, Inventory playerInv, int windowId)
    {
        super(lithicContainerTypes.MOTHBOX.get(), windowId, blockEntity);
    }

}
