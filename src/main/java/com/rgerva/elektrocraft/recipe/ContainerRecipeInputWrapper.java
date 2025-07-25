/**
 * Generic Class: ContainerRecipeInputWrapper <T> A generic structure that works with type
 * parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jul.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public class ContainerRecipeInputWrapper implements RecipeInput {
  private final Container inventory;

  public ContainerRecipeInputWrapper(Container inventory) {
    this.inventory = inventory;
  }

  @Override
  public ItemStack getItem(int slot) {
    return inventory.getItem(slot);
  }

  @Override
  public int size() {
    return inventory.getContainerSize();
  }
}
