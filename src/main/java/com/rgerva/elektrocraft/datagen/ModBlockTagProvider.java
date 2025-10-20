/**
 * Generic Class: ModBlockTagProvider <T>
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
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ElektroCraft.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(Tags.Blocks.PLAYER_WORKSTATIONS_CRAFTING_TABLES)
                .add(ModBlocks.EXTENDED_CRAFTING_STATION.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.EXTENDED_CRAFTING_STATION.get())
                .add(ModBlocks.SOLAR_PANEL.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.EXTENDED_CRAFTING_STATION.get())
                .add(ModBlocks.SOLAR_PANEL.get());
    }
}
