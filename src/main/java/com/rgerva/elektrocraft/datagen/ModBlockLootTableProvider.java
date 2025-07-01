/**
 * Generic Class: ModBlockLootTableProvider <T>
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

import com.rgerva.elektrocraft.block.ModBlocks;
import com.rgerva.elektrocraft.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        LootTableOre(ModBlocks.LEAD_ORE.get(), ModItems.LEAD_RAW.get(), 0, 0);
        LootTableOre(ModBlocks.LEAD_DEEPSLATE_ORE.get(), ModItems.LEAD_RAW.get(), 0, 0);
        LootTableOre(ModBlocks.LEAD_NETHER_ORE.get(), ModItems.LEAD_RAW.get(), 2.0F, 3.0F);
        LootTableOre(ModBlocks.LEAD_END_ORE.get(), ModItems.LEAD_RAW.get(), 2.0F, 3.0F);

        dropSelf(ModBlocks.LEAD_BLOCK.get());
        dropSelf(ModBlocks.LEAD_RAW_BLOCK.get());

        LootTableOre(ModBlocks.TIN_ORE.get(), ModItems.TIN_RAW.get(), 0, 0);
        LootTableOre(ModBlocks.TIN_DEEPSLATE_ORE.get(), ModItems.TIN_RAW.get(), 0, 0);
        LootTableOre(ModBlocks.TIN_NETHER_ORE.get(), ModItems.TIN_RAW.get(), 2.0F, 3.0F);
        LootTableOre(ModBlocks.TIN_END_ORE.get(), ModItems.TIN_RAW.get(), 2.0F, 3.0F);

        dropSelf(ModBlocks.TIN_BLOCK.get());
        dropSelf(ModBlocks.TIN_RAW_BLOCK.get());
        dropSelf(ModBlocks.RESISTOR_ASSEMBLE.get());
        dropSelf(ModBlocks.CHARGER_STATION.get());
    }

    protected void LootTableOre(Block pInput, Item pOutput, float minDrops, float maxDrops) {
        if (minDrops == 0 && maxDrops == 0) {
            add(pInput,
                    block -> createOreDrop(pInput, pOutput));
        } else {
            add(pInput,
                    block -> createMultipleOreDrops(pInput, pOutput, minDrops, maxDrops));

        }
    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
