/**
 * Generic Class: ModEnchantmentEffects <T> A generic structure that works with type parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jun.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.enchantment;

import com.mojang.serialization.MapCodec;
import com.rgerva.elektrocraft.ElektroCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEnchantmentEffects {
  public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>>
      ENTITY_ENCHANTMENT_EFFECTS =
          DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, ElektroCraft.MOD_ID);

  public static void register(IEventBus eventBus) {
    ENTITY_ENCHANTMENT_EFFECTS.register(eventBus);
  }
}
