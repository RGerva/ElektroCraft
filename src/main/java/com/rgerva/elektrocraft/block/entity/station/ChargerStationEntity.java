/**
 * Generic Class: ChargerStationEntity <T>
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

package com.rgerva.elektrocraft.block.entity.station;

import com.rgerva.elektrocraft.block.entity.ModBlockEntities;
import com.rgerva.elektrocraft.block.entity.energy.ModVoltEnergyStorage;
import com.rgerva.elektrocraft.component.ModDataComponents;
import com.rgerva.elektrocraft.gui.menu.ChargerStationMenu;
import com.rgerva.elektrocraft.item.ModItems;
import com.rgerva.elektrocraft.util.ModTags;
import com.rgerva.elektrocraft.util.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ChargerStationEntity extends BlockEntity implements MenuProvider {
    public boolean SPARKS = false;
    private final ModVoltEnergyStorage energy = ModVoltEnergyStorage.makeConsumer(100_000L, 512L);
    public static final double VOLTAGE_USAGE = 5.0;

    public static final int BLANK_CAPACITOR_SLOT = 0;
    public static final int METALLIC_SLOT = 1;
    public static final int CONDUCTOR_WIRE_SLOT = 2;
    public static final int ACTIVE_POWER_SLOT = 3;
    public static final int OUTPUT_SLOT = 4;

    private int progress = 0;
    private int maxProgress = 100;

    public ChargerStationEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.CHARGER_STATION_ENTITY.get(), pos, blockState);
    }

    private final ItemStackHandler inventory = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            if (!Objects.requireNonNull(level).isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (slot == BLANK_CAPACITOR_SLOT) {
                return stack.is(ModItems.BLANK_CAPACITOR.get());
            }

            if (slot == ACTIVE_POWER_SLOT) {
                return stack.is(ModTags.Items.DIELECTRIC_CONSTANTS);
            }

            return slot != OUTPUT_SLOT;
        }
    };

    public final ContainerData data = new ContainerData() {

        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> ChargerStationEntity.this.progress;
                case 1 -> ChargerStationEntity.this.maxProgress;
                case 2 -> (int) Math.floor(getStoredVolts());
                case 3 -> (int) Math.floor(getCapacityVolts());
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> ChargerStationEntity.this.progress = value;
                case 1 -> ChargerStationEntity.this.maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.elektrocraft.charger_station");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ChargerStationMenu(i, inventory, this);
    }

    public int getSignalStrength(){
        return (int) Math.floor(15.0 * energy.getStoredFE() / energy.getCapacityFE());
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, ChargerStationEntity entity) {
        if (level.isClientSide) return;

        if (entity.hasValidRecipe()) {
            entity.maxProgress = entity.computeMaxProgress();

            if (entity.energy.consumeVolts(VOLTAGE_USAGE, true)) {
                entity.energy.consumeVolts(VOLTAGE_USAGE, false);
                entity.progress++;

                if (entity.progress >= entity.maxProgress) {
                    entity.craftCapacitor();
                    entity.progress = 0;
                }

                entity.SPARKS = true;
                entity.setChanged();
            } else {
                entity.SPARKS = false;
            }
        } else {
            entity.progress = 0;
            entity.SPARKS = false;
        }

    }

    public boolean hasValidRecipe() {
        ItemStack blank = inventory.getStackInSlot(BLANK_CAPACITOR_SLOT);
        ItemStack metal = inventory.getStackInSlot(METALLIC_SLOT);
        ItemStack wire = inventory.getStackInSlot(CONDUCTOR_WIRE_SLOT);
        ItemStack dielectric = inventory.getStackInSlot(ACTIVE_POWER_SLOT);
        ItemStack output = inventory.getStackInSlot(OUTPUT_SLOT);

        boolean hasBlank = blank.is(ModItems.BLANK_CAPACITOR.get());
        boolean hasMetal = !metal.isEmpty();
        boolean hasWire = !wire.isEmpty();
        boolean hasDielectric = dielectric.is(ModTags.Items.DIELECTRIC_CONSTANTS);
        boolean outputEmpty = output.isEmpty();
        boolean hasEnergy = energy.getStoredVolts() >= VOLTAGE_USAGE;

        return hasBlank && hasMetal && hasWire && hasDielectric && outputEmpty && hasEnergy;
    }

    public void craftCapacitor() {
        ItemStack blank = inventory.getStackInSlot(BLANK_CAPACITOR_SLOT);
        ItemStack metal = inventory.getStackInSlot(METALLIC_SLOT);
        ItemStack wire = inventory.getStackInSlot(CONDUCTOR_WIRE_SLOT);
        ItemStack dielectric = inventory.getStackInSlot(ACTIVE_POWER_SLOT);
        ItemStack output = inventory.getStackInSlot(OUTPUT_SLOT);

        double k = ModUtils.ModCapacitanceUtil.getDielectricConstant(dielectric.getItem()).orElse(1.0);
        double capacitance = 100e-6 * k; // Ex: 100μF × k
        long feCapacity = (long) (capacitance * 1_000_000);

        boolean shouldCreate = true;

        if (!output.isEmpty() && output.is(ModItems.CAPACITOR.get())){
            Double existingCapacitanceObj = output.get(ModDataComponents.CAPACITANCE.get());
            if(existingCapacitanceObj != null){
                double existingCapacitance = existingCapacitanceObj;
                if(existingCapacitance == feCapacity){
                    shouldCreate = false;
                }
            }
        }

        if(shouldCreate){
            ItemStack result = new ItemStack(ModItems.CAPACITOR.get());
            result.set(ModDataComponents.CAPACITANCE.get(), capacitance);
            inventory.setStackInSlot(OUTPUT_SLOT, result);

//            CompoundTag tag = result.getOrCreateTag();
//            tag.putDouble("CapacitanceFarads", capacitance);
//            tag.putLong("EnergyCapacityFE", feCapacity);
//            tag.putString("CapacitanceFormatted", ModUtils.ModCapacitanceUtil.getCapacitanceWithPrefix(capacitance));
//            inventory.setStackInSlot(OUTPUT_SLOT, result);
        }

        blank.shrink(1);
        metal.shrink(1);
        wire.shrink(1);
        dielectric.shrink(1);
    }

    public int computeMaxProgress() {
        ItemStack dielectric = inventory.getStackInSlot(METALLIC_SLOT);
        double k = ModUtils.ModCapacitanceUtil.getDielectricConstant(dielectric.getItem()).orElse(1.0);

        double baseTicks = 100.0;
        double scaledTicks = baseTicks * (1.0 / k); // mais K → menos tempo

        int result = (int) Math.max(40, scaledTicks); // nunca menos que 40 ticks
        return result;

    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    public @Nullable IEnergyStorage getEnergyStorageCapability(@Nullable Direction side) {
        return energy;
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        valueOutput.putInt("charger_station.energy", energy.getEnergyStored());
        valueOutput.putChild("charger_station.inventory", inventory);
        inventory.serialize(valueOutput);
        super.saveAdditional(valueOutput);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        energy.setEnergy(valueInput.getIntOr("charger_station.energy", 0));
        inventory.deserialize(valueInput);
    }

    public double getStoredVolts() {
        return energy.getStoredVolts();
    }

    public double getCapacityVolts() {
        return energy.getCapacityVolts();
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
        loadAdditional(valueInput);
    }

}
