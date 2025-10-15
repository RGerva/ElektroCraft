/**
 * Generic Class: ModRecipes <T>
 * A generic structure that works with type parameters.
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

package com.rgerva.elektrocraft.recipe;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.recipe.custom.ExtendedCraftingTableRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {

    public static final DeferredRegister<RecipeBookCategory> CATEGORIES =
            DeferredRegister.create(BuiltInRegistries.RECIPE_BOOK_CATEGORY, ElektroCraft.MOD_ID);

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, ElektroCraft.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, ElektroCraft.MOD_ID);


    public static final Supplier<RecipeBookCategory> EXTENDED_CRAFTING_TABLE_CATEGORY =
            CATEGORIES.register("extended_crafting_table", RecipeBookCategory::new);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ExtendedCraftingTableRecipe>> EXTENDED_CRAFTING_TABLE_SERIALIZER =
            SERIALIZERS.register("extended_crafting_table", ExtendedCraftingTableRecipe.Serializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<ExtendedCraftingTableRecipe>> EXTENDED_CRAFTING_TABLE_TYPE =
            TYPES.register("extended_crafting_table", () -> new RecipeType<ExtendedCraftingTableRecipe>() {
                @Override
                public String toString() {
                    return "extended_crafting_table";
                }
            });

    public static void register(IEventBus eventBus) {
        CATEGORIES.register(eventBus);
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
