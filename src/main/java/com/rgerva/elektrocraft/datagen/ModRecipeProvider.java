/**
 * Generic Class: ModRecipeProvider <T>
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

package com.rgerva.elektrocraft.datagen;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.block.ModBlocks;
import com.rgerva.elektrocraft.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {

        protected Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider provider, @NotNull RecipeOutput recipeOutput) {
            return new ModRecipeProvider(provider, recipeOutput);
        }

        @Override
        public @NotNull String getName() {
            return "My Recipes";
        }
    }

    List<ItemLike> SMELLABLE_LEAD =
            List.of(ModItems.LEAD_RAW.get(),
                    ModBlocks.LEAD_ORE.get(),
                    ModBlocks.LEAD_DEEPSLATE_ORE.get(),
                    ModBlocks.LEAD_NETHER_ORE.get(),
                    ModBlocks.LEAD_END_ORE.get());

    List<ItemLike> SMELLABLE_TIN =
            List.of(ModItems.TIN_RAW.get(),
                    ModBlocks.TIN_ORE.get(),
                    ModBlocks.TIN_DEEPSLATE_ORE.get(),
                    ModBlocks.TIN_NETHER_ORE.get(),
                    ModBlocks.TIN_END_ORE.get());

    @Override
    protected void buildRecipes() {
        oreCook(this.output, SMELLABLE_LEAD, RecipeCategory.MISC, ModItems.LEAD_INGOT.get(), "lead");
        customNuggetRecipe(ModItems.LEAD_NUGGET, ModItems.LEAD_INGOT, "lead");
        customBlockToIngotRecipe(ModBlocks.LEAD_BLOCK, ModItems.LEAD_INGOT, "lead");

        oreCook(this.output, SMELLABLE_TIN, RecipeCategory.MISC, ModItems.TIN_INGOT.get(), "tin");
        customNuggetRecipe(ModItems.TIN_NUGGET, ModItems.TIN_INGOT, "tin");
        customBlockToIngotRecipe(ModBlocks.TIN_BLOCK, ModItems.TIN_INGOT, "tin");

        this.shaped(RecipeCategory.TOOLS, ModItems.HAMMER.get(), 1)
                .define('#', Items.IRON_INGOT)
                .define('@', Items.STICK)
                        .pattern(" # ")
                        .pattern(" @#")
                        .pattern("@  ")
                        .group(getItemName(ModItems.HAMMER.get()))
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .save(this.output, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID,
                                getSimpleRecipeName(ModItems.HAMMER.get()).concat("_from_").concat(getSimpleRecipeName(Items.IRON_INGOT)))));

        customHammerRecipe(ModItems.LEAD_INGOT, ModItems.LEAD_DUST);
        customHammerRecipe(ModItems.TIN_INGOT, ModItems.TIN_DUST);

        customSolderRecipe(ModItems.TIN_DUST, ModItems.LEAD_DUST, ModItems.TIN_SOLDER, "solder");

        oreCook(this.output, List.of(ModItems.TIN_SOLDER.get()), RecipeCategory.MISC, ModItems.TIN_SOLDER_WIRE.get(), "solder");
    }

    protected void customHammerRecipe(ItemLike pInput, ItemLike pOutput) {
        this.shapeless(RecipeCategory.MISC, pOutput, 2)
                .requires(pInput)
                .requires(ModItems.HAMMER.get())
                .group(getItemName(pOutput))
                .unlockedBy(getHasName(pOutput), has(pOutput))
                .save(this.output, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, getSimpleRecipeName(pOutput))));
    }

    protected void customSolderRecipe(ItemLike pInput1, ItemLike pInput2, ItemLike pOutput, String pGroup) {
        this.shapeless(RecipeCategory.MISC, pOutput, 4)
                .requires(pInput1, 2)
                .requires(pInput2, 1)
                .group(pGroup)
                .unlockedBy(getHasName(pOutput), has(pOutput))
                .save(this.output, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID,
                        getSimpleRecipeName(pOutput).concat("_from_").concat(getSimpleRecipeName(pInput1).concat("_and_").concat(getSimpleRecipeName(pInput2))))));
    }


    /**
     * Custom method to generate Nuggets from Ingot Recipe.
     *
     * @param pInput  is main item for recipe
     * @param pOutput is the output from recipe
     * @param pGroup  is group name
     */
    protected void customNuggetRecipe(ItemLike pInput, ItemLike pOutput, String pGroup) {
        this.shapeless(RecipeCategory.MISC, pInput, 9)
                .requires(pOutput)
                .group(pGroup.concat("_nugget"))
                .unlockedBy(getHasName(pOutput), has(pOutput))
                .save(this.output, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, getSimpleRecipeName(pOutput))));

        this.shaped(RecipeCategory.MISC, pOutput)
                .define('#', pInput)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .group(pGroup)
                .unlockedBy(getHasName(pOutput), has(pOutput))
                .save(this.output, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, getSimpleRecipeName(pOutput).concat("_from_").concat(getSimpleRecipeName(pInput)))));
    }

    /**
     * Custom method to generate Ingots and Blocks Recipe.
     *
     * @param pInput  is main item for recipe
     * @param pOutput is the output from recipe
     * @param pGroup  is group name
     */
    protected void customBlockToIngotRecipe(ItemLike pInput, ItemLike pOutput, String pGroup) {
        this.shapeless(RecipeCategory.MISC, pOutput, 9)
                .requires(pInput)
                .group(pGroup)
                .unlockedBy(getHasName(pOutput), has(pOutput))
                .save(this.output, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, getSimpleRecipeName(pOutput).concat("_from_").concat(getSimpleRecipeName(pInput)))));

        this.shaped(RecipeCategory.BUILDING_BLOCKS, pInput)
                .define('#', pOutput)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .group(pGroup)
                .unlockedBy(getHasName(pOutput), has(pOutput))
                .save(this.output, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, getSimpleRecipeName(pInput))));
    }

    /**
     * Custom method to cook materials to output.
     *
     * @param recipeOutput is like this.output
     * @param pIngredients is the list of ingredients
     * @param pCategory the category of output
     * @param pResult is the output of recipe
     * @param pGroup is the group of material
     */
    protected void oreCook(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                0.25f, 200, pGroup, "_from_smelting");

        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                0.25f, 100, pGroup, "_from_blasting");
    }

    protected void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                               float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                               float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, ElektroCraft.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
