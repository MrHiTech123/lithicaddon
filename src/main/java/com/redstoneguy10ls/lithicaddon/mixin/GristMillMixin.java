package com.redstoneguy10ls.lithicaddon.mixin;
/*
import blusunrize.immersiveengineering.api.multiblocks.blocks.env.IMultiblockContext;
import blusunrize.immersiveengineering.common.util.sound.MultiblockSound;
import com.hermitowo.advancedtfctech.client.ATTSounds;
import com.hermitowo.advancedtfctech.common.multiblocks.logic.BeamhouseLogic;
import com.hermitowo.advancedtfctech.common.multiblocks.logic.GristMillLogic;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.function.BooleanSupplier;

@Mixin(GristMillLogic.class)
public abstract class GristMillMixin {

    private BooleanSupplier isPlayingSound = () -> false;


    @Overwrite(remap=false)
    public void tickClient(IMultiblockContext<GristMillLogic.State> context)
    {
        final GristMillLogic.State state = context.getState();
        if (state.shouldRenderAsActive())
            state.driverAngle = (state.driverAngle + 18) % 360;
        if (!this.isPlayingSound.getAsBoolean())
        {
            final Vec3 soundPos = context.getLevel().toAbsolute(new Vec3(1.5, 1.5, 1.5));
            this.isPlayingSound = MultiblockSound.startSound(
                    () -> state.shouldRenderAsActive(), context.isValid(), soundPos, ATTSounds.GRIST_MILL, 0.5f
            );
        }
    }

}
*/