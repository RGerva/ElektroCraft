/**
 * Generic Class: ModEnchantments <T> A generic structure that works with type parameters.
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

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {

  public static void bootstrap(BootstrapContext<Enchantment> context) {}

  private static void register(
      BootstrapContext<Enchantment> registry,
      ResourceKey<Enchantment> key,
      Enchantment.Builder builder) {
    registry.register(key, builder.build(key.location()));
  }
}
