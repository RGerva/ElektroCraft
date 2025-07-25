/**
 * Record: ResistorAssembleRecipe Immutable data structure for simplified object representation.
 *
 * <p>Created by: D56V1OK On: 2025/jul.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.recipe.station;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rgerva.elektrocraft.codec.ArrayCodec;
import com.rgerva.elektrocraft.codec.CodecFix;
import com.rgerva.elektrocraft.recipe.IngredientWithCount;
import com.rgerva.elektrocraft.recipe.ModRecipes;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record ResistorAssembleRecipe(ItemStack output, IngredientWithCount[] inputs)
    implements ModRecipes.ModBasicRecipe<RecipeInput> {
  @Override
  public List<Ingredient> getIngredients() {
    return List.of();
  }

  @Override
  public boolean isIngredient(ItemStack itemStack) {
    return Arrays.stream(inputs)
        .map(IngredientWithCount::input)
        .anyMatch(ingredient -> ingredient.test(itemStack));
  }

  @Override
  public boolean isResult(ItemStack itemStack) {
    return ItemStack.isSameItemSameComponents(output, itemStack);
  }

  @Override
  public boolean matches(RecipeInput recipeInput, Level level) {
    if (level.isClientSide) return false;

    boolean[] usedIndices = new boolean[4];
    for (int i = 0; i < 4; i++) {
      usedIndices[i] = recipeInput.getItem(i).isEmpty();
    }

    int len = Math.min(inputs.length, 4);
    for (int i = 0; i < len; i++) {
      IngredientWithCount input = inputs[i];

      int indexMinCount = -1;
      int minCount = Integer.MAX_VALUE;
      for (int j = 0; j < 4; j++) {
        if (usedIndices[j]) {
          continue;
        }
        ItemStack item = recipeInput.getItem(j);

        if ((indexMinCount == -1 || item.getCount() < minCount)
            && input.input().test(item)
            && item.getCount() >= input.count()) {
          indexMinCount = j;
          minCount = item.getCount();
        }
      }

      if (indexMinCount == -1) return false;

      usedIndices[indexMinCount] = true;
    }

    return true;
  }

  @Override
  public ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider provider) {
    return output;
  }

  @Override
  public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
    return ModRecipes.RESISTOR_ASSEMBLE_SERIALIZER.get();
  }

  @Override
  public RecipeType<? extends Recipe<RecipeInput>> getType() {
    return ModRecipes.RESISTOR_ASSEMBLE_TYPE.get();
  }

  @Override
  public PlacementInfo placementInfo() {
    return PlacementInfo.NOT_PLACEABLE;
  }

  @Override
  public RecipeBookCategory recipeBookCategory() {
    return ModRecipes.RESISTOR_ASSEMBLE_CATEGORY.get();
  }

  public static final class Serializer implements RecipeSerializer<ResistorAssembleRecipe> {
    private final MapCodec<ResistorAssembleRecipe> CODEC =
        RecordCodecBuilder.mapCodec(
            (instance) -> {
              return instance
                  .group(
                      CodecFix.ITEM_STACK_CODEC
                          .fieldOf("result")
                          .forGetter(
                              (recipe) -> {
                                return recipe.output;
                              }),
                      new ArrayCodec<>(IngredientWithCount.CODEC, IngredientWithCount[]::new)
                          .fieldOf("ingredients")
                          .forGetter(
                              (recipe) -> {
                                return recipe.inputs;
                              }))
                  .apply(instance, ResistorAssembleRecipe::new);
            });

    private final StreamCodec<RegistryFriendlyByteBuf, ResistorAssembleRecipe> STREAM_CODEC =
        StreamCodec.of(
            ResistorAssembleRecipe.Serializer::write, ResistorAssembleRecipe.Serializer::read);

    @Override
    public MapCodec<ResistorAssembleRecipe> codec() {
      return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ResistorAssembleRecipe> streamCodec() {
      return STREAM_CODEC;
    }

    private static ResistorAssembleRecipe read(RegistryFriendlyByteBuf buffer) {
      int len = buffer.readInt();
      IngredientWithCount[] inputs = new IngredientWithCount[len];
      for (int i = 0; i < len; i++) inputs[i] = IngredientWithCount.STREAM_CODEC.decode(buffer);

      ItemStack output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);

      return new ResistorAssembleRecipe(output, inputs);
    }

    private static void write(RegistryFriendlyByteBuf buffer, ResistorAssembleRecipe recipe) {
      buffer.writeInt(recipe.inputs.length);
      for (int i = 0; i < recipe.inputs.length; i++)
        IngredientWithCount.STREAM_CODEC.encode(buffer, recipe.inputs[i]);

      ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output);
    }
  }
}
