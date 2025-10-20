/**
 * Generic Class: SolarPanelBlockEntity <T>
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

package com.rgerva.elektrocraft.block_entities.custom.generator;

import com.rgerva.elektrocraft.block_entities.ModBlockEntities;
import com.rgerva.elektrocraft.capabilities.ModCapabilities;
import com.rgerva.elektrocraft.energy.ModEnergy;
import com.rgerva.elektrocraft.network.packages.UpdateSolarPanelPackage;
import com.rgerva.elektrocraft.screen.menu.generator.SolarPanelMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SolarPanelBlockEntity extends BlockEntity implements MenuProvider {

    private final int multiplier = 2;
    private final int solarMaxTransfer = 100;
    private final ModEnergy modEnergy;
    private final int solarCapacity = 1000;
    public int energyClient;
    public int energyProductionClient;

    public SolarPanelBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SOLAR_PANEL_ENTITY.get(), pos, blockState);

        this.modEnergy = ModEnergy.makeGenerator(this.solarCapacity, this.solarMaxTransfer, 0);
        this.energyClient = this.energyProductionClient = -1;
    }

    public int getCapacity() {
        return solarCapacity;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.elektrocraft.solar_panel");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new SolarPanelMenu(i, inventory, this);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, SolarPanelBlockEntity entity) {
        if (level.isClientSide()) {
            return;
        }

//        if(entity.modEnergy.isFullEnergy()) {
//            entity.modEnergy.setEnergy(0);
//        }

        int energyProducedBySun = entity.currentAmountEnergyProduced(level);
        entity.modEnergy.generatePower(energyProducedBySun);
        entity.sendEnergy();
        int energyStored = entity.modEnergy.getAmountAsInt();

        if (entity.energyClient != energyStored || entity.energyProductionClient != energyProducedBySun) {
            int energyProduced = entity.modEnergy.isFullEnergy() ? 0 : energyProducedBySun;
            UpdateSolarPanelPackage msg = new UpdateSolarPanelPackage(blockPos, energyStored, energyProduced);
            PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) level, level.getChunk(blockPos).getPos(), msg);
        }
    }

    private void sendEnergy() {
        int capacity = modEnergy.getAmountAsInt();

        for (int i = 0; (i < Direction.values().length) && capacity > 0; i++) {
            Direction facing = Direction.values()[i];
            if (facing.equals(Direction.UP)) {
                continue;
            }

            assert level != null;
            EnergyHandler handler = level.getCapability(Capabilities.Energy.BLOCK, worldPosition.relative(facing), facing.getOpposite());
            if (handler == null) {
                continue;
            }

            try (Transaction tx = Transaction.openRoot()) {
                int energyInserted = handler.insert(Math.min(capacity, solarMaxTransfer), tx);
                if (energyInserted == 0) {
                    continue;
                }

                capacity -= energyInserted;
                modEnergy.consumePower(energyInserted);
                setChanged();
                tx.commit();
            }
        }
    }

    private int currentAmountEnergyProduced(Level level) {
        return (int) (multiplier * computeSunIntensity(level, worldPosition));
    }

    public static float computeSunIntensity(Level level, BlockPos pos) {
        float sunIntensity = 0;

        if (level.canSeeSkyFromBelowWater(pos)) {
            float multiplicator = 1.5f;
            float displacement = 1.2f;

            float celestialAngleRadians = level.getSunAngle(1.0f);
            if (celestialAngleRadians > Math.PI) {
                celestialAngleRadians = (2 * 3.141592f - celestialAngleRadians);
            }

            sunIntensity = multiplicator * Mth.cos(celestialAngleRadians / displacement);
            sunIntensity = Math.max(0, sunIntensity);
            sunIntensity = Math.min(1, sunIntensity);

            if (sunIntensity > 0) {
                if (sunIntensity < 1) {
                    sunIntensity = 1;
                }

                if (level.isRaining()) {
                    sunIntensity *= 0.4F;
                }

                if (level.isThundering()) {
                    sunIntensity *= 0.2F;
                }
            }
        }

        return sunIntensity;
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

    public @Nullable EnergyHandler getSolarPanelBattery(@Nullable Direction direction) {
        return direction != Direction.UP ? modEnergy : null;
    }
}
