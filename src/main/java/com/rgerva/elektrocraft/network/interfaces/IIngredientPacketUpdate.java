/**
 * Interface: IIngredientPacketUpdate
 * Defines the contract for implementations of this type.
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

package com.rgerva.elektrocraft.network.interfaces;

import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public interface IIngredientPacketUpdate {
    void setIngredients(int index, List<Ingredient> ingredients);

//    BlockEntity getInterfaceSyncBlockEntity();
//
//    default void syncIngredientListToPlayer(Player player) {
//        BlockPos pos = getInterfaceSyncBlockEntity().getBlockPos();
//        Level level = getInterfaceSyncBlockEntity().getLevel();
//        if (!(level instanceof ServerLevel serverLevel)) return;
//
//    }
}
