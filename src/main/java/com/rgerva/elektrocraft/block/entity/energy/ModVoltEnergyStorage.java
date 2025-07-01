/**
 * Generic Class: VoltEnergyStorage <T>
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

package com.rgerva.elektrocraft.block.entity.energy;

import net.neoforged.neoforge.energy.IEnergyStorage;

public class ModVoltEnergyStorage implements IEnergyStorage, IVoltEnergy{
    private long energy;
    private final long capacity;
    private final long maxReceive;
    private final long maxExtract;

    public static ModVoltEnergyStorage makeConsumer(long cap, long in) {
        return new ModVoltEnergyStorage(cap, in, 0);
    }

    public static ModVoltEnergyStorage makeGenerator(long cap, long out) {
        return new ModVoltEnergyStorage(cap, 0, out);
    }

    public static ModVoltEnergyStorage makeBattery(long cap, long in, long out) {
        return new ModVoltEnergyStorage(cap, in, out);
    }

    public ModVoltEnergyStorage(long capacity, long maxReceive, long maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public void setEnergy(long energy) {
        this.energy = Math.min(energy, capacity);
    }

    @Override
    public long getStoredFE() {
        return energy;
    }

    @Override
    public long getCapacityFE() {
        return capacity;
    }

    @Override
    public long extractFE(long amount, boolean simulate) {
        long extracted = Math.min(amount, Math.min(maxExtract, energy));
        if (!simulate) energy -= extracted;
        return extracted;
    }

    @Override
    public long receiveFE(long amount, boolean simulate) {
        long receivable = Math.min(amount, Math.min(maxReceive, capacity - energy));
        if (!simulate) energy += receivable;
        return receivable;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return (int) receiveFE(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return (int) extractFE(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return (int) Math.min(energy, Integer.MAX_VALUE);
    }

    @Override
    public int getMaxEnergyStored() {
        return (int) Math.min(capacity, Integer.MAX_VALUE);
    }

    @Override
    public boolean canExtract() {
        return maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return maxReceive > 0;
    }
}
