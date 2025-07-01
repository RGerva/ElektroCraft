/**
 * Interface: IVoltEnergy
 * Defines the contract for implementations of this type.
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

import com.rgerva.elektrocraft.util.ModUtils;

public interface IVoltEnergy {
    long getStoredFE();
    long getCapacityFE();

    default double getStoredVolts() {
        return ModUtils.ModUnits.toVolts(getStoredFE());
    }

    default double getCapacityVolts() {
        return ModUtils.ModUnits.toVolts(getCapacityFE());
    }

    default boolean consumeVolts(double volts, boolean simulate) {
        long fe = ModUtils.ModUnits.toFE(volts);
        return extractFE(fe, simulate) == fe;
    }

    long extractFE(long fe, boolean simulate);
    long receiveFE(long fe, boolean simulate);
}
