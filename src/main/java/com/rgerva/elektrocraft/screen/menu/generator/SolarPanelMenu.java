/**
 * Generic Class: SolarPanelMenu <T>
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

package com.rgerva.elektrocraft.screen.menu.generator;

import com.rgerva.elektrocraft.block.ModBlocks;
import com.rgerva.elektrocraft.block_entities.custom.generator.SolarPanelBlockEntity;
import com.rgerva.elektrocraft.screen.ModScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SolarPanelMenu extends AbstractContainerMenu {

    private final Level level;
    public final SolarPanelBlockEntity entity;

    public SolarPanelMenu(int containerId, Inventory inv, FriendlyByteBuf buffer) {
        this(containerId, inv, inv.player.level().getBlockEntity(buffer.readBlockPos()));
    }

    public SolarPanelMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(ModScreen.SOLAR_PANEL_MENU.get(), containerId);

        this.level = inventory.player.level();
        this.entity = (SolarPanelBlockEntity) blockEntity;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        assert entity.getLevel() != null;
        return stillValid(ContainerLevelAccess.create(entity.getLevel(), entity.getBlockPos()), player, ModBlocks.SOLAR_PANEL.get());
    }
}
