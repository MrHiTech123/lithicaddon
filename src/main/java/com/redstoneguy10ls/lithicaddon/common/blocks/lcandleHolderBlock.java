package com.redstoneguy10ls.lithicaddon.common.blocks;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.dries007.tfc.common.blocks.EntityBlockExtension;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.IForgeBlockExtension;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class lcandleHolderBlock extends CandleBlock implements IForgeBlockExtension, EntityBlockExtension {

    public static void onRandomTick(BlockState state, ServerLevel level, BlockPos pos)
    {
        if(level.getBlockEntity(pos) instanceof TickCounterBlockEntity candle)
        {
            final int candleTicks = TFCConfig.SERVER.candleTicks.get();
            if (candle.getTicksSinceUpdate() > candleTicks && candleTicks > 0)
            {
                level.setBlockAndUpdate(pos, state.setValue(LIT, false));
            }
        }
    }
    public static final ToIntFunction<BlockState> LIGHTING_SCALE = (state) -> state.getValue(LIT) ? 3 * state.getValue(CANDLES) + 2 : 0;

    private final ExtendedProperties properties;


    public lcandleHolderBlock(ExtendedProperties properties)
    {
        super(properties.properties());
        this.properties = properties;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if(Helpers.isItem(player.getMainHandItem(), TFCItems.FIRESTARTER.get()))
        {
            return InteractionResult.PASS;
        }
        return super.use(state,level,pos,player,hand,hit);
    }

    @Override
    public ExtendedProperties getExtendedProperties()
    {
        return this.properties;
    }


    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand)
    {
        onRandomTick(state, level, pos);
    }

}
