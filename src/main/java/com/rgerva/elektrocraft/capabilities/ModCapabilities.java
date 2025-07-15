/**
 * Generic Class: ModCapabilities <T>
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

package com.rgerva.elektrocraft.capabilities;

import com.rgerva.elektrocraft.block.entity.ModBlockEntities;
import com.rgerva.elektrocraft.block.entity.station.ChargingStationEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.Optional;

public class ModCapabilities {
    public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.CHARGING_STATION_ENTITY.get(),
                ChargingStationEntity::getEnergyStorageCapability);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.CHARGING_STATION_ENTITY.get(),
                ChargingStationEntity::getItemHandlerCapability);
    }

    public static Optional<IItemHandler> getCapabilityItemHandler(Level level, BlockEntity blockEntity) {
        return Optional.ofNullable(level.getCapability(Capabilities.ItemHandler.BLOCK, blockEntity.getBlockPos(),
                blockEntity.getBlockState(), blockEntity, null));
    }
}
