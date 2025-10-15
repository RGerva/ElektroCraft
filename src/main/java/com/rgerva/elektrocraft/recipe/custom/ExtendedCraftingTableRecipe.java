/**
 * Record: ExtendedCraftingTableRecipe
 * Immutable data structure for simplified object representation.
 * <p>
 * Created by: D56V1OK
 * On: 2025/out.
 * <p>
 * GitHub: https://github.com/RGerva
 * <p>
 * Copyright (c) 2025 @RGerva. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.rgerva.elektrocraft.recipe.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rgerva.elektrocraft.recipe.ModRecipes;
import com.rgerva.elektrocraft.recipe.input.ExtendedCraftingTableRecipeInput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record ExtendedCraftingTableRecipe(Ingredient inputItem,
                                          ItemStack output) implements Recipe<ExtendedCraftingTableRecipeInput> {

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(ExtendedCraftingTableRecipeInput pInput, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }
        return inputItem.test(pInput.getItem(0));
    }

    @Override
    public ItemStack assemble(ExtendedCraftingTableRecipeInput extendedCraftingTableRecipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<ExtendedCraftingTableRecipeInput>> getSerializer() {
        return ModRecipes.EXTENDED_CRAFTING_TABLE_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<ExtendedCraftingTableRecipeInput>> getType() {
        return ModRecipes.EXTENDED_CRAFTING_TABLE_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipes.EXTENDED_CRAFTING_TABLE_CATEGORY.get();
    }

    public static class Serializer implements RecipeSerializer<ExtendedCraftingTableRecipe> {

        public static final MapCodec<ExtendedCraftingTableRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter(ExtendedCraftingTableRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(ExtendedCraftingTableRecipe::output)
        ).apply(inst, ExtendedCraftingTableRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ExtendedCraftingTableRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, ExtendedCraftingTableRecipe::inputItem,
                        ItemStack.STREAM_CODEC, ExtendedCraftingTableRecipe::output,
                        ExtendedCraftingTableRecipe::new);

        @Override
        public MapCodec<ExtendedCraftingTableRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ExtendedCraftingTableRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
