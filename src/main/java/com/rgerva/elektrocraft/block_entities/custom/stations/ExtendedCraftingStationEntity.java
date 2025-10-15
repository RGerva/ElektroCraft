/**
 * Generic Class: ExtendedCraftingStationEntity <T>
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

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.block_entities.ModBlockEntities;
import com.rgerva.elektrocraft.screen.menu.ExtendedCraftingStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ExtendedCraftingStationEntity extends BlockEntity implements MenuProvider {

    public final SimpleContainer craftingContainer = new SimpleContainer(25);
    public final SimpleContainer resultContainer = new SimpleContainer(1);

    public ExtendedCraftingStationEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.EXTENDED_CRAFTING_STATION_ENTITY.get(), pos, blockState);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.elektrocraft.extended_crafting_station");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ExtendedCraftingStationMenu(i, inventory, this);
    }

    public void dropsContent() {
        for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
            assert this.level != null;
            Containers.dropContents(this.level, this.worldPosition, craftingContainer);
        }
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, ExtendedCraftingStationEntity entity) {
        ItemStack slot = entity.craftingContainer.getItems().getFirst();
        if (slot.isStackable()) {
            ElektroCraft.LOGGER.info("HAS ITEM");
        }
    }
}
