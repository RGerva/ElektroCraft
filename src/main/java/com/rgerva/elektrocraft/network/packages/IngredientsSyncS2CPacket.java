/**
 * Generic Class: IngredientsSyncS2CPacket <T> A generic structure that works with type parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jul.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.network.packages;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.network.interfaces.IngredientPacketUpdate;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class IngredientsSyncS2CPacket implements CustomPacketPayload {
  public static final Type<IngredientsSyncS2CPacket> ID =
      new Type<>(ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, "sync_ingredients"));

  public static final StreamCodec<RegistryFriendlyByteBuf, IngredientsSyncS2CPacket> STREAM_CODEC =
      StreamCodec.ofMember(IngredientsSyncS2CPacket::write, IngredientsSyncS2CPacket::new);

  private final BlockPos pos;
  private final int index;
  private final List<Ingredient> ingredientList;

  public IngredientsSyncS2CPacket(BlockPos pos, int index, List<Ingredient> ingredientList) {
    this.pos = pos;
    this.index = index;
    this.ingredientList = new ArrayList<>(ingredientList);
  }

  public IngredientsSyncS2CPacket(RegistryFriendlyByteBuf buffer) {
    pos = buffer.readBlockPos();

    index = buffer.readInt();

    int len = buffer.readInt();
    ArrayList<Ingredient> ingredientList = new ArrayList<>(len);
    for (int i = 0; i < len; i++)
      ingredientList.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));

    this.ingredientList = ingredientList;
  }

  public void write(RegistryFriendlyByteBuf buffer) {
    buffer.writeBlockPos(pos);

    buffer.writeInt(index);

    buffer.writeInt(ingredientList.size());

    for (Ingredient ingredient : ingredientList)
      Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
  }

  @Override
  @NotNull
  public Type<? extends CustomPacketPayload> type() {
    return ID;
  }

  public static void handle(IngredientsSyncS2CPacket data, IPayloadContext context) {
    context.enqueueWork(
        () -> {
          BlockEntity blockEntity = context.player().level().getBlockEntity(data.pos);

          if (blockEntity instanceof IngredientPacketUpdate ingredientPacketUpdate)
            ingredientPacketUpdate.setIngredients(data.index, new ArrayList<>(data.ingredientList));
        });
  }
}
