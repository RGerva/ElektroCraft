/**
 * Generic Class: ModFluids <T> A generic structure that works with type parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jun.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.fluid;

import com.rgerva.elektrocraft.ElektroCraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFluids {
  public static final DeferredRegister<Fluid> FLUIDS =
      DeferredRegister.create(BuiltInRegistries.FLUID, ElektroCraft.MOD_ID);

  public static void register(IEventBus eventBus) {
    FLUIDS.register(eventBus);
  }
}
