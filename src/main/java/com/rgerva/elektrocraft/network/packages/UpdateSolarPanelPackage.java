/**
 * Record: UpdateSolarPanelPackage
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
import com.rgerva.elektrocraft.block_entities.custom.generator.SolarPanelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UpdateSolarPanelPackage(BlockPos pos, int currentEnergy,
                                      int currentProduction) implements CustomPacketPayload {

    public static final Type<UpdateSolarPanelPackage> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, "solar_panel_update"));

    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateSolarPanelPackage> STREAM_CODEC =
            StreamCodec.ofMember(UpdateSolarPanelPackage::write, UpdateSolarPanelPackage::new);

    public UpdateSolarPanelPackage(RegistryFriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readInt(), buffer.readInt());
    }

    public void write(RegistryFriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeInt(currentEnergy);
        buffer.writeInt(currentProduction);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(UpdateSolarPanelPackage data, IPayloadContext context) {
        context.enqueueWork(() -> {
            BlockEntity blockEntity = context.player().level().getBlockEntity(data.pos);

            if (blockEntity instanceof SolarPanelBlockEntity solar) {
                solar.energyClient = data.currentEnergy;
                solar.energyProductionClient = data.currentProduction;
            }
        });
    }
}
