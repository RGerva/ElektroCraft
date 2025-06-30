/**
 * Generic Class: ModCreativeTab <T>
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

package com.rgerva.elektrocraft.creative;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.block.ModBlocks;
import com.rgerva.elektrocraft.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ElektroCraft.MOD_ID);

    public static final Supplier<CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TAB.register("tab_elektrocraft",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.HAMMER.get()))
                    .title(Component.translatable("itemGroup.elektrocraft"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.LEAD_ORE.get());
                        output.accept(ModBlocks.LEAD_DEEPSLATE_ORE.get());
                        output.accept(ModBlocks.LEAD_NETHER_ORE.get());
                        output.accept(ModBlocks.LEAD_END_ORE.get());
                        output.accept(ModItems.LEAD_RAW.get());
                        output.accept(ModBlocks.LEAD_RAW_BLOCK.get());
                        output.accept(ModBlocks.LEAD_BLOCK.get());
                        output.accept(ModItems.LEAD_INGOT.get());
                        output.accept(ModItems.LEAD_NUGGET.get());
                        output.accept(ModItems.LEAD_DUST.get());

                        output.accept(ModBlocks.TIN_ORE.get());
                        output.accept(ModBlocks.TIN_DEEPSLATE_ORE.get());
                        output.accept(ModBlocks.TIN_NETHER_ORE.get());
                        output.accept(ModBlocks.TIN_END_ORE.get());
                        output.accept(ModItems.TIN_RAW.get());
                        output.accept(ModBlocks.TIN_RAW_BLOCK.get());
                        output.accept(ModBlocks.TIN_BLOCK.get());
                        output.accept(ModItems.TIN_INGOT.get());
                        output.accept(ModItems.TIN_NUGGET.get());
                        output.accept(ModItems.TIN_DUST.get());

                        output.accept(ModItems.TIN_SOLDER.get());
                        output.accept(ModItems.TIN_SOLDER_WIRE.get());
                        output.accept(ModItems.HAMMER.get());
                        output.accept(ModItems.BLANK_RESISTOR.get());
                        output.accept(ModItems.RESISTOR.get());

                        output.accept(ModBlocks.RESISTOR_ASSEMBLE.get());

                        output.accept(ModItems.SILICON.get());
                        output.accept(ModItems.DIODE.get());

                        output.accept(ModItems.CAPACITOR.get());

                    }))
                    .build());

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
