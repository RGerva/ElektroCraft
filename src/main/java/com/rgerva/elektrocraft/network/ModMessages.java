/**
 * Generic Class: ModMessages <T> A generic structure that works with type parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jun.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.network;

import com.rgerva.elektrocraft.network.packages.EnergySyncS2CPacket;
import com.rgerva.elektrocraft.network.packages.IngredientsSyncS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModMessages {
  public static void register(final RegisterPayloadHandlersEvent event) {
    final PayloadRegistrar registrar = event.registrar("1.0");

    registrar.playToClient(
        EnergySyncS2CPacket.ID, EnergySyncS2CPacket.STREAM_CODEC, EnergySyncS2CPacket::handle);

    registrar.playToClient(
        IngredientsSyncS2CPacket.ID,
        IngredientsSyncS2CPacket.STREAM_CODEC,
        IngredientsSyncS2CPacket::handle);
  }

  public static void sendToServer(CustomPacketPayload message) {
    ClientPacketDistributor.sendToServer(message);
  }

  public static void sendToPlayer(CustomPacketPayload message, ServerPlayer player) {
    PacketDistributor.sendToPlayer(player, message);
  }

  public static void sendToPlayersWithinXBlocks(
      CustomPacketPayload message, BlockPos pos, ServerLevel level, int distance) {
    PacketDistributor.sendToPlayersNear(
        level, null, pos.getX(), pos.getY(), pos.getZ(), distance, message);
  }
}
