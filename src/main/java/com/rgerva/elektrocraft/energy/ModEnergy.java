/**
 * Generic Class: ModEnergy <T>
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

import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;

public class ModEnergy extends SimpleEnergyHandler {

    public static ModEnergy makeConsumer(int capacity, int maxReceive, int energyInside) {
        return new ModEnergy(capacity, maxReceive, 0, energyInside);
    }

    public static ModEnergy makeGenerator(int capacity, int maxExtract, int energyInside) {
        return new ModEnergy(capacity, 0, maxExtract, energyInside);
    }

    public static ModEnergy makeBattery(int capacity, int maxReceive, int maxExtract, int energyInside) {
        return new ModEnergy(capacity, maxReceive, maxExtract, energyInside);
    }

    private ModEnergy(int capacity, int maxInsert, int maxExtract, int energy) {
        super(capacity, maxInsert, maxExtract, energy);
    }

    public void setEnergy(int energy) {
        this.energy = Math.min(energy, capacity);
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void generatePower(int energy) {
        this.set(Math.min(capacity, this.energy + energy));
    }

    public void consumePower(int energy) {
        this.set(Math.max(0, this.energy - energy));
    }

    public boolean isFullEnergy() {
        return this.energy >= this.capacity;
    }

}
