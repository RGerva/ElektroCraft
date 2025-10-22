/**
 * Generic Class: ModAdvancementProvider <T>
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
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider {
    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new Advancements()));
    }

    public static class Advancements implements AdvancementSubProvider {

        @Override
        public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {

            Advancement.Builder.advancement()
                    .display(ModBlocks.SOLAR_PANEL.get(),
                            Component.translatable("block.elektrocraft.solar_panel"),
                            Component.translatable("block.elektrocraft.solar_panel"),
                            ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, "textures/advancement/advancements.png"),
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("inv_changed", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.SOLAR_PANEL.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, "solar_panel"));

            Advancement.Builder.advancement()
                    .display(ModBlocks.CHARGING_STATION.get(),
                            Component.translatable("block.elektrocraft.charging_station"),
                            Component.translatable("block.elektrocraft.charging_station"),
                            ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, "textures/advancement/charging_station"),
                            AdvancementType.TASK, true, true, false)
                    .addCriterion("inv_changed", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.CHARGING_STATION.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, "charging_station"));

        }
    }
}
