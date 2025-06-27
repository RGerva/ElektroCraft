/**
 * Generic Class: ModOresUtils <T>
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

package com.rgerva.elektrocraft.util;

import com.rgerva.elektrocraft.block.ModBlocks;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModOresUtils {
    public record ModBlockVeinProperties(int veinSize, int minY, int maxY, int count) {
    }

    private static final Map<Block, ModBlockVeinProperties> blockPropertiesMap = new HashMap<>();

    public static void addProperties(Block block, int veinSize, int minY, int maxY, int count) {
        ModBlockVeinProperties properties = new ModBlockVeinProperties(veinSize, minY, maxY, count);
        blockPropertiesMap.put(block, properties);
    }

    public static ModBlockVeinProperties getProperties(Block block) {
        return blockPropertiesMap.get(block);
    }

    public static boolean hasProperties(Block block) {
        return blockPropertiesMap.containsKey(block);
    }

    public static void setOresProperties() {
        addProperties(ModBlocks.LEAD_ORE.get(), 24, -64, 56, 3);
        addProperties(ModBlocks.TIN_ORE.get(), 24, -64, 196, 4);
    }

    public static List<Block> getOres() {
        return List.of(
                ModBlocks.LEAD_ORE.get(),
                ModBlocks.TIN_ORE.get());
    }

    public static List<Block> getDeepslateOre() {
        return List.of(
                ModBlocks.LEAD_DEEPSLATE_ORE.get(),
                ModBlocks.TIN_DEEPSLATE_ORE.get());
    }

    public static List<Block> getNetherOre() {
        return List.of(
                ModBlocks.LEAD_NETHER_ORE.get(),
                ModBlocks.TIN_NETHER_ORE.get());
    }

    public static List<Block> getEndOre() {
        return List.of(
                ModBlocks.LEAD_END_ORE.get(),
                ModBlocks.TIN_END_ORE.get());
    }
}
