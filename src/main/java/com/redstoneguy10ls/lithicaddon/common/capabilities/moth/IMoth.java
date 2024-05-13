package com.redstoneguy10ls.lithicaddon.common.capabilities.moth;

import com.redstoneguy10ls.lithicaddon.config.lithicConfig;
import net.dries007.tfc.util.Helpers;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public interface IMoth extends INBTSerializable<CompoundTag> {

    int DAYS_TILL_COCOON = Helpers.getValueOrDefault(lithicConfig.SERVER.daysTillCocoon);

    int daysAlive();

    default int getDaysTillCocoon(){return DAYS_TILL_COCOON;}

    void setDaysAlive(int value);

    void setHasLarva(boolean exists);

    boolean hasLarva();

    boolean hasCocoon();

    void setHasCocoon(boolean exists);


    default void initLarva()
    {
        setDaysAlive(1);
        setHasLarva(true);
    }


    default void addToolTipInfo(List<Component> tooltip)
    {
        if(hasLarva())
        {
            tooltip.add(Component.translatable("lithic.moth.larva").withStyle(ChatFormatting.GOLD));
            if(hasCocoon())
            {
                tooltip.add(Component.translatable("lithic.moth.cocoon").withStyle(ChatFormatting.GOLD));

            }
            else {
                tooltip.add(Component.translatable("lithic.moth.till_cocoon", String.valueOf(DAYS_TILL_COCOON -daysAlive()) ).withStyle(ChatFormatting.WHITE));

            }

        }
        else
        {
            tooltip.add(Component.translatable("lithic.moth.no_larva").withStyle(ChatFormatting.RED));
        }
    }

    void addTooltipInfo(ItemStack stack, List<Component> tooltip);
}
