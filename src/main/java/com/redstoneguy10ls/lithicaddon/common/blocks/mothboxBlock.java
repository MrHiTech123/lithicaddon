package com.redstoneguy10ls.lithicaddon.common.blocks;

import com.redstoneguy10ls.lithicaddon.common.blockentities.lithicBlockEntities;
import com.redstoneguy10ls.lithicaddon.common.blockentities.mothBlockEntity;
import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.IMoth;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.devices.DeviceBlock;
import net.dries007.tfc.common.blocks.soil.HoeOverlayBlock;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.Climate;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class mothboxBlock extends DeviceBlock implements HoeOverlayBlock {

    public static final BooleanProperty LARVA = lithicStateProperties.LARVA;

    public mothboxBlock(ExtendedProperties properties)
    {
        super(properties, InventoryRemoveBehavior.DROP);
        registerDefaultState(getStateDefinition().any().setValue(LARVA, false));

    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(!player.isShiftKeyDown())
        {
            if (player instanceof ServerPlayer serverPlayer)
            {
                level.getBlockEntity(pos,lithicBlockEntities.MOTHBOX.get()).ifPresent(box -> Helpers.openScreen(serverPlayer, box,pos));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);

        }
        return InteractionResult.PASS;

    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand)
    {
        level.getBlockEntity(pos, lithicBlockEntities.MOTHBOX.get()).ifPresent(mothBlockEntity::tryPeriodicUpdate);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder.add(LARVA));
    }

    @Override
    public void addHoeOverlayInfo(Level level, BlockPos pos, BlockState blockState, List<Component> text, boolean debug)
    {
        level.getBlockEntity(pos, lithicBlockEntities.MOTHBOX.get()).ifPresent(box -> {
            if(box.getLeaves() > 0)
            {
                box.calculateLeaves();
                text.add(Component.translatable("lithic.moth.leaves",String.valueOf(box.getLeaves())).withStyle(ChatFormatting.GREEN));
            }
            else
            {
                text.add(Component.translatable("lithic.moth.no_leaves").withStyle(ChatFormatting.RED));
            }
            final float temp = Climate.getTemperature(level,pos);
            final float rain = Climate.getRainfall(level,pos);
            int ord = 0;
            final List<IMoth> moths = new ArrayList<>();
            int noLarva = 0;
            for(IMoth moth : box.getCachedMoths())
            {
                if(ord == 0)
                {
                    ord++;
                    continue;
                }
                MutableComponent mothText = Component.translatable("lithic.moth.lattices", (ord));
                ord++;
                //temp check
                if((temp <= box.MAX_TEMP && temp >= box.MIN_TEMP) && (rain <= box.MAX_RAIN && rain >= box.MIN_RAIN))
                {
                    //if has larva
                    if (moth != null && moth.hasLarva()) {
                        mothText.append(Component.translatable("lithic.moth.has_larva"));
                        if (moth.getDaysTillCocoon() > moth.daysAlive()) {
                            mothText.append(Component.translatable("lithic.moth.till_cocoon",
                                    String.valueOf(moth.getDaysTillCocoon() - moth.daysAlive())).withStyle(ChatFormatting.WHITE));
                        } else {
                            mothText.append(Component.translatable("lithic.moth.cocoon").withStyle(ChatFormatting.GOLD));
                        }
                    }
                    else {
                        if(moth != null) noLarva++;
                        mothText.append(Component.translatable("lithic.moth.empty"));
                    }
                    text.add(mothText);

                }
                else {
                    if(temp > box.MAX_TEMP)
                    {
                        mothText.append(Component.translatable("lithic.moth.too_hot", box.MAX_TEMP, String.format("%.2f", temp)).withStyle(ChatFormatting.RED));
                    }
                    if(temp < box.MIN_TEMP)
                    {
                        mothText.append(Component.translatable("lithic.moth.too_cold", box.MIN_TEMP, String.format("%.2f", temp)).withStyle(ChatFormatting.AQUA));
                    }
                    if(rain > box.MAX_RAIN)
                    {
                        mothText.append(Component.translatable("lithic.moth.too_wet", box.MAX_RAIN, String.format("%.2f", rain)).withStyle(ChatFormatting.BLUE));
                    }
                    if(rain < box.MIN_RAIN)
                    {
                        mothText.append(Component.translatable("lithic.moth.too_dry", box.MIN_RAIN, String.format("%.2f", rain)).withStyle(ChatFormatting.YELLOW));
                    }
                    text.add(mothText);


                }
            }
            final int lights = box.getLight();
            final int dimLights = box.getDimLight();
            text.add(Component.translatable("lithic.moth.lights",  lights));
            //checks how many light blocks are around
            if(lights < mothBlockEntity.MIN_LIGHTS)
            {
                //if theres more dim lights than the minimum amount of light required aka if theres enough light but its to dim
                //then say that
                if(dimLights >= mothBlockEntity.MIN_LIGHTS)
                {
                    text.add(Component.translatable("lithic.moth.too_dim"));
                }
                else {
                    text.add(Component.translatable("lithic.moth.min_lights"));
                }
            }
            else
            {
                if(moths.size() < 5)
                {
                    int breed = box.getBreedTickChanceInverted(lights);
                    if (breed == 0) text.add(Component.translatable("lithic.moth.larva_chance_100"));
                    else text.add(Component.translatable("lithic.moth.larva_chance", breed));
                }
            }


        });
    }



}
