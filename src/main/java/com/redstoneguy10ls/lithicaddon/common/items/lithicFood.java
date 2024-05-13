package com.redstoneguy10ls.lithicaddon.common.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public enum lithicFood {
    BOILED_GRUB(true,false,false);

    private final boolean meat, fast,fruit;

    lithicFood(){this(false,false,false);}

    lithicFood(boolean meat, boolean fast,boolean fruit)
    {
        this.meat = meat;
        this.fast = fast;
        this.fruit =fruit;
    }

    public FoodProperties getFoodProperties()
    {
        FoodProperties.Builder builder = new FoodProperties.Builder();
        if(meat) builder.meat();
        if(fast) builder.fast();
        return builder.nutrition(4).saturationMod(0.3f).build();
    }
    public Item.Properties createProperties()
    {
        return new Item.Properties().food(getFoodProperties());
    }
}
