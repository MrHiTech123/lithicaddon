package com.redstoneguy10ls.lithicaddon.common.blocks;

import com.redstoneguy10ls.lithicaddon.common.fluids.lithicFluids;
import com.redstoneguy10ls.lithicaddon.common.fluids.lithicMetals;
import com.redstoneguy10ls.lithicaddon.common.items.lithicItems;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.wood.ExtendedRotatedPillarBlock;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
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

    public static final Map<Rock, Map<rockBlocks, RegistryObject<Block>>> ROCKS_BLOCKS = Helpers.mapOfKeys(Rock.class, rock ->
            Helpers.mapOfKeys(rockBlocks.class, type ->
                            register(("rock/"+type.name()+"/"+ rock.name()), () -> new Block(ExtendedProperties.of()
                            .mapColor(rock.color()).sound(SoundType.STONE).instrument(NoteBlockInstrument.BASEDRUM).properties())
                    )
                    )
            );
    public static final Map<Rock, RegistryObject<Block>> ROCKS_PILLARS = Helpers.mapOfKeys(Rock.class, rock ->(
                    register(("rock/pillar/"+ rock.name()), () -> new
                            ExtendedRotatedPillarBlock(ExtendedProperties.of().mapColor(rock.color()).instrument(NoteBlockInstrument.BASEDRUM).strength(2f))
                    )
            )
    );



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
