/**
 * Generic Class: ChargingStationEntity <T>
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

package com.rgerva.elektrocraft.block_entities.custom.stations;

import com.rgerva.elektrocraft.block_entities.ModBlockEntities;
import com.rgerva.elektrocraft.capabilities.ModCapabilities;
import com.rgerva.elektrocraft.energy.ModEnergy;
import com.rgerva.elektrocraft.screen.menu.stations.ChargingStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import org.jetbrains.annotations.Nullable;

public class ChargingStationEntity extends BlockEntity implements MenuProvider {

    private final ModEnergy modEnergy;
    private final int stationCapacity = 10000;
    private final int stationMaxReceive = 100;

    public int energyClient;
    public int energyProductionClient;

    public ChargingStationEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.CHARGING_STATION_ENTITY.get(), pos, blockState);

        this.modEnergy = ModEnergy.makeConsumer(this.stationCapacity, this.stationMaxReceive, 0);
        this.energyClient = this.energyProductionClient = -1;
    }

    public int getCapacity() {
        return stationCapacity;
    }

    public int getMaxReceive() {
        return stationMaxReceive;
    }

    public ModEnergy getModEnergy() {
        return modEnergy;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.elektrocraft.charging_station");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ChargingStationMenu(i, inventory, this);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState state, ChargingStationEntity entity) {
        if (level.isClientSide()) {
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        modEnergy.serialize(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        modEnergy.deserialize(input);
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter componentGetter) {
        int energy = componentGetter.getOrDefault(ModCapabilities.MOD_ENERGY, 0);
        modEnergy.set(energy);
        super.applyImplicitComponents(componentGetter);
    }

    public @Nullable EnergyHandler getChargingStationBattery(@Nullable Direction direction) {
        return direction != Direction.UP ? modEnergy : null;
    }
}
