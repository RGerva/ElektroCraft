/**
 * Generic Class: ModBlockEntities <T>
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

package com.rgerva.elektrocraft.block.entity;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.block.ModBlocks;
import com.rgerva.elektrocraft.block.entity.station.ChargerStationEntity;
import com.rgerva.elektrocraft.block.entity.station.ResistorAssembleEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ElektroCraft.MOD_ID);

    public static final Supplier<BlockEntityType<ResistorAssembleEntity>> RESISTOR_ASSEMBLE_ENTITY = BLOCK_ENTITIES.register("resistor_assemble",
            () -> new BlockEntityType<>(ResistorAssembleEntity::new, ModBlocks.RESISTOR_ASSEMBLE.get()));

    public static final Supplier<BlockEntityType<ChargerStationEntity>> CHARGER_STATION_ENTITY = BLOCK_ENTITIES.register("charger_station",
            () -> new BlockEntityType<>(ChargerStationEntity::new, ModBlocks.CHARGER_STATION.get()));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
