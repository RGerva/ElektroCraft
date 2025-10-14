/**
 * Generic Class: ModModelProvider <T>
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

package com.rgerva.elektrocraft.datagen;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.block.ModBlocks;
import com.rgerva.elektrocraft.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;

import java.util.function.BiConsumer;

public class ModModelProvider extends ModelProvider {
    static BlockModelGenerators blockModelGenerator;
    static BiConsumer<ResourceLocation, ModelInstance> modelOutput;
    static ItemModelGenerators itemModel;

    public ModModelProvider(PackOutput output) {
        super(output, ElektroCraft.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        blockModelGenerator = blockModels;
        modelOutput = blockModels.modelOutput;

        itemModel = itemModels;

        registerBlock(blockModels);
        registerItem(itemModels);
    }

    protected void registerItem(ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.HAMMER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CAPACITOR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.DIODE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.INDUCTOR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SILICON.get(), ModelTemplates.FLAT_ITEM);
    }

    protected void registerBlock(BlockModelGenerators blockModels) {
        blockModels.createCraftingTableLike(ModBlocks.EXTENDED_CRAFTING_STATION.get(), Blocks.STONE, TextureMapping::craftingTable);
    }
}
