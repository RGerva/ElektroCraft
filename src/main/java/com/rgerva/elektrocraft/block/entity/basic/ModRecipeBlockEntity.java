/**
 * Generic Class: ModRecipeBlockEntity <T> A generic structure that works with type parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jul.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.block.entity.basic;

import com.rgerva.elektrocraft.block.entity.basic.energy.ModVoltEnergyStorage;
import com.rgerva.elektrocraft.block.entity.basic.stack.ModItemStackStorage;
import com.rgerva.elektrocraft.network.interfaces.IngredientPacketUpdate;
import com.rgerva.elektrocraft.network.interfaces.ModSyncIngredientsPackages;
import com.rgerva.elektrocraft.util.ModUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class ModRecipeBlockEntity<C extends RecipeInput, R extends Recipe<C>>
    extends ModBasicCrafterEntity<RecipeHolder<R>>
    implements IngredientPacketUpdate, MenuProvider, ModSyncIngredientsPackages {

  protected final RecipeType<R> recipeType;
  protected List<Ingredient> ingredientsOfRecipes = new ArrayList<>();
  protected final ModItemStackStorage inventory;

  public ModRecipeBlockEntity(
      BlockEntityType<?> type,
      BlockPos pos,
      BlockState blockState,
      RecipeType<R> recipeType,
      int slotSize,
      int baseWorkDuration,
      int baseEnergyConsumptionPerTick) {

    super(type, pos, blockState, baseWorkDuration, baseEnergyConsumptionPerTick);

    this.recipeType = recipeType;
    this.inventory =
        new ModItemStackStorage(slotSize, this::setChanged) {
          @Override
          public boolean isItemValid(int slot, ItemStack stack) {
            return switch (slot) {
              case 0, 1, 2, 3 ->
                  ((level instanceof ServerLevel serverLevel)
                      ? ModUtils.ModRecipeUtil.isIngredientOfAny(serverLevel, recipeType, stack)
                      : ModUtils.ModRecipeUtil.isIngredientOfAny(ingredientsOfRecipes, stack));
              case 4 -> false;
              default -> false;
            };
          }

          @Override
          public void setStackInSlot(int slot, ItemStack stack) {
            if (slot >= 0 && slot < 4) {
              ItemStack itemStack = getStackInSlot(slot);
              if (level != null
                  && !stack.isEmpty()
                  && !itemStack.isEmpty()
                  && !ItemStack.isSameItemSameComponents(stack, itemStack)) resetProgress();
            }

            super.setStackInSlot(slot, stack);
          }

          @Override
          protected void onContentsChanged(int slot) {
            setChanged();
          }
        };
  }

  @Override
  protected ModVoltEnergyStorage makeConsumer() {
    return ModVoltEnergyStorage.makeConsumer(100_000L, 512L);
  }

  public void drops() {
    SimpleContainer inv = new SimpleContainer(inventory.getSlots());
    for (int i = 0; i < inventory.getSlots(); i++) {
      inv.setItem(i, inventory.getStackInSlot(i));
    }

    assert this.level != null;
    Containers.dropContents(this.level, this.worldPosition, inv);
  }

  protected abstract C getRecipeInput(Container inventory);

  protected Optional<RecipeHolder<R>> getRecipeFor(Container inventory) {
    if (!(level instanceof ServerLevel serverLevel)) return Optional.empty();

    return serverLevel.recipeAccess().getRecipeFor(recipeType, getRecipeInput(inventory), level);
  }

  @Override
  protected boolean hasWork() {
    return hasRecipe();
  }

  protected boolean hasRecipe() {
    if (level == null) return false;

    SimpleContainer inv = new SimpleContainer(inventory.getSlots());
    for (int i = 0; i < inventory.getSlots(); i++) {
      inv.setItem(i, inventory.getStackInSlot(i));
    }
    Optional<RecipeHolder<R>> recipe = getRecipeFor(inv);

    return recipe.isPresent() && canCraftRecipe(inv, recipe.get());
  }

  protected boolean canCraftRecipe(SimpleContainer inventory, RecipeHolder<R> recipe) {
    return level != null
        && ModUtils.InventoryUtils.canInsertItemIntoSlot(
            inventory,
            1,
            recipe.value().assemble(getRecipeInput(inventory), level.registryAccess()));
  }

  @Override
  protected Optional<RecipeHolder<R>> getCurrentWorkData() {
    SimpleContainer inv = new SimpleContainer(inventory.getSlots());
    for (int i = 0; i < inventory.getSlots(); i++) inv.setItem(i, inventory.getStackInSlot(i));

    return getRecipeFor(inv);
  }

  @Override
  protected void onWorkStarted(RecipeHolder<R> workData) {
    onStartCrafting(workData);
  }

  protected void onStartCrafting(RecipeHolder<R> recipe) {}

  @Override
  protected void onWorkCompleted(RecipeHolder<R> workData) {
    craftItem(workData);
  }

  protected void craftItem(RecipeHolder<R> recipe) {
    if (level == null || !hasRecipe()) return;

    SimpleContainer inv = new SimpleContainer(inventory.getSlots());
    for (int i = 0; i < inventory.getSlots(); i++) {
      inv.setItem(i, inventory.getStackInSlot(i));
    }

    inventory.extractItem(0, 1, false);
    inventory.setStackInSlot(
        1,
        recipe
            .value()
            .assemble(getRecipeInput(inv), level.registryAccess())
            .copyWithCount(
                inventory.getStackInSlot(1).getCount()
                    + recipe
                        .value()
                        .assemble(getRecipeInput(inv), level.registryAccess())
                        .getCount()));
    resetProgress();
  }

  public List<Ingredient> getIngredientsOfRecipes() {
    return new ArrayList<>(ingredientsOfRecipes);
  }

  @Override
  public void setIngredients(int index, List<Ingredient> ingredients) {
    if (index == 0) {
      this.ingredientsOfRecipes = ingredients;
    }
  }

  @Override
  public BlockEntity getInterfaceSyncBlockEntity() {
    return this;
  }

  @Override
  public RecipeType<?> getRecipeType() {
    return recipeType;
  }

  @Override
  protected void loadAdditional(ValueInput valueInput) {
    super.loadAdditional(valueInput);
    inventory.deserialize(valueInput);
  }

  @Override
  protected void saveAdditional(ValueOutput valueOutput) {
    valueOutput.putChild(this.getNameForReporting() + ".inventory", inventory);
    inventory.serialize(valueOutput);
    super.saveAdditional(valueOutput);
  }
}
