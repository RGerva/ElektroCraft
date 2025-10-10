/**
 * Generic Class: ModBlocks <T>
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

package com.rgerva.elektrocraft.block;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.block.custom.stations.ExtendedCraftingStationBlock;
import com.rgerva.elektrocraft.item.ModItems;
import java.util.function.Function;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(ElektroCraft.MOD_ID);

    // region STATION

    public static final DeferredBlock<Block> EXTENDED_CRAFTING_STATION =
            registerBlock("extended_crafting_station", (properties -> new ExtendedCraftingStationBlock(BlockBehaviour.Properties.of()
                    .setId(id("extended_crafting_station"))
                    .strength(4.0F)
                    .requiresCorrectToolForDrops())));

    // endregion


    protected static ResourceKey<Block> id(@NotNull String path) {
        return ResourceKey.create(
                Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, path));
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.registerItem(name, (properties) -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
