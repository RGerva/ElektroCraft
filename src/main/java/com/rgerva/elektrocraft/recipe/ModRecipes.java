/**
 * Generic Class: ModRecipes <T>
 * A generic structure that works with type parameters.
 * <p>
 * Created by: D56V1OK
 * On: 2025/jun.
 * <p>
 * GitHub: https://github.com/RGerva
 * <p>
 * Copyright (c) 2025 @RGerva. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.rgerva.elektrocraft.recipe;

import com.rgerva.elektrocraft.ElektroCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, ElektroCraft.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, ElektroCraft.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HammerRecipe>> HAMMER_SERIALIZER =
            SERIALIZERS.register("hammer", HammerRecipe.Serializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<HammerRecipe>> HAMMER_TYPE =
            TYPES.register("hammer", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "elektrocraft:hammer";
                }
    });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
