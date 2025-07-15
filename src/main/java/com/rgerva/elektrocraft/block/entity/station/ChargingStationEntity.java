/**
 * Generic Class: ChargingStationEntity <T>
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

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.block.entity.ModBlockEntities;
import com.rgerva.elektrocraft.block.entity.basic.ModRecipeBlockEntity;
import com.rgerva.elektrocraft.block.entity.basic.energy.ModVoltEnergyStorage;
import com.rgerva.elektrocraft.component.ModDataComponents;
import com.rgerva.elektrocraft.gui.menu.ChargingStationMenu;
import com.rgerva.elektrocraft.recipe.ContainerRecipeInputWrapper;
import com.rgerva.elektrocraft.recipe.IngredientWithCount;
import com.rgerva.elektrocraft.recipe.ModRecipes;
import com.rgerva.elektrocraft.recipe.station.ChargerStationRecipe;
import com.rgerva.elektrocraft.util.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class ChargingStationEntity extends ModRecipeBlockEntity<RecipeInput, ChargerStationRecipe> {
    public boolean SPARKS = false; //TODO
    public static final double VOLTAGE_USAGE = 5.0; //TODO
    public static final int BLANK_CAPACITOR_SLOT = 0;
    public static final int METALLIC_SLOT = 1;
    public static final int CONDUCTOR_WIRE_SLOT = 2;
    public static final int ACTIVE_POWER_SLOT = 3;
    public static final int OUTPUT_SLOT = 4;

    public ChargingStationEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.CHARGING_STATION_ENTITY.get(), blockPos, blockState,
                ModRecipes.CHARGER_STATION_TYPE.get(), 5,
                512, 256);
    }

    public @Nullable IItemHandler getItemHandlerCapability(@Nullable Direction side) {
        if(side == null) {
            return getInventory();
        }
        return null;
    }

    @Override
    public ContainerData initContainerData() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    case 2 -> (int) Math.floor(energy.getStoredVolts());
                    case 3 -> (int) Math.floor(energy.getCapacityVolts());
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> progress = value;
                    case 1 -> maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    protected RecipeInput getRecipeInput(Container inventory) {
        return new ContainerRecipeInputWrapper(inventory);
    }

    public int getProgress(){
        return this.progress;
    }

    public int getMaxProgress(){
        return this.maxProgress;
    }

    public ItemStackHandler getInventory(){
        return inventory;
    }

    public ContainerData getData(){
        return data;
    }

    public ModVoltEnergyStorage getEnergy(){
        return energy;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.elektrocraft.charging_station");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        syncIngredientListToPlayer(player);
        return new ChargingStationMenu(i, inventory, this);
    }

    @Override
    protected void craftItem(RecipeHolder<ChargerStationRecipe> recipe) {
        if(level == null || !hasRecipe())
            return;

        IngredientWithCount[] inputs = recipe.value().inputs();
        boolean[] usedIndices = new boolean[4];
        for(int i = 0;i < 4;i++)
            usedIndices[i] = inventory.getStackInSlot(i).isEmpty();

        int len = Math.min(inputs.length, 4);
        for(int i = 0;i < len;i++) {
            IngredientWithCount input = inputs[i];

            int indexMinCount = -1;
            int minCount = Integer.MAX_VALUE;

            for(int j = 0;j < 4;j++) {
                if(usedIndices[j])
                    continue;

                ItemStack item = inventory.getStackInSlot(j);

                if((indexMinCount == -1 || item.getCount() < minCount) && input.input().test(item) &&
                        item.getCount() >= input.count()) {
                    indexMinCount = j;
                    minCount = item.getCount();
                }
            }

            if(indexMinCount == -1)
                return; //Should never happen: Ingredient did not match any item

            usedIndices[indexMinCount] = true;

            inventory.extractItem(indexMinCount, input.count(), false);
        }

        ItemStack stack = recipe.value().assemble(null, level.registryAccess()).copyWithCount(
                inventory.getStackInSlot(4).getCount() +
                        recipe.value().assemble(null, level.registryAccess()).getCount());

        ElektroCraft.LOGGER.info("Result Stack; {}", stack.getItemName());

        ItemStack dielectric = inventory.getStackInSlot(ACTIVE_POWER_SLOT);
        double capacitance = ModUtils.ModCapacitanceUtil.computeCapacitance(dielectric);
        stack.set(ModDataComponents.CAPACITANCE.get(), capacitance);

        inventory.setStackInSlot(4, stack);

        resetProgress();
    }

    @Override
    protected boolean canCraftRecipe(SimpleContainer inventory, RecipeHolder<ChargerStationRecipe> recipe) {
        return level != null &&
                ModUtils.InventoryUtils.canInsertItemIntoSlot(inventory, 4, recipe.value().assemble(null, level.registryAccess()));
    }
}
