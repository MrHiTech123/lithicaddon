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
import java.util.function.Supplier;

public class boiledCacoon extends Item {


    public boiledCacoon(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess carried)
    {
        if (action == ClickAction.SECONDARY && Helpers.isItem(other, TFCTags.Items.KNIVES))
        {

            AtomicInteger rands = new AtomicInteger(player.getRandom().nextIntBetweenInclusive(1, 8));
                    while(rands.get() > 0)
                    {
                        ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(lithicItems.FOODS.get(lithicFood.BOILED_GRUB).get()));
                        rands.getAndDecrement();
                    }
                    ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(lithicItems.BALL_OF_SILK.get()));
                    other.hurtAndBreak(1,player, p -> {});
                    stack.shrink(1);
                    return true;
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag advanced)
    {
        tooltip.add(Component.translatable("lithic.moth.scrape").withStyle(ChatFormatting.WHITE));
    }

}
