/**
 *  Generic Class: HammerRecipe <T>
 *  A generic structure that works with type parameters.
 *  
 *  Created by: D56V1OK
 *  On: 2025/jun.
 *  
 *  GitHub: https://github.com/RGerva
 *  
 *  Copyright (c) 2025 @RGerva. All Rights Reserved.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.rgerva.elektrocraft.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rgerva.elektrocraft.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record HammerRecipe(Ingredient inputItem, ItemStack output) implements Recipe<RecipeInput> {

    @Override
    public boolean matches(RecipeInput recipeInput, Level level) {
        return inputItem.test(recipeInput.getItem(0)) &&
                recipeInput.getItem(1).getItem() == ModItems.HAMMER.get();
    }

    @Override
    public ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return ModRecipes.HAMMER_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return ModRecipes.HAMMER_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(inputItem);
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    public static class Serializer implements RecipeSerializer<HammerRecipe> {

        public static final MapCodec<HammerRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter(HammerRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(HammerRecipe::output)
        ).apply(inst, HammerRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, HammerRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, HammerRecipe::inputItem,
                        ItemStack.STREAM_CODEC, HammerRecipe::output,
                        HammerRecipe::new);

        @Override
        public MapCodec<HammerRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, HammerRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
