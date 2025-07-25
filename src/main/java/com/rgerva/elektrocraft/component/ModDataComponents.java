/**
 * Generic Class: ModDataComponents <T> A generic structure that works with type parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jun.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.component;

import com.mojang.serialization.Codec;
import com.rgerva.elektrocraft.ElektroCraft;
import java.util.function.UnaryOperator;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
  public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
      DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ElektroCraft.MOD_ID);

  public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> RESISTANCE =
      register("resistance", builder -> builder.persistent(Codec.INT));

  public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> VOLTAGE =
      register("voltage", builder -> builder.persistent(Codec.DOUBLE));

  public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> CURRENT =
      register("current", builder -> builder.persistent(Codec.DOUBLE));

  public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> CAPACITANCE =
      register("capacitance", builder -> builder.persistent(Codec.DOUBLE));

  public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> INDUCTANCE =
      register("inductance", builder -> builder.persistent(Codec.DOUBLE));

  private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(
      String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
    return DATA_COMPONENT_TYPES.register(
        name, () -> builderOperator.apply(DataComponentType.builder()).build());
  }

  public static void register(IEventBus eventBus) {
    DATA_COMPONENT_TYPES.register(eventBus);
  }
}
