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
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;

public class ModModelProvider extends ModelProvider {

    static BlockModelGenerators blockModelGenerator;
    static BiConsumer<ResourceLocation, ModelInstance> modelOutput;

    public ModModelProvider(PackOutput output) {
        super(output, ElektroCraft.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        blockModelGenerator = blockModels;
        modelOutput = blockModels.modelOutput;

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

        itemModels.generateFlatItem(ModItems.BLANK_RESISTOR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RESISTOR.get(), ModelTemplates.FLAT_ITEM);
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

        horizontalBlockWithItem(ModBlocks.RESISTOR_ASSEMBLE, false);
    }

    private void horizontalBlockWithItem(Holder<Block> block, boolean uniqueBottomTexture) {
        ResourceLocation model = TexturedModel.createDefault(unused -> new TextureMapping().
                        put(TextureSlot.UP, TextureMapping.getBlockTexture(block.value(), "_top")).
                        put(TextureSlot.DOWN, TextureMapping.getBlockTexture(block.value(), uniqueBottomTexture?"_bottom":"_top")).
                        put(TextureSlot.NORTH, TextureMapping.getBlockTexture(block.value(), "_side")).
                        put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(block.value(), "_side")).
                        put(TextureSlot.EAST, TextureMapping.getBlockTexture(block.value(), "_side")).
                        put(TextureSlot.WEST, TextureMapping.getBlockTexture(block.value(), "_side")).
                        copySlot(TextureSlot.UP, TextureSlot.PARTICLE),
                ModelTemplates.CUBE).get(block.value()).create(block.value(), modelOutput);

        blockModelGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block.value(),
                new MultiVariant(WeightedList.of(new Variant(model)))));

        blockModelGenerator.registerSimpleItemModel(block.value(), model);
    }
}
