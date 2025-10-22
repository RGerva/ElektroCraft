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
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

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

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(ModBlocks.SOLAR_PANEL.get(),
                BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(ModBlocks.SOLAR_PANEL.get()))));

        horizontalBlockWithItem(ModBlocks.CHARGING_STATION, true, true);
    }

    private void horizontalBlockWithItem(Holder<Block> blocksHolder, boolean uniqueBottomTexture, boolean uniqueFrontTexture) {
        ResourceLocation model = TexturedModel.createDefault(unused -> new TextureMapping()
                        .put(TextureSlot.TOP, TextureMapping.getBlockTexture(blocksHolder.value(), "_top"))
                        .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(blocksHolder.value(), uniqueBottomTexture ? "_bottom" : "_top"))
                        .put(TextureSlot.FRONT, TextureMapping.getBlockTexture(blocksHolder.value(), uniqueFrontTexture ? "_front" : "_side"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(blocksHolder.value(), "_side"))
                        .copySlot(TextureSlot.TOP, TextureSlot.PARTICLE), ModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM)
                .get(blocksHolder.value())
                .create(blocksHolder.value(), modelOutput);

        if (uniqueFrontTexture) {
            blockModelGenerator.blockStateOutput.accept(
                    MultiVariantGenerator.dispatch(blocksHolder.value(), new MultiVariant(WeightedList.of(new Variant(model))))
                            .with(PropertyDispatch.modify(BlockStateProperties.HORIZONTAL_FACING)
                                    .select(Direction.NORTH, BlockModelGenerators.NOP)
                                    .select(Direction.SOUTH, BlockModelGenerators.Y_ROT_180)
                                    .select(Direction.EAST, BlockModelGenerators.Y_ROT_90)
                                    .select(Direction.WEST, BlockModelGenerators.Y_ROT_270)));
        } else {
            blockModelGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(
                    blocksHolder.value(), new MultiVariant(WeightedList.of(new Variant(model)))));
        }
    }
}
