/**
 * Generic Class: JEIPlugin <T>
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

package com.rgerva.elektrocraft.integration.jei;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.recipe.ModRecipes;
import com.rgerva.elektrocraft.screen.custom.ExtendedCraftingStationScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeMap;

import java.util.ArrayList;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    public static RecipeMap recipeMap = null;

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ExtendedCraftingTableCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (recipeMap != null) {
            registration.addRecipes(ExtendedCraftingTableCategory.TYPE, new ArrayList<>(recipeMap.byType(ModRecipes.EXTENDED_CRAFTING_TABLE_TYPE.get())));
        }

    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(ExtendedCraftingStationScreen.class, 70, 30, 25, 20,
                ExtendedCraftingTableCategory.TYPE);
    }
}
