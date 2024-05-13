package com.redstoneguy10ls.lithicaddon.mixin;
/*
import blusunrize.immersiveengineering.api.multiblocks.blocks.env.IMultiblockContext;
import blusunrize.immersiveengineering.common.util.sound.MultiblockSound;
import com.hermitowo.advancedtfctech.client.ATTSounds;
import com.hermitowo.advancedtfctech.common.multiblocks.logic.BeamhouseLogic;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.BooleanSupplier;

@Mixin(BeamhouseLogic.class)
public abstract class BeamHouseMixin {

    private BooleanSupplier isPlayingSound = () -> false;


    @Overwrite(remap=false)
    public void tickClient(IMultiblockContext<BeamhouseLogic.State> context)
    {
        final BeamhouseLogic.State state = context.getState();
        if (state.shouldRenderAsActive())
            state.barrelAngle = (state.barrelAngle + 18) % 360;
        if (!this.isPlayingSound.getAsBoolean())
        {
            final Vec3 soundPos = context.getLevel().toAbsolute(new Vec3(1.5, 1.5, 1.5));
            this.isPlayingSound = MultiblockSound.startSound(
                    () -> state.shouldRenderAsActive(), context.isValid(), soundPos, ATTSounds.BEAMHOUSE,0.5f
            );
        }
    }

}
*/
