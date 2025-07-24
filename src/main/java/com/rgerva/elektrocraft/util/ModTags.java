/**
 * Generic Class: ModTags <T>
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

import com.rgerva.elektrocraft.ElektroCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> CONDUCTIVE = createTag("conductive_blocks");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> WIRE_ITEMS = createTag("wire_items");
        public static final TagKey<Item> RESISTORS = createTag("resistor_items");
        public static final TagKey<Item> VOLTAGE = createTag("voltage_items");
        public static final TagKey<Item> CURRENT = createTag("current_items");
        public static final TagKey<Item> CAPACITANCE = createTag("capacitance_items");
        public static final TagKey<Item> DIELECTRIC_CONSTANTS = createTag("dielectric_items");
        public static final TagKey<Item> INSULATOR = createTag("insulator_items");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, name));
        }
    }
}
