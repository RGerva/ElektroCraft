/**
 * Interface: ModSyncPackages
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

import com.rgerva.elektrocraft.network.ModMessages;
import com.rgerva.elektrocraft.network.packages.EnergySyncS2CPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface ModSyncPackages {
    BlockEntity getInterfaceSyncBlockEntity();

    int getInterfaceSyncEnergy();

    int getInterfaceSyncCapacity();

    default void syncEnergyToPlayer(Player player) {
        ModMessages.sendToPlayer(new EnergySyncS2CPacket(getInterfaceSyncEnergy(), getInterfaceSyncCapacity(),
                getInterfaceSyncBlockEntity().getBlockPos()), (ServerPlayer) player);
    }

    default void syncEnergyToPlayers(int distance) {
        Level level = getInterfaceSyncBlockEntity().getLevel();
        if (level != null && !level.isClientSide())
            ModMessages.sendToPlayersWithinXBlocks(
                    new EnergySyncS2CPacket(getInterfaceSyncEnergy(), getInterfaceSyncCapacity(), getInterfaceSyncBlockEntity().getBlockPos()),
                    getInterfaceSyncBlockEntity().getBlockPos(), (ServerLevel) level, distance
            );
    }
}
