/**
 * Generic Class: ModBasicElectricMachineEntity <T> A generic structure that works with type
 * parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jul.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.block.entity.basic;

import com.rgerva.elektrocraft.block.entity.basic.energy.ModVoltEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public abstract class ModBasicElectricMachineEntity<W> extends BlockEntity {
  protected final double requiredVoltage;
  protected final double requiredCurrent;

  protected final ModVoltEnergyStorage energy;
  protected int energyConsumptionLeft = -1;
  protected boolean hasEnoughEnergy;

  public ModBasicElectricMachineEntity(
      BlockEntityType<?> type,
      BlockPos pos,
      BlockState state,
      double requiredVoltage,
      double requiredCurrent) {
    super(type, pos, state);
    this.requiredVoltage = requiredVoltage;
    this.requiredCurrent = requiredCurrent;
    this.energy = createEnergyStorage();
  }

  protected abstract ModVoltEnergyStorage createEnergyStorage();

  public @Nullable IEnergyStorage getEnergyStorageCapability(@Nullable Direction side) {
    return energy;
  }

  public int getSignalStrength() {
    return (int) Math.floor(15.0 * energy.getStoredFE() / energy.getCapacityFE());
  }

  @Override
  protected void loadAdditional(ValueInput valueInput) {
    super.loadAdditional(valueInput);
    this.energy.setEnergy(valueInput.getIntOr(this.getNameForReporting() + ".energy", 0));
    this.energyConsumptionLeft =
        valueInput.getIntOr(this.getNameForReporting() + ".recipe.energy_consumption_left", 0);
  }

  @Override
  protected void saveAdditional(ValueOutput valueOutput) {
    valueOutput.putInt(this.getNameForReporting() + ".energy", energy.getEnergyStored());
    valueOutput.putInt(
        this.getNameForReporting() + ".recipe.energy_consumption_left", energyConsumptionLeft);
    super.saveAdditional(valueOutput);
  }

  protected void restEnergyProgress() {
    energyConsumptionLeft = -1;
    hasEnoughEnergy = false;
  }
}
