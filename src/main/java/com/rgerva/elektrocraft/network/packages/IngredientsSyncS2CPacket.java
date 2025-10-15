/**
 * Record: IngredientsSyncS2CPacket
 * Immutable data structure for simplified object representation.
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

package com.rgerva.elektrocraft.network.packages;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.network.interfaces.IIngredientPacketUpdate;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public record IngredientsSyncS2CPacket(BlockPos pos, int index,
                                       List<Ingredient> ingredientList) implements CustomPacketPayload {

    public static final Type<IngredientsSyncS2CPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, "sync_ingredients"));

    public static final StreamCodec<RegistryFriendlyByteBuf, IngredientsSyncS2CPacket> STREAM_CODEC =
            StreamCodec.ofMember(IngredientsSyncS2CPacket::write, IngredientsSyncS2CPacket::new);

    private static List<Ingredient> readIngredientList(RegistryFriendlyByteBuf buffer) {
        int len = buffer.readInt();
        List<Ingredient> ingredients = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            ingredients.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
        }
        return ingredients;
    }

    public IngredientsSyncS2CPacket(RegistryFriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readerIndex(), readIngredientList(buffer));
    }

    public void write(RegistryFriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);

        buffer.writeInt(index);

        buffer.writeInt(ingredientList.size());

        for (Ingredient ingredient : ingredientList)
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(IngredientsSyncS2CPacket data, IPayloadContext context) {
        context.enqueueWork(() -> {
            BlockEntity blockEntity = context.player().level().getBlockEntity(data.pos);

            if (blockEntity instanceof IIngredientPacketUpdate packetUpdate) {
                packetUpdate.setIngredients(data.index, new ArrayList<>(data.ingredientList));
            }
        });
    }
}
