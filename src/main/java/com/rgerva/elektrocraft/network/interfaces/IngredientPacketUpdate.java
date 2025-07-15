/**
 * Interface: IngredientPacketUpdate
 * Defines the contract for implementations of this type.
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

package com.rgerva.elektrocraft.network.interfaces;

import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

/**
 * Used for SyncIngredientsS2CPacket
 */
public interface IngredientPacketUpdate {
    void setIngredients(int index, List<Ingredient> ingredients);
}
