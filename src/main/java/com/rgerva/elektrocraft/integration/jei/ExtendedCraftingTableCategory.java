/**
 * Generic Class: ExtendedCraftingTableJEI <T>
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
import com.rgerva.elektrocraft.block.ModBlocks;
import com.rgerva.elektrocraft.recipe.ModRecipes;
import com.rgerva.elektrocraft.recipe.custom.ExtendedCraftingTableRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.stream.Collectors;

public class ExtendedCraftingTableCategory implements IRecipeCategory<RecipeHolder<ExtendedCraftingTableRecipe>> {

    public static final IRecipeHolderType<ExtendedCraftingTableRecipe> TYPE = IRecipeHolderType.create(ModRecipes.EXTENDED_CRAFTING_TABLE_TYPE.get());

    private final IDrawable icon;

    public ExtendedCraftingTableCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.EXTENDED_CRAFTING_STATION.get()));
    }


    @Override
    public IRecipeType<RecipeHolder<ExtendedCraftingTableRecipe>> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.elektrocraft.extended_crafting_station");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, RecipeHolder<ExtendedCraftingTableRecipe> recipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 1, 5).addItemStacks(
                recipe.value().inputItem().items().
                        map(Holder::unwrap).
                        map(registryKeyItemEither -> registryKeyItemEither.map(
                                l -> new ItemStack(Minecraft.getInstance().level.registryAccess().lookupOrThrow(Registries.ITEM).getOrThrow(l)),
                                ItemStack::new
                        )).
                        map(itemStack -> itemStack.copyWithCount((int) recipe.value().inputItem().items().count())).
                        collect(Collectors.toList()));

        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 77, 5).add(recipe.value().output()).
                addRichTooltipCallback((view, tooltip) -> {
                    tooltip.add(Component.translatable("recipes.energizedpower.transfer.output_percentages"));
                });
    }

    @Override
    public void draw(RecipeHolder<ExtendedCraftingTableRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;
        Component component = Component.literal("TICKS: ");
        int textWidth = font.width(component);
        guiGraphics.drawString(Minecraft.getInstance().font, component, 98 - textWidth, 30, 0xFFFFFFFF, false);
    }
}
