package com.redstoneguy10ls.lithicaddon.common.fluids;

import com.redstoneguy10ls.lithicaddon.common.blocks.lithicBlocks;
import com.redstoneguy10ls.lithicaddon.common.items.lithicItems;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.fluids.*;
import net.dries007.tfc.common.fluids.FluidRegistryObject;
import net.dries007.tfc.common.fluids.MoltenFluid;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.redstoneguy10ls.lithicaddon.LithicAddon.MOD_ID;
import static net.dries007.tfc.common.fluids.TFCFluids.*;

public final class lithicFluids {
    /*
        public static final Map<lithicMetals, FluidRegistryObject<ForgeFlowingFluid>> METALS = Helpers.mapOfKeys(lithicMetals.class, metal -> register(
            "metal/" + metal.getId(),
            properties -> properties
                    .block(lithicBlocks.METAL_FLUIDS.get(metal))
                    .bucket(lithicItems.FLUID_BUCKETS.get(FluidId.asType(metal)))
                    .explosionResistance(100),
            lavaLike()
                    .descriptionId("fluid.lithic.metal." + metal.getId()),
            new FluidTypeClientProperties(ALPHA_MASK | metal.getColor(), MOLTEN_STILL, MOLTEN_FLOW, null, null),
            MoltenFluid.Source::new,
            MoltenFluid.Flowing::new
    ));
     */

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MOD_ID);

    public static final Map<lithicMetals, FluidRegistryObject<ForgeFlowingFluid>> METALS = Helpers.mapOfKeys(lithicMetals.class, metal
            -> register(
            "metal/" + metal.getId(),
            properties -> properties
                    .block(lithicBlocks.METAL_FLUIDS.get(metal))
                    .bucket(lithicItems.METAL_FLUIDS_BUCKETS.get(metal))
                    .explosionResistance(100),
            lavaLike()
                    .descriptionId("fluid.lithic.metal." + metal.getId())
                    .canConvertToSource(false),
            new FluidTypeClientProperties(ALPHA_MASK | metal.getColor(), MOLTEN_STILL, MOLTEN_FLOW, null, null),
            MoltenFluid.Source::new,
            MoltenFluid.Flowing::new
    ));

    public static final Map<lithicAcids, FluidRegistryObject<ForgeFlowingFluid>> ACIDS = Helpers.mapOfKeys(lithicAcids.class, acid
            -> register(
            "acid/" + acid.getId(),
            properties -> properties
                    .block(lithicBlocks.ACID_FLUIDS.get(acid))
                    .bucket(lithicItems.ACID_FLUIDS_BUCKETS.get(acid))
                    .explosionResistance(100),
            lavaLike()
                    .descriptionId("fluid.lithic.acid." + acid.getId())
                    .canConvertToSource(false),
            new FluidTypeClientProperties(ALPHA_MASK | acid.getColor(), MOLTEN_STILL, MOLTEN_FLOW, null, null),
            MoltenFluid.Source::new,
            MoltenFluid.Flowing::new
    ));

    public static final Map<lithicGlass, FluidRegistryObject<ForgeFlowingFluid>> GLASS = Helpers.mapOfKeys(lithicGlass.class, glass
            -> register(
            "glass/" + glass.getId(),
            properties -> properties
                    .block(lithicBlocks.GLASS_FLUIDS.get(glass))
                    .bucket(lithicItems.GLASS_FLUIDS_BUCKETS.get(glass))
                    .explosionResistance(100),
            lavaLike()
                    .descriptionId("fluid.lithic.glass." + glass.getId())
                    .canConvertToSource(false),
            new FluidTypeClientProperties(ALPHA_MASK | glass.getColor(), MOLTEN_STILL, MOLTEN_FLOW, null, null),
            MoltenFluid.Source::new,
            MoltenFluid.Flowing::new
    ));


    private static FluidType.Properties lavaLike()
    {
        return FluidType.Properties.create()
                .adjacentPathType(BlockPathTypes.LAVA)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                .lightLevel(0)
                .density(3000)
                .viscosity(6000)
                .temperature(1300)
                .canConvertToSource(false)
                .canDrown(false)
                .canExtinguish(false)
                .canHydrate(false)
                .canPushEntity(false)
                .canSwim(false)
                .supportsBoating(false);
    }


    private static <F extends FlowingFluid> FluidRegistryObject<F> register(String name, Consumer<ForgeFlowingFluid.Properties> builder, FluidType.Properties typeProperties, FluidTypeClientProperties clientProperties, Function<ForgeFlowingFluid.Properties, F> sourceFactory, Function<ForgeFlowingFluid.Properties, F> flowingFactory)
    {
        // Names `metal/foo` to `metal/flowing_foo`
        final int index = name.lastIndexOf('/');
        final String flowingName = index == -1 ? "flowing_" + name : name.substring(0, index) + "/flowing_" + name.substring(index + 1);

        return RegistrationHelpers.registerFluid(FLUID_TYPES, FLUIDS, name, name, flowingName, builder,
                () -> new ExtendedFluidType(typeProperties, clientProperties), sourceFactory, flowingFactory);
    }


}
