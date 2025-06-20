/**
 * Generic Class: ModEnergyUtil <T>
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

package com.rgerva.elektrocraft.block.entity.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class ModEnergyUtil {
    public static boolean move(BlockPos from, BlockPos to, int amount, Level level) {
        IEnergyStorage fromStorage = level.getCapability(Capabilities.EnergyStorage.BLOCK, from, null);
        IEnergyStorage toStorage = level.getCapability(Capabilities.EnergyStorage.BLOCK, to, null);

        if (canEnergyStorageExtractThisAmount(fromStorage, amount)) {
            return false;
        }

        if (canEnergyStorageStillReceiveEnergy(toStorage)) {
            return false;
        }

        int maxAmountToReceive = toStorage.receiveEnergy(amount, true);

        int extractedEnergy = fromStorage.extractEnergy(maxAmountToReceive, false);
        toStorage.receiveEnergy(extractedEnergy, false);

        return true;
    }

    private static boolean canEnergyStorageStillReceiveEnergy(IEnergyStorage toStorage) {
        // No more Energy to draw or cannot extract
        return toStorage.getEnergyStored() >= toStorage.getMaxEnergyStored() || !toStorage.canReceive();
    }

    private static boolean canEnergyStorageExtractThisAmount(IEnergyStorage fromStorage, int amount) {
        // No more Space to receive or cannot receive
        return fromStorage.getEnergyStored() <= 0 || fromStorage.getEnergyStored() < amount || !fromStorage.canExtract();
    }

    public static boolean doesBlockHaveEnergyStorage(BlockPos positionToCheck, Level level) {
        return level.getBlockEntity(positionToCheck) != null
                && level.getCapability(Capabilities.EnergyStorage.BLOCK, positionToCheck, null) != null;
    }
}
