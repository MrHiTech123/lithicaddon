package com.redstoneguy10ls.lithicaddon.util;

import com.redstoneguy10ls.lithicaddon.LithicAddon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class lithicTags {

    public static class Items {
        public static final TagKey<Item> MOTH_FOOD = tag("moth_food");


        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(LithicAddon.MOD_ID, name));
        }

    }
}
