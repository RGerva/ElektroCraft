/**
 * Generic Class: ChargerStationMenu <T>
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

package com.rgerva.elektrocraft.gui.menu;

import com.rgerva.elektrocraft.block.ModBlocks;
import com.rgerva.elektrocraft.block.entity.station.ChargingStationEntity;
import com.rgerva.elektrocraft.capabilities.ModCapabilities;
import com.rgerva.elektrocraft.gui.ModGUI;
import com.rgerva.elektrocraft.item.ModItems;
import com.rgerva.elektrocraft.util.ModTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.items.SlotItemHandler;

import java.util.Objects;

public class ChargingStationMenu extends AbstractContainerMenu {

    private final ChargingStationEntity entity;
    public final ContainerData data;

    private int clientStoredVolts = -1;
    private int clientMaxVolts = -1;

    public ChargingStationMenu(int pContainerId, Inventory inv, FriendlyByteBuf buffer) {
        this(pContainerId, inv, Objects.requireNonNull(inv.player.level().getBlockEntity(buffer.readBlockPos())));
    }

    public ChargingStationMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(ModGUI.CHARGER_STATION_MENU.get(), containerId);
        this.entity = (ChargingStationEntity) blockEntity;
        this.data = ((ChargingStationEntity) blockEntity).getData();

        this.addDataSlots(data);

        assert blockEntity.getLevel() != null;
        ModCapabilities.getCapabilityItemHandler(blockEntity.getLevel(), blockEntity).ifPresent(itemHandler -> {
            addSlot(new SlotItemHandler(itemHandler, ChargingStationEntity.BLANK_CAPACITOR_SLOT, 44, 37){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.is(ModTags.Items.CAPACITANCE);
                }
            });
            addSlot(new SlotItemHandler(itemHandler, ChargingStationEntity.METALLIC_SLOT, 62, 19){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.is(Tags.Items.INGOTS_IRON);
                }
            });
            addSlot(new SlotItemHandler(itemHandler, ChargingStationEntity.CONDUCTOR_WIRE_SLOT, 80, 37){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.is(ModItems.TIN_SOLDER_WIRE.get());
                }
            });
            addSlot(new SlotItemHandler(itemHandler, ChargingStationEntity.ACTIVE_POWER_SLOT, 62, 55){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.is(ModTags.Items.DIELECTRIC_CONSTANTS);
                }
            });
            addSlot(new SlotItemHandler(itemHandler, ChargingStationEntity.OUTPUT_SLOT, 134, 37) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
        });

        addPlayerInventorySlots(inventory);

        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (int) entity.getEnergy().getStoredVolts();
            }

            @Override
            public void set(int value) {
                clientStoredVolts = value;
            }
        });

        this.addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (int) entity.getEnergy().getCapacityVolts();
            }

            @Override
            public void set(int value) {
                clientMaxVolts = value;
            }
        });
    }

    protected void addPlayerInventorySlots(Inventory inventory) {
        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++) {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++) {
                this.addSlot(new Slot(inventory, playerInvCol + playerInvRow * 9 + 9, 8 + playerInvCol * 18, 88 + playerInvRow * 18));
            }
        }

        for (int hotHarSlot = 0; hotHarSlot < 9; hotHarSlot++) {
            this.addSlot(new Slot(inventory, hotHarSlot, 8 + hotHarSlot * 18, 146));
        }
    }

    public int getMachineSlotCount() {
        return 5;
    }

    public boolean handleCustomSlotTransfer(ItemStack stack) {
        if (stack.is(ModItems.BLANK_CAPACITOR.get())) {
            return this.moveItemStackTo(stack, 0, 1, false);
        } else if (stack.is(ModTags.Items.DIELECTRIC_CONSTANTS)) {
            return this.moveItemStackTo(stack, 3, 4, false);
        }
        return false;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = this.slots.get(index);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copy = sourceStack.copy();

        int machineSlotCount = getMachineSlotCount();
        int playerStart = machineSlotCount;
        int playerEnd = playerStart + 36;

        if (index < playerStart) {
            if (!this.moveItemStackTo(sourceStack, playerStart, playerEnd, false))
                return ItemStack.EMPTY;
        } else {
            if (!handleCustomSlotTransfer(sourceStack)) {
                if (!this.moveItemStackTo(sourceStack, 0, machineSlotCount, false))
                    return ItemStack.EMPTY;
            }
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(Objects.requireNonNull(entity.getLevel()), entity.getBlockPos()),
                player, ModBlocks.CHARGING_STATION.get());
    }

    public int getProgress() {
        return data.get(0);
    }

    public int getMaxProgress() {
        return data.get(1);
    }

    public int getClientStoredVolts() {
        return clientStoredVolts;
    }

    public int getClientMaxVolts() {
        return clientMaxVolts;
    }
}
