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
import com.rgerva.elektrocraft.block.entity.station.ChargerStationEntity;
import com.rgerva.elektrocraft.gui.ModGUI;
import com.rgerva.elektrocraft.item.ModItems;
import com.rgerva.elektrocraft.util.ModTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ChargerStationMenu extends AbstractContainerMenu {

    private final ChargerStationEntity entity;
    public final ContainerData data;

    public ChargerStationMenu(int pContainerId, Inventory inv, FriendlyByteBuf buffer) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(buffer.readBlockPos()));
    }

    public ChargerStationMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(ModGUI.CHARGER_STATION_MENU.get(), containerId);
        this.entity = (ChargerStationEntity) blockEntity;
        this.data = ((ChargerStationEntity) blockEntity).data;

        this.addDataSlots(data);
        addSlot(new SlotItemHandler(entity.getInventory(), ChargerStationEntity.BLANK_CAPACITOR_SLOT, 26, 21));
        addSlot(new SlotItemHandler(entity.getInventory(), ChargerStationEntity.METALLIC_SLOT, 44, 21));
        addSlot(new SlotItemHandler(entity.getInventory(), ChargerStationEntity.CONDUCTOR_WIRE_SLOT, 26, 39));
        addSlot(new SlotItemHandler(entity.getInventory(), ChargerStationEntity.ACTIVE_POWER_SLOT, 44, 39));
        addSlot(new SlotItemHandler(entity.getInventory(), ChargerStationEntity.OUTPUT_SLOT, 116, 30) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }
        });


        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++) {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++) {
                this.addSlot(new Slot(inventory, playerInvCol + playerInvRow * 9 + 9, 8 + playerInvCol * 18, 84 + playerInvRow * 18));
            }
        }

        for (int hotHarSlot = 0; hotHarSlot < 9; hotHarSlot++) {
            this.addSlot(new Slot(inventory, hotHarSlot, 8 + hotHarSlot * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = this.slots.get(index);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyStack = sourceStack.copy();

        int containerSize = 5;
        int playerStart = containerSize;
        int playerEnd = containerSize + 36;

        if (index < playerStart) {
            if (!this.moveItemStackTo(sourceStack, playerStart, playerEnd, true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (sourceStack.is(ModItems.BLANK_CAPACITOR.get())) {
                if (!this.moveItemStackTo(sourceStack, ChargerStationEntity.BLANK_CAPACITOR_SLOT, ChargerStationEntity.BLANK_CAPACITOR_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (sourceStack.is(ModTags.Items.DIELECTRIC_CONSTANTS)) {
                if (!this.moveItemStackTo(sourceStack, ChargerStationEntity.ACTIVE_POWER_SLOT, ChargerStationEntity.ACTIVE_POWER_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(sourceStack, ChargerStationEntity.METALLIC_SLOT, ChargerStationEntity.OUTPUT_SLOT, false)) {
                    return ItemStack.EMPTY;
                }
            }
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return copyStack;

    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(Objects.requireNonNull(entity.getLevel()), entity.getBlockPos()),
                player, ModBlocks.CHARGER_STATION.get());
    }

    public int getProgress() {
        return data.get(0);
    }

    public int getMaxProgress() {
        return data.get(1);
    }

    public int getStoredVolts() {
        return data.get(2);
    }

    public int getMaxVolts() {
        return data.get(3);
    }

}
