package com.redstoneguy10ls.lithicaddon.common.items;

import net.dries007.tfc.common.items.ToolItem;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import static com.redstoneguy10ls.lithicaddon.LithicAddon.MOD_ID;

public class lithicItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);


    public static final Map<Metal.Default, RegistryObject<Item>> METAL_SPINDLES =
            Helpers.mapOfKeys(Metal.Default.class, Metal.Default::hasTools, metals ->
                    register("metal_spindle/" + metals.name(), () ->
                    new metalSpindle(metals.toolTier(), new Item.Properties())));
    public static final Map<Metal.Default, RegistryObject<Item>> SPINDLE_HEADS =
            Helpers.mapOfKeys(Metal.Default.class, Metal.Default::hasTools, metals ->
                    register("metal_spindle_head/" + metals.name(), basicItem())
            );

    public static final RegistryObject<Item> LITHIC = register("lithic");

    private static RegistryObject<Item> register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item)
    {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }

    private static Supplier<Item> basicItem() {
        return () -> new Item( new Item.Properties());
    }
}
