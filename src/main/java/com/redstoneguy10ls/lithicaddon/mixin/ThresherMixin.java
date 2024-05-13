package com.redstoneguy10ls.lithicaddon.mixin;
/*
import blusunrize.immersiveengineering.api.multiblocks.blocks.env.IMultiblockContext;
import blusunrize.immersiveengineering.common.util.sound.MultiblockSound;
import com.hermitowo.advancedtfctech.client.ATTSounds;
import com.hermitowo.advancedtfctech.common.multiblocks.logic.BeamhouseLogic;
import com.hermitowo.advancedtfctech.common.multiblocks.logic.ThresherLogic;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.function.BooleanSupplier;

@Mixin(ThresherLogic.class)
public abstract class ThresherMixin {

    private BooleanSupplier isPlayingSound = () -> false;

    private boolean active;


    @Overwrite(remap=false)
    public void tickClient(IMultiblockContext<ThresherLogic.State> context)
    {
        final ThresherLogic.State state = context.getState();
        if (!this.isPlayingSound.getAsBoolean())
        {
            final Vec3 soundPos = context.getLevel().toAbsolute(new Vec3(1.5, 1.5, 1.5));
            this.isPlayingSound = MultiblockSound.startSound(
                    () -> this.active, context.isValid(), soundPos, ATTSounds.THRESHER, 0.5f
            );
        }
    }

}
*/
