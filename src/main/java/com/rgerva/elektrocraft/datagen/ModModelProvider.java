/**
 * Generic Class: ModModelProvider <T>
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
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, ElektroCraft.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        registerBlock(blockModels);
        registerItem(itemModels);
    }

    protected void registerItem(ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.LEAD_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LEAD_NUGGET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LEAD_RAW.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LEAD_DUST.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.TIN_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TIN_NUGGET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TIN_RAW.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TIN_DUST.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.TIN_SOLDER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TIN_SOLDER_WIRE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.HAMMER.get(), ModelTemplates.FLAT_ITEM);
    }

    protected void registerBlock(BlockModelGenerators blockModels) {
        blockModels.createTrivialCube(ModBlocks.LEAD_ORE.get());
        blockModels.createTrivialCube(ModBlocks.LEAD_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.LEAD_DEEPSLATE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.LEAD_NETHER_ORE.get());
        blockModels.createTrivialCube(ModBlocks.LEAD_END_ORE.get());
        blockModels.createTrivialCube(ModBlocks.LEAD_RAW_BLOCK.get());

        blockModels.createTrivialCube(ModBlocks.TIN_ORE.get());
        blockModels.createTrivialCube(ModBlocks.TIN_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.TIN_DEEPSLATE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.TIN_NETHER_ORE.get());
        blockModels.createTrivialCube(ModBlocks.TIN_END_ORE.get());
        blockModels.createTrivialCube(ModBlocks.TIN_RAW_BLOCK.get());
    }
}
