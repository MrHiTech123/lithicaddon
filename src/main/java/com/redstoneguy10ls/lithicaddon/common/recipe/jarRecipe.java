package com.redstoneguy10ls.lithicaddon.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.dries007.tfc.common.recipes.DelegateRecipe;
import net.dries007.tfc.common.recipes.RecipeSerializerImpl;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.JsonHelpers;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.items.ItemHandlerHelper;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class jarRecipe<R extends Recipe<CraftingContainer>> extends DelegateRecipe<R, CraftingContainer> implements CraftingRecipe {

    private final CraftingBookCategory category;
    private final List<ItemStack> lid;

    private final float chance;

    protected jarRecipe(ResourceLocation id, CraftingBookCategory category, R recipe, List<ItemStack> lid, float chance)
    {
        super(id, recipe);
        this.category = category;
        this.lid = lid;
        this.chance = chance;
    }

    @Override
    public CraftingBookCategory category()
    {
        return category;
    }

    public List<ItemStack> getExtraProducts()
    {
        return lid;
    }

    public float getBreackChance(){return chance;}

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv)
    {
        Player player = ForgeHooks.getCraftingPlayer();
        final jarRecipe recipe = jarRecipe.this;
        if (player != null && player.getRandom().nextFloat() > recipe.getBreackChance())
        {
            lid.forEach(item -> ItemHandlerHelper.giveItemToPlayer(player, item.copy()));
        }
        return super.getRemainingItems(inv);
    }
    public static class Shapeless extends jarRecipe<Recipe<CraftingContainer>> {
        public Shapeless(ResourceLocation id, CraftingBookCategory category, Recipe<CraftingContainer> recipe, List<ItemStack> lid,float chance) {
            super(id, category, recipe, lid, chance);
        }

        @Override
        public RecipeSerializer<?> getSerializer() {
            return LithicRecipeSerializer.JAR_RECIPE_SHAPELESS.get();
        }
    }
    public static class jarRecipeSerializer extends RecipeSerializerImpl<jarRecipe<?>>
        {
            public interface Factory
            {
                jarRecipe<?> apply(ResourceLocation id, CraftingBookCategory category, Recipe<CraftingContainer> recipe, List<ItemStack> list, float chance);
            }

            public static jarRecipeSerializer shapeless(Factory factory)
            {
                return new jarRecipeSerializer((id, category, delegate, list,chance) -> {
                    if (delegate instanceof IShapedRecipe)
                    {
                        throw new JsonParseException("Mixing shapeless delegate recipe type with shaped delegate, not allowed!");
                    }
                    return factory.apply(id, category, delegate, list,chance);
                });
            }

            private final Factory factory;

            public jarRecipeSerializer(Factory factory){this.factory = factory;}

            @Override
            public jarRecipe<?> fromJson(ResourceLocation recipeID, JsonObject json)
            {
                return fromJson(recipeID, json, ICondition.IContext.EMPTY);
            }

            @Override
            @SuppressWarnings("unchecked")
            public jarRecipe<?> fromJson(ResourceLocation recipeID, JsonObject json, ICondition.IContext context)
            {
                List<ItemStack> items = new ArrayList<>();
                float breakChance = JsonHelpers.getAsFloat(json,"break_chance");
                for (JsonElement element : json.getAsJsonArray("lid"))
                {
                        items.add(CraftingHelper.getItemStack(element.getAsJsonObject(),true));
                }
                Recipe<CraftingContainer> internal =
                        (Recipe<CraftingContainer>) RecipeManager.fromJson(DELEGATE, GsonHelper.getAsJsonObject(json, "recipe"), context);
                return factory.apply(recipeID, JsonHelpers.getCraftingCategory(json),internal,items,breakChance);
            }

            @Nullable
            @SuppressWarnings("unchecked")
            @Override
            public jarRecipe<?> fromNetwork(ResourceLocation recipeID, FriendlyByteBuf buffer)
            {
                CraftingBookCategory cat = buffer.readEnum(CraftingBookCategory.class);
                List<ItemStack> items = new ArrayList<>();
                float breakChance = buffer.readFloat();
                Helpers.decodeAll(buffer, items, FriendlyByteBuf::readItem);
                Recipe<CraftingContainer> internal = (Recipe<CraftingContainer>) ClientboundUpdateRecipesPacket.fromNetwork(buffer);
                return factory.apply(recipeID,cat,internal,items,breakChance);
            }

            @Override
            public void toNetwork(FriendlyByteBuf buffer, jarRecipe<?> recipe)
            {
                buffer.writeEnum(recipe.category);
                buffer.writeFloat(recipe.chance);
                Helpers.encodeAll(buffer, recipe.lid, (item, buf) -> buf.writeItem(item));
                ClientboundUpdateRecipesPacket.toNetwork(buffer, recipe.getDelegate());
            }


        }


}
