package com.redstoneguy10ls.lithicaddon.client;

import com.redstoneguy10ls.lithicaddon.LithicAddon;
import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.IMoth;
import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.MothCapability;
import com.redstoneguy10ls.lithicaddon.common.container.lithicContainerTypes;
import com.redstoneguy10ls.lithicaddon.common.items.lithicItems;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.model.DynamicFluidContainerModel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;

import static com.redstoneguy10ls.lithicaddon.LithicAddon.MOD_ID;

public class lithicClientEventHandler {

    public static void init()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        final IEventBus bus2 = MinecraftForge.EVENT_BUS;

        bus.addListener(lithicClientEventHandler::clientSetup);
        bus2.addListener(lithicClientEventHandler::onTooltip);
        bus.addListener(lithicClientEventHandler::registerColorHandlerItems);
    }

    public static void clientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            MenuScreens.register(lithicContainerTypes.MOTHBOX.get(), mothBoxScreen::new);

            ItemProperties.register(lithicItems.LARVA_LATTICE.get(), new ResourceLocation(MOD_ID, "larva"),
                    (stack, a, b, c) -> stack.getCapability(MothCapability.CAPABILITY).map(IMoth::hasLarva).orElse(false) ? 1f: 0f);
            ItemProperties.register(lithicItems.LARVA_LATTICE.get(), new ResourceLocation(MOD_ID, "cocoon"),
                    (stack, a, b, c) -> stack.getCapability(MothCapability.CAPABILITY).map(IMoth::hasCocoon).orElse(false) ? 1f: 0f);
        });
    }
    private static void onTooltip(ItemTooltipEvent event)
    {
        final ItemStack stack = event.getItemStack();
        final List<Component> text = event.getToolTip();
        if (!stack.isEmpty())
        {
            stack.getCapability(MothCapability.CAPABILITY).ifPresent(cap -> cap.addToolTipInfo(text));
        }
    }

    public static void registerColorHandlerItems(RegisterColorHandlersEvent.Item event)
    {
        for (Fluid fluid : ForgeRegistries.FLUIDS.getValues())
        {
            if (Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(fluid)).getNamespace().equals(MOD_ID))
            {
                event.register(new DynamicFluidContainerModel.Colors(), fluid.getBucket());
            }
        }
    }

}
