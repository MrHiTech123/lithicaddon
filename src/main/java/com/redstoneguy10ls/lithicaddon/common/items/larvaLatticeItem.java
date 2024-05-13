package com.redstoneguy10ls.lithicaddon.common.items;

import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.MothCapability;
import com.redstoneguy10ls.lithicaddon.common.capabilities.moth.MothHandler;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.util.Helpers;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class larvaLatticeItem extends Item {

    public larvaLatticeItem(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess carried)
    {
        if (action == ClickAction.SECONDARY && other.isEmpty())
        {

            AtomicInteger rands = new AtomicInteger(player.getRandom().nextIntBetweenInclusive(1, 8));
            return stack.getCapability(MothCapability.CAPABILITY).map(moth ->{
                if(moth.hasLarva() && moth.hasCocoon())
                {
                    slot.set(new ItemStack(this));
                    while(rands.get() > 0)
                    {
                        ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(lithicItems.COCOON.get()));
                        rands.getAndDecrement();
                    }
                    return true;
                }
                return false;
            }).orElse(false);
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag advanced)
    {
        stack.getCapability(MothCapability.CAPABILITY).ifPresent(moth -> {
            if (moth.daysAlive() >= moth.getDaysTillCocoon())
            {
                tooltip.add(Component.translatable("lithic.moth.pull").withStyle(ChatFormatting.WHITE));
            }
        });
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
    {
        return new MothHandler(stack);
    }
}
