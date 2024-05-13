package com.redstoneguy10ls.lithicaddon;

import com.mojang.logging.LogUtils;
import com.redstoneguy10ls.lithicaddon.client.lithicClientEventHandler;
import com.redstoneguy10ls.lithicaddon.common.blockentities.lithicBlockEntities;
import com.redstoneguy10ls.lithicaddon.common.blocks.lithicBlocks;
import com.redstoneguy10ls.lithicaddon.common.container.lithicContainerTypes;
import com.redstoneguy10ls.lithicaddon.common.fluids.lithicFluids;
import com.redstoneguy10ls.lithicaddon.common.items.lithicItems;
import com.redstoneguy10ls.lithicaddon.common.items.lithicTab;
import com.redstoneguy10ls.lithicaddon.common.recipe.LithicRecipeSerializer;
import com.redstoneguy10ls.lithicaddon.config.lithicConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

import java.util.Scanner;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LithicAddon.MOD_ID)
public class LithicAddon
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "lithicaddon";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public LithicAddon()
    {

        lithicConfig.init();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::addCreative);

        lithicItems.ITEMS.register(bus);
        lithicTab.CREATIVE_TABS.register(bus);
        lithicBlocks.BLOCKS.register(bus);
        lithicFluids.FLUIDS.register(bus);
        lithicBlockEntities.BLOCK_ENTITIES.register(bus);
        lithicContainerTypes.CONTAINERS.register(bus);

        LithicRecipeSerializer.RECIPE_SERIALIZERS.register(bus);

        if(FMLEnvironment.dist == Dist.CLIENT)
        {
            lithicClientEventHandler.init();

        }

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
