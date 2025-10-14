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
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public class ModEnergyStorage extends SimpleEnergyHandler implements IEnergyPacketUpdate {

    private int energy;
    private int capacity;
    private final int maxReceive;
    private final int maxExtract;

    private final boolean allowInternalUse;

    public static ModEnergyStorage makeConsumer(int capacity, int maxReceive){
        return new ModEnergyStorage(capacity, maxReceive, 0, true);
    }

    public static ModEnergyStorage makeGenerator(int capacity, int maxExtract) {
        return new ModEnergyStorage(capacity, 0, maxExtract, false);
    }

    public static ModEnergyStorage makeBattery(int capacity, int maxReceive, int maxExtract) {
        return new ModEnergyStorage(capacity, maxReceive, maxExtract, true);
    }

    public ModEnergyStorage(int capacity, int maxInsert, int maxExtract, boolean allowInternalUse) {
        super(capacity, maxInsert, maxExtract);
        this.capacity = capacity;
        this.maxReceive = maxInsert;
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
