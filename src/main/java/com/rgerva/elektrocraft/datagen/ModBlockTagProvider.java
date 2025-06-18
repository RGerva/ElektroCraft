/**
 * Generic Class: ModBlockTagProvider <T>
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
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ElektroCraft.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.Blocks.ORES)
                .add(ModBlocks.LEAD_ORE.get())
                .add(ModBlocks.LEAD_DEEPSLATE_ORE.get())
                .add(ModBlocks.LEAD_NETHER_ORE.get())
                .add(ModBlocks.LEAD_END_ORE.get())

                .add(ModBlocks.TIN_ORE.get())
                .add(ModBlocks.TIN_DEEPSLATE_ORE.get())
                .add(ModBlocks.TIN_NETHER_ORE.get())
                .add(ModBlocks.TIN_END_ORE.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.LEAD_ORE.get())
                .add(ModBlocks.LEAD_DEEPSLATE_ORE.get())
                .add(ModBlocks.LEAD_NETHER_ORE.get())
                .add(ModBlocks.LEAD_END_ORE.get())
                .add(ModBlocks.LEAD_BLOCK.get())
                .add(ModBlocks.LEAD_RAW_BLOCK.get())

                .add(ModBlocks.TIN_ORE.get())
                .add(ModBlocks.TIN_DEEPSLATE_ORE.get())
                .add(ModBlocks.TIN_NETHER_ORE.get())
                .add(ModBlocks.TIN_END_ORE.get())
                .add(ModBlocks.TIN_BLOCK.get())
                .add(ModBlocks.TIN_RAW_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.LEAD_ORE.get())
                .add(ModBlocks.LEAD_DEEPSLATE_ORE.get())
                .add(ModBlocks.LEAD_NETHER_ORE.get())
                .add(ModBlocks.LEAD_BLOCK.get())
                .add(ModBlocks.LEAD_END_ORE.get())
                .add(ModBlocks.LEAD_RAW_BLOCK.get())

                .add(ModBlocks.TIN_ORE.get())
                .add(ModBlocks.TIN_DEEPSLATE_ORE.get())
                .add(ModBlocks.TIN_NETHER_ORE.get())
                .add(ModBlocks.TIN_END_ORE.get())
                .add(ModBlocks.TIN_BLOCK.get())
                .add(ModBlocks.TIN_RAW_BLOCK.get());
    }
}
