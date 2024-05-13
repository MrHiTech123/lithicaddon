package com.redstoneguy10ls.lithicaddon.common.items;

import com.redstoneguy10ls.lithicaddon.common.fluids.lithicAcids;
import com.redstoneguy10ls.lithicaddon.common.fluids.lithicFluids;
import com.redstoneguy10ls.lithicaddon.common.fluids.lithicGlass;
import com.redstoneguy10ls.lithicaddon.common.fluids.lithicMetals;
import net.dries007.tfc.common.fluids.FluidId;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.JarItem;
import net.dries007.tfc.common.items.ToolItem;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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

    public static final RegistryObject<Item> ALUMINUM_LID = register("aluminum_lid");
    public static final RegistryObject<Item> STAINLESS_STEEL_LID = register("stainless_steel_lid");

    public static final RegistryObject<Item> EMPTY_JAR_WITH_ALUMINUM_LID = register("empty_jar_with_aluminum_lid",
            () -> new JarItem(new Item.Properties(), Helpers.identifier("block/jar"), false));
    public static final RegistryObject<Item> EMPTY_JAR_WITH_STAINLESS_STEEL_LID = register("empty_jar_with_stainless_steel_lid",
            () -> new JarItem(new Item.Properties(), Helpers.identifier("block/jar"), false));


    public static final Map<Food, RegistryObject<Item>> FRUIT_PRESERVES_ALUMINUM = Helpers.mapOfKeys(Food.class, Food::isFruit, food ->
            register("aluminum_jar/" + food.name(), () -> new JarItem(new Item.Properties(), food.name().toLowerCase(Locale.ROOT), false))
    );
    public static final Map<Food, RegistryObject<Item>> FRUIT_PRESERVES_STAINLESS_STEEL = Helpers.mapOfKeys(Food.class, Food::isFruit, food ->
            register("stainless_steel_jar/" + food.name(), () -> new JarItem(new Item.Properties(), food.name().toLowerCase(Locale.ROOT), false))
    );

    public static final Map<lithicMetals, RegistryObject<BucketItem>> METAL_FLUIDS_BUCKETS =
            Helpers.mapOfKeys(lithicMetals.class, fluid -> register("bucket/" + fluid.getId(),
                    () -> new BucketItem(lithicFluids.METALS.get(fluid).source(),
                            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)))
            );

    public static final Map<lithicAcids, RegistryObject<BucketItem>> ACID_FLUIDS_BUCKETS =
            Helpers.mapOfKeys(lithicAcids.class, fluid -> register("bucket/" + fluid.getId(),
                    () -> new BucketItem(lithicFluids.ACIDS.get(fluid).source(),
                            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)))
            );

    public static final Map<lithicGlass, RegistryObject<BucketItem>> GLASS_FLUIDS_BUCKETS =
            Helpers.mapOfKeys(lithicGlass.class, fluid -> register("bucket/" + fluid.getId(),
                    () -> new BucketItem(lithicFluids.GLASS.get(fluid).source(),
                            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)))
            );

    public static final RegistryObject<Item> CHARCOAL_BRIQUETTE = register("charcoal_briquette", () ->
            new briquettesItem(new Item.Properties()));

    public static final RegistryObject<Item> MOLD_INGOT = register("mold_ingot");
    public static final RegistryObject<Item> MOLD_LAMP = register("mold_lamp");

    public static final RegistryObject<Item> COCOON = register("cocoon");
    public static final RegistryObject<Item> BOILED_COCOON = register("boiled_cocoon", () -> new boiledCacoon(prop()));
    public static final RegistryObject<Item> BALL_OF_SILK = register("ball_of_silk");


    public static final RegistryObject<Item> LARVA_LATTICE = register("larva_lattice", () -> new larvaLatticeItem(prop().stacksTo(1)));

    public static final Map<lithicFood, RegistryObject<Item>> FOODS =
            Helpers.mapOfKeys(lithicFood.class, food -> register("food/"+ food.name(),
                    () -> new Item(new Item.Properties().food(food.getFoodProperties()))));



    public static final RegistryObject<Item> LITHIC = register("lithic");

    private static RegistryObject<Item> register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item)
    {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }

    private static Item.Properties prop()
    {
        return new Item.Properties();
    }
    private static Supplier<Item> basicItem() {
        return () -> new Item( new Item.Properties());
    }

    private static Item.Properties foodProperties()
    {
        return new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f).build());
    }
}
