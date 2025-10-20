/**
 * Generic Class: ExtendedCraftingStationMenu <T>
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

package com.rgerva.elektrocraft.screen.menu.stations;

import com.rgerva.elektrocraft.block.ModBlocks;
import com.rgerva.elektrocraft.block_entities.custom.stations.ExtendedCraftingStationEntity;
import com.rgerva.elektrocraft.screen.ModScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ExtendedCraftingStationMenu extends AbstractContainerMenu {

    private final Level level;
    private final ExtendedCraftingStationEntity entity;

    public ExtendedCraftingStationMenu(int pContainerId, Inventory inv, FriendlyByteBuf buffer) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(buffer.readBlockPos()));
    }

    public ExtendedCraftingStationMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(ModScreen.EXTENDED_CRAFTING_STATION_MENU.get(), containerId);
        this.level = inventory.player.level();
        this.entity = (ExtendedCraftingStationEntity) blockEntity;

        this.addSlot(new ResultSlot(inventory.player, null, this.entity.resultContainer, 0, 141, 48));

        for (int playerInvRow = 0; playerInvRow < 5; ++playerInvRow) {
            for (int playerInvCol = 0; playerInvCol < 5; ++playerInvCol) {
                this.addSlot(new Slot(this.entity.craftingContainer, playerInvCol + playerInvRow * 5, 12 + playerInvCol * 18, 11 + playerInvRow * 18));
            }
        }

        for (int playerInvRow = 0; playerInvRow < 3; ++playerInvRow) {
            for (int playerInvCol = 0; playerInvCol < 9; ++playerInvCol) {
                this.addSlot(new Slot(inventory, playerInvCol + playerInvRow * 9 + 9, 8 + playerInvCol * 18, 110 + playerInvRow * 18));
            }
        }

        for (int hotHarSlot = 0; hotHarSlot < 9; ++hotHarSlot) {
            this.addSlot(new Slot(inventory, hotHarSlot, 8 + hotHarSlot * 18, 168));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, entity.getBlockPos()), player, ModBlocks.EXTENDED_CRAFTING_STATION.get());
    }
}
