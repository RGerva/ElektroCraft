/**
 * Generic Class: ChargingStationMenu <T>
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
import com.rgerva.elektrocraft.block_entities.custom.stations.ChargingStationEntity;
import com.rgerva.elektrocraft.screen.ModScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Objects;

public class ChargingStationMenu extends AbstractContainerMenu {

    public final ChargingStationEntity entity;

    public ChargingStationMenu(int pContainerId, Inventory inv, FriendlyByteBuf buffer) {
        this(pContainerId, inv, Objects.requireNonNull(inv.player.level().getBlockEntity(buffer.readBlockPos())));
    }

    public ChargingStationMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(ModScreen.CHARGING_STATION_MENU.get(), containerId);

        this.entity = (ChargingStationEntity) blockEntity;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        assert entity.getLevel() != null;
        return stillValid(ContainerLevelAccess.create(entity.getLevel(), entity.getBlockPos()), player, ModBlocks.CHARGING_STATION.get());
    }
}
