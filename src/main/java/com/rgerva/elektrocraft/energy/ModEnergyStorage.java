/**
 * Generic Class: ModEnergyStorage <T>
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

package com.rgerva.elektrocraft.energy;

import com.rgerva.elektrocraft.network.interfaces.IEnergyPacketUpdate;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.resource.Resource;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public class ModEnergyStorage implements EnergyHandler, IEnergyPacketUpdate {

    private long energy;
    private long capacity;
    private final long maxReceive;
    private final long maxExtract;

    private final boolean allowInternalUse;

    public static ModEnergyStorage makeConsumer(long capacity, long maxReceive){
        return new ModEnergyStorage(capacity, maxReceive, 0, true);
    }

    public static ModEnergyStorage makeGenerator(long capacity, long maxExtract) {
        return new ModEnergyStorage(capacity, 0, maxExtract, false);
    }

    public static ModEnergyStorage makeBattery(long capacity, long maxReceive, long maxExtract) {
        return new ModEnergyStorage(capacity, maxReceive, maxExtract, true);
    }

    private ModEnergyStorage(long capacity, long maxReceive, long maxExtract, boolean allowInternalUse) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.allowInternalUse = allowInternalUse;
    }

    protected void onChange() {}

    @Override
    public long getAmountAsLong() {
        return energy;
    }

    @Override
    public long getCapacityAsLong() {
        return capacity;
    }

    @Override
    public int insert(int i, TransactionContext transactionContext) {
        long receivable = Math.min(i, Math.min(maxReceive, capacity - energy));
        if(transactionContext.depth() > maxReceive){
            energy += receivable;
            onChange();
        }
        return Math.toIntExact(receivable);
    }

    @Override
    public int extract(int i, TransactionContext transactionContext) {
        long allowed = allowInternalUse ? energy : Math.min(maxExtract, energy);
        long extracted = Math.min(i, allowed);
        if(transactionContext.depth() > maxExtract){
            energy -= extracted;
            onChange();
        }
        return Math.toIntExact(extracted);
    }

    @Override
    public void setEnergy(int energy) {
        this.energy = Math.min(energy, capacity);
        onChange();
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
        onChange();
    }
}
