/**
 * Generic Class: ModCapabilities <T>
 * A generic structure that works with type parameters.
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

package com.rgerva.elektrocraft.capabilities;

import com.mojang.serialization.Codec;
import com.rgerva.elektrocraft.ElektroCraft;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCapabilities {

    public static final DeferredRegister.DataComponents CAPABILITIES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ElektroCraft.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MOD_ENERGY =
            CAPABILITIES.registerComponentType("energy", integerBuilder ->
                    integerBuilder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));

    public static void register(IEventBus eventBus) {
        CAPABILITIES.register(eventBus);
    }
}
