/**
 * Generic Class: ModItemTagProvider <T>
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
import com.rgerva.elektrocraft.item.ModItems;
import com.rgerva.elektrocraft.util.ModTags;
import com.rgerva.elektrocraft.util.ModUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {

    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ElektroCraft.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.Items.WIRE_ITEMS)
                .add(ModItems.TIN_SOLDER_WIRE.get());

        this.tag(ModTags.Items.RESISTORS)
                .add(ModItems.BLANK_RESISTOR.get())
                .add(ModItems.RESISTOR.get());

        this.tag(ModTags.Items.VOLTAGE)
                .add(ModItems.DIODE.get());

        this.tag(ModTags.Items.CURRENT);

        this.tag(ModTags.Items.CAPACITANCE)
                .add(ModItems.BLANK_CAPACITOR.get())
                .add(ModItems.CAPACITOR.get());

        for (Item item : ModUtils.ModCapacitanceUtil.DIELECTRIC_CONSTANTS.keySet()) {
            this.tag(ModTags.Items.DIELECTRIC_CONSTANTS)
                    .add(item);
        }
    }
}
