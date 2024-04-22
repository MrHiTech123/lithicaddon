package com.redstoneguy10ls.lithicaddon.common.recipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.redstoneguy10ls.lithicaddon.LithicAddon.MOD_ID;

public class LithicRecipeSerializer {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, MOD_ID);

    public static final RegistryObject<jarRecipe.jarRecipeSerializer> JAR_RECIPE_SHAPELESS = register("jar_recipe",
            () -> jarRecipe.jarRecipeSerializer.shapeless(jarRecipe.Shapeless::new));

    //public static final RegistryObject<alPotsRecipe.Serializer> POT_JAM_AL = register("pot_jam_al", alPotsRecipe.Serializer::new);

    //public static final RegistryObject<lithicPotsRecipe.Serializer> POT_JAMING = register("pot_jaming", lithicPotsRecipe.Serializer::new);

    //public static final RegistryObject<alPotsRecipe.Serializer> POT_JAM_SS = register("pot_jam_ss", alPotsRecipe.Serializer::new);

    private static <S extends RecipeSerializer<?>> RegistryObject<S> register(String name, Supplier<S> factory)
    {
        return RECIPE_SERIALIZERS.register(name, factory);
    }

}
