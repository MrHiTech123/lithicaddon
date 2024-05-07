package com.redstoneguy10ls.lithicaddon.common.blockentities;

import com.redstoneguy10ls.lithicaddon.common.blocks.lithicBlocks;
import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.dries007.tfc.common.blockentities.TickableBlockEntity;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.redstoneguy10ls.lithicaddon.LithicAddon.MOD_ID;
/*
public class lithicBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MOD_ID);


    public static final RegistryObject<BlockEntityType<TickCounterBlockEntity>> TICK_COUNTER = register
            ("tick_counter", TickCounterBlockEntity::new, Stream.of(
         lithicBlocks.LCANDLE_HOLDER

    ).flatMap(Helpers::flatten)
    );


    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks);
    }
}
*/