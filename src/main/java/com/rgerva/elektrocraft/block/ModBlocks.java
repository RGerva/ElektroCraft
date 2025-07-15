/**
 * Generic Class: ModBlocks <T>
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

package com.rgerva.elektrocraft.block;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.block.station.ChargingStationBlock;
import com.rgerva.elektrocraft.block.station.ResistorAssembleBlock;
import com.rgerva.elektrocraft.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ElektroCraft.MOD_ID);

    //region ORES

    public static final DeferredBlock<Block> LEAD_ORE = registerBlock("lead_ore",
            (properties) -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of()
                            .setId(id("lead_ore"))
                            .strength(3.0F)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> LEAD_DEEPSLATE_ORE = registerBlock("lead_deepslate_ore",
            (properties) -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of()
                            .setId(id("lead_deepslate_ore"))
                            .strength(3.0F)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.DEEPSLATE)));

    public static final DeferredBlock<Block> LEAD_NETHER_ORE = registerBlock("lead_nether_ore",
            (properties) -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of()
                            .setId(id("lead_nether_ore"))
                            .strength(3.5F)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.NETHER_ORE)));

    public static final DeferredBlock<Block> LEAD_END_ORE = registerBlock("lead_end_ore",
            (properties) -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of()
                            .setId(id("lead_end_ore"))
                            .strength(3.5F)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.DRIPSTONE_BLOCK)));

    public static final DeferredBlock<Block> LEAD_BLOCK = registerBlock("lead_block",
            (properties) -> new Block(BlockBehaviour.Properties.of()
                    .setId(id("lead_block"))
                    .strength(3.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)));

    public static final DeferredBlock<Block> LEAD_RAW_BLOCK = registerBlock("lead_raw_block",
            (properties) -> new Block(BlockBehaviour.Properties.of()
                    .setId(id("lead_raw_block"))
                    .strength(1f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.GRAVEL)));

    public static final DeferredBlock<Block> TIN_ORE = registerBlock("tin_ore",
            (properties) -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of()
                            .setId(id("tin_ore"))
                            .strength(3.0F)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> TIN_DEEPSLATE_ORE = registerBlock("tin_deepslate_ore",
            (properties) -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of()
                            .setId(id("tin_deepslate_ore"))
                            .strength(3.0F)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.DEEPSLATE)));

    public static final DeferredBlock<Block> TIN_NETHER_ORE = registerBlock("tin_nether_ore",
            (properties) -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of()
                            .setId(id("tin_nether_ore"))
                            .strength(3.5F)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.NETHER_ORE)));

    public static final DeferredBlock<Block> TIN_END_ORE = registerBlock("tin_end_ore",
            (properties) -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of()
                            .setId(id("tin_end_ore"))
                            .strength(3.5F)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.DRIPSTONE_BLOCK)));

    public static final DeferredBlock<Block> TIN_BLOCK = registerBlock("tin_block",
            (properties) -> new Block(BlockBehaviour.Properties.of()
                    .setId(id("tin_block"))
                    .strength(3.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)));

    public static final DeferredBlock<Block> TIN_RAW_BLOCK = registerBlock("tin_raw_block",
            (properties) -> new Block(BlockBehaviour.Properties.of()
                    .setId(id("tin_raw_block"))
                    .strength(1f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.GRAVEL)));

    //endregion

    //region STATIONS

    public static final DeferredBlock<Block> RESISTOR_ASSEMBLE = registerBlock("resistor_assemble",
            (properties) -> new ResistorAssembleBlock(BlockBehaviour.Properties.of()
                    .setId(id("resistor_assemble"))
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.5F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> CHARGING_STATION = registerBlock("charging_station",
            (properties) -> new ChargingStationBlock(BlockBehaviour.Properties.of()
                    .setId(id("charging_station"))
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.5F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()));

    //endregion

    protected static ResourceKey<Block> id(@NotNull String path) {
        return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, path));
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
