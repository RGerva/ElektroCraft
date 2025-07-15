/**
 * Generic Class: ModBasicWorkerEntity <T>
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
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class ModBasicCrafterEntity<W> extends BlockEntity {

    protected final int baseEnergyConsumptionPerTick;
    protected final int baseWorkDuration;

    protected int progress;
    protected int maxProgress;
    protected int energyConsumptionLeft = -1;
    protected boolean hasEnoughEnergy;

    protected final ModVoltEnergyStorage energy;
    protected final ContainerData data;

    public ModBasicCrafterEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState,
                                 int baseWorkDuration, int baseEnergyConsumptionPerTick) {

        super(type, pos, blockState);

        this.baseEnergyConsumptionPerTick = baseEnergyConsumptionPerTick;
        this.baseWorkDuration = baseWorkDuration;
        this.energy = makeConsumer();
        this.data = initContainerData();
    }

    protected abstract ModVoltEnergyStorage makeConsumer();

    public @Nullable IEnergyStorage getEnergyStorageCapability(@Nullable Direction side) {
        return energy;
    }

    public ContainerData initContainerData(){
        return new SimpleContainerData(0);
    }

    public int getSignalStrength(){
        return (int) Math.floor(15.0 * energy.getStoredFE() / energy.getCapacityFE());
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        this.energy.setEnergy(valueInput.getIntOr(this.getNameForReporting() + ".energy", 0));
        this.progress = valueInput.getIntOr( this.getNameForReporting() + ".recipe.progress", 0);
        this.maxProgress = valueInput.getIntOr(this.getNameForReporting() + ".recipe.max_progress", 100);
        this.energyConsumptionLeft = valueInput.getIntOr(this.getNameForReporting() + ".recipe.energy_consumption_left", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        valueOutput.putInt(this.getNameForReporting() + ".energy", energy.getEnergyStored());
        valueOutput.putInt(this.getNameForReporting() + ".recipe.progress", progress);
        valueOutput.putInt(this.getNameForReporting() + ".recipe.max_progress", maxProgress);
        valueOutput.putInt(this.getNameForReporting() + ".recipe.energy_consumption_left", energyConsumptionLeft);
        super.saveAdditional(valueOutput);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public void onDataPacket(Connection net, ValueInput valueInput) {
        super.onDataPacket(net, valueInput);
        this.loadAdditional(valueInput);
    }

    public static <W> void tick(Level level, BlockPos blockPos, BlockState state, ModBasicCrafterEntity<W> blockEntity) {
        if(level.isClientSide) return;

        blockEntity.energy.setEnergy(blockEntity.energy.getEnergyStored() + 200);

        blockEntity.onTickStart();
        if(blockEntity.hasWork()) {
            Optional<W> workData = blockEntity.getCurrentWorkData();
            if(workData.isEmpty()) {
                blockEntity.onTickEnd();
                return;
            }

            if(blockEntity.maxProgress == 0) {
                blockEntity.onWorkStarted(workData.get());
                blockEntity.maxProgress = blockEntity.getWorkDurationFor(workData.get());
            }

            int energyConsumptionPerTick = blockEntity.getEnergyConsumptionFor(workData.get());

            if(blockEntity.energyConsumptionLeft < 0) {
                blockEntity.energyConsumptionLeft = energyConsumptionPerTick * blockEntity.maxProgress;
            }

            if(energyConsumptionPerTick <= blockEntity.energy.getEnergyStored()) {
                blockEntity.hasEnoughEnergy = true;
                blockEntity.onHasEnoughEnergy();

                if(blockEntity.progress < 0 || blockEntity.maxProgress < 0 || blockEntity.energyConsumptionLeft < 0) {
                    blockEntity.resetProgress();
                    setChanged(level, blockPos, state);
                    blockEntity.onTickEnd();
                    return;
                }

                blockEntity.energy.setEnergy(blockEntity.energy.getEnergyStored() - energyConsumptionPerTick);
                blockEntity.energyConsumptionLeft -= energyConsumptionPerTick;

                blockEntity.progress++;
                if(blockEntity.progress >= blockEntity.maxProgress){
                    blockEntity.onWorkCompleted(workData.get());
                }

                setChanged(level, blockPos, state);

            }else {
                blockEntity.hasEnoughEnergy = false;
                blockEntity.onHasNotEnoughEnergy();
                setChanged(level, blockPos, state);
            }

        }else{
            blockEntity.resetProgress();
            blockEntity.onHasNotEnoughEnergy();
            setChanged(level, blockPos, state);
        }
        blockEntity.onTickEnd();
    }

    protected void onTickStart() {}

    protected void onTickEnd() {}

    protected void onHasEnoughEnergy() {}

    protected void onHasNotEnoughEnergy() {}

    protected final int getWorkDurationFor(W workData) {
        return Math.max(1, (int)Math.ceil(baseWorkDuration * getWorkDataDependentWorkDuration(workData)));
    }

    protected final int getEnergyConsumptionFor(W workData) {
        return Math.max(1, (int)Math.ceil(baseEnergyConsumptionPerTick *
                getWorkDataDependentEnergyConsumption(workData)));
    }

    protected double getWorkDataDependentWorkDuration(W workData) {
        return 1;
    }

    protected double getWorkDataDependentEnergyConsumption(W workData) {
        return 1;
    }

    protected abstract boolean hasWork();

    protected abstract Optional<W> getCurrentWorkData();

    protected abstract void onWorkStarted(W workData);

    protected abstract void onWorkCompleted(W workData);

    protected void resetProgress() {
        progress = 0;
        maxProgress = 0;
        energyConsumptionLeft = -1;
        hasEnoughEnergy = false;
    }
}
