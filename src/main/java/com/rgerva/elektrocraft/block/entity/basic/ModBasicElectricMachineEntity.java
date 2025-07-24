/**
 * Generic Class: ModBasicElectricMachine <T>
 * A generic structure that works with type parameters.
 * <p>
 * Created by: D56V1OK
 * On: 2025/jul.
 * <p>
 * GitHub: https://github.com/RGerva
 * <p>
 * Copyright (c) 2025 @RGerva. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.rgerva.elektrocraft.block.entity.basic;

import com.rgerva.elektrocraft.block.entity.basic.energy.ModVoltEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ModBasicElectricMachine extends BlockEntity {
    protected final double requiredVoltage;
    protected final double requiredCurrent;

    protected final ModVoltEnergyStorage energy;

    public ModBasicElectricMachine(BlockEntityType<?> type, BlockPos pos, BlockState state,
                                         double requiredVoltage, double requiredCurrent) {
        super(type, pos, state);
        this.requiredVoltage = requiredVoltage;
        this.requiredCurrent = requiredCurrent;
        this.energy = createEnergyStorage();
    }

    protected abstract ModVoltEnergyStorage createEnergyStorage();

    public double getRequiredVoltage() {
        return requiredVoltage;
    }

    public double getRequiredCurrent() {
        return requiredCurrent;
    }

    public long getRequiredFEPerTick() {
        return ModUnits.toFE(requiredVoltage * requiredCurrent); // P = V × I
    }

    public ModVoltEnergyStorage getEnergy() {
        return energy;
    }

    public boolean isPoweredEnough() {
        return energy.getEnergyStored() >= getRequiredFEPerTick();
    }

    public double getEfficiencyFactor() {
        return Math.min(1.0, energy.getEnergyStored() / (double) getRequiredFEPerTick());
    }

    public ElectricShockRisk getShockRisk() {
        return ElectricShockRisk.fromVoltage(requiredVoltage);
    }

    public int getSignalStrength() {
        return (int) Math.floor(15.0 * energy.getStoredFE() / energy.getCapacityFE());
    }

}
