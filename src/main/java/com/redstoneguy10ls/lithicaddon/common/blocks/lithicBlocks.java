package com.redstoneguy10ls.lithicaddon.common.blocks;

import com.redstoneguy10ls.lithicaddon.common.blockentities.lithicBlockEntities;
import com.redstoneguy10ls.lithicaddon.common.blockentities.mothBlockEntity;
import com.redstoneguy10ls.lithicaddon.common.fluids.lithicAcids;
import com.redstoneguy10ls.lithicaddon.common.fluids.lithicFluids;
import com.redstoneguy10ls.lithicaddon.common.fluids.lithicGlass;
import com.redstoneguy10ls.lithicaddon.common.fluids.lithicMetals;
import com.redstoneguy10ls.lithicaddon.common.items.lithicItems;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.wood.ExtendedRotatedPillarBlock;
import net.dries007.tfc.common.items.CandleBlockItem;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.redstoneguy10ls.lithicaddon.LithicAddon.MOD_ID;

public class lithicBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MOD_ID);

    public static final Map<lithicMetals, RegistryObject<LiquidBlock>> METAL_FLUIDS = Helpers.mapOfKeys(lithicMetals.class, metal ->
            registerNoItem("fluid/metal/" + metal.toString(), () -> new
                    LiquidBlock(lithicFluids.METALS.get(metal).source(), BlockBehaviour.Properties.copy(Blocks.LAVA).noLootTable()))
    );

    public static final Map<lithicAcids, RegistryObject<LiquidBlock>> ACID_FLUIDS = Helpers.mapOfKeys(lithicAcids.class, acid ->
            registerNoItem("fluid/acid/" + acid.toString(), () -> new
                    LiquidBlock(lithicFluids.ACIDS.get(acid).source(), BlockBehaviour.Properties.copy(Blocks.LAVA).noLootTable()))
    );

    public static final Map<lithicGlass, RegistryObject<LiquidBlock>> GLASS_FLUIDS = Helpers.mapOfKeys(lithicGlass.class, glass ->
            registerNoItem("fluid/glass/" + glass.toString(), () -> new
                    LiquidBlock(lithicFluids.GLASS.get(glass).source(), BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()))
    );

    public static final Map<Rock, Map<rockBlocks, RegistryObject<Block>>> ROCKS_BLOCKS = Helpers.mapOfKeys(Rock.class, rock ->
            Helpers.mapOfKeys(rockBlocks.class, type ->
                            register(("rock/"+type.name()+"/"+ rock.name()), () -> new Block(ExtendedProperties.of()
                            .mapColor(rock.color()).sound(SoundType.STONE).instrument(NoteBlockInstrument.BASEDRUM).properties().strength(6.5f,10).requiresCorrectToolForDrops())
                    )
                    )
            );
    public static final Map<Rock, RegistryObject<Block>> ROCKS_PILLARS = Helpers.mapOfKeys(Rock.class, rock ->(
                    register(("rock/pillar/"+ rock.name()), () -> new
                            ExtendedRotatedPillarBlock(ExtendedProperties.of().mapColor(rock.color()).instrument(NoteBlockInstrument.BASEDRUM).strength(6.5f,10).requiresCorrectToolForDrops())
                    )
            )
    );
    public static final RegistryObject<Block> SHH = register("shh", () -> new shh(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)
            .mapColor(MapColor.COLOR_LIGHT_BLUE)
            .forceSolidOff()
            .noCollission()
            .strength(0.1f)
            .sound(SoundType.WOOL)

            ));


    public static final RegistryObject<Block> LCANDLE_HOLDER = register("candle_holder",
            () -> new lcandleHolderBlock(ExtendedProperties.of(Blocks.CANDLE)
                    .mapColor(MapColor.SAND)
                    .randomTicks()
                    .noOcclusion()
                    .strength(0.1F)
                    .sound(SoundType.CANDLE)
                    .lightLevel(lcandleHolderBlock.LIGHTING_SCALE)
                    .blockEntity(TFCBlockEntities.TICK_COUNTER))
            , b -> new CandleBlockItem(new Item.Properties(), b, TFCBlocks.CANDLE_CAKE));

    public static final RegistryObject<Block> MOTHBOX = register("mothbox",
            () -> new mothboxBlock(ExtendedProperties.of()
                    .strength(0.6f)
                    .sound(SoundType.WOOD)
                    .flammable(60, 30)
                    .randomTicks()
                    .blockEntity(lithicBlockEntities.MOTHBOX)
                    .serverTicks(mothBlockEntity::serverTick)));




    //BlockBehaviour.Properties.of().mapColor(rock.color()).instrument(NoteBlockInstrument.BASEDRUM).strength(2f)
    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }
    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties()));
    }
    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return RegistrationHelpers.registerBlock(lithicBlocks.BLOCKS, lithicItems.ITEMS, name, blockSupplier, blockItemFactory);
    }
}
