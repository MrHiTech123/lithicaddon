package com.redstoneguy10ls.lithicaddon.client;

import com.redstoneguy10ls.lithicaddon.common.blockentities.mothBlockEntity;
import com.redstoneguy10ls.lithicaddon.common.container.mothboxContainer;
import net.dries007.tfc.client.screen.BlockEntityScreen;
import net.dries007.tfc.client.screen.TFCContainerScreen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class mothBoxScreen extends BlockEntityScreen<mothBlockEntity, mothboxContainer> {

    public mothBoxScreen(mothboxContainer container, Inventory playerInventory, Component name)
    {
        super(container, playerInventory, name, new ResourceLocation("lithicaddon","textures/gui/moth_box.png"));
        inventoryLabelY += 20;
        imageHeight += 20;
    }


}
