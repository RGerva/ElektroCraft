/**
 * Generic Class: ModRecipes <T> A generic structure that works with type parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jun.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.recipe;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.recipe.station.ChargerStationRecipe;
import com.rgerva.elektrocraft.recipe.station.ResistorAssembleRecipe;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {

  public interface ModBasicRecipe<T extends RecipeInput> extends Recipe<T> {
    List<Ingredient> getIngredients();

    boolean isIngredient(ItemStack itemStack);

    boolean isResult(ItemStack itemStack);
  }

  // ===== Registers =====
  public static final DeferredRegister<RecipeBookCategory> CATEGORIES =
      DeferredRegister.create(BuiltInRegistries.RECIPE_BOOK_CATEGORY, ElektroCraft.MOD_ID);

  public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
      DeferredRegister.create(Registries.RECIPE_SERIALIZER, ElektroCraft.MOD_ID);

  public static final DeferredRegister<RecipeType<?>> TYPES =
      DeferredRegister.create(Registries.RECIPE_TYPE, ElektroCraft.MOD_ID);

  // ===== Resistor Assemble Registers =====
  public static final Supplier<RecipeBookCategory> RESISTOR_ASSEMBLE_CATEGORY =
      CATEGORIES.register("resistor_assemble", RecipeBookCategory::new);

  public static final Supplier<RecipeSerializer<ResistorAssembleRecipe>>
      RESISTOR_ASSEMBLE_SERIALIZER =
          SERIALIZERS.register("resistor_assemble", ResistorAssembleRecipe.Serializer::new);

  public static final Supplier<RecipeType<ResistorAssembleRecipe>> RESISTOR_ASSEMBLE_TYPE =
      TYPES.register(
          "resistor_assemble",
          () ->
              new RecipeType<ResistorAssembleRecipe>() {
                @Override
                public String toString() {
                  return "resistor_assemble";
                }
              });

  // ===== Charger Station Registers =====
  public static final Supplier<RecipeBookCategory> CHARGER_STATION_CATEGORY =
      CATEGORIES.register("charger_station", RecipeBookCategory::new);

  public static final Supplier<RecipeSerializer<ChargerStationRecipe>> CHARGER_STATION_SERIALIZER =
      SERIALIZERS.register("charger_station", ChargerStationRecipe.Serializer::new);

  public static final Supplier<RecipeType<ChargerStationRecipe>> CHARGER_STATION_TYPE =
      TYPES.register(
          "charger_station",
          () ->
              new RecipeType<ChargerStationRecipe>() {
                @Override
                public String toString() {
                  return "charger_station";
                }
              });

  public static void register(IEventBus eventBus) {
    CATEGORIES.register(eventBus);
    SERIALIZERS.register(eventBus);
    TYPES.register(eventBus);
  }
}
