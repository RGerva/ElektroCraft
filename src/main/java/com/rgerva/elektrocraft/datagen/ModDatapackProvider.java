/**
 * Generic Class: ModDatapackProvider <T> A generic structure that works with type parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jun.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.datagen;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.worldgen.ModBiomeModifiers;
import com.rgerva.elektrocraft.worldgen.ModConfiguredFeatures;
import com.rgerva.elektrocraft.worldgen.ModPlacedFeatures;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModDatapackProvider extends DatapackBuiltinEntriesProvider {
  public static final RegistrySetBuilder BUILDER =
      new RegistrySetBuilder()
          .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
          .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
          .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);

  public ModDatapackProvider(
      PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries, BUILDER, Set.of(ElektroCraft.MOD_ID));
  }
}
