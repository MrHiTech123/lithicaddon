package com.redstoneguy10ls.lithicaddon.common.capabilities.moth;

import net.dries007.tfc.util.Helpers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class MothCapability {
    public static final Capability<IMoth> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    public static final ResourceLocation KEY = new ResourceLocation("lithicaddon","moth");
}
