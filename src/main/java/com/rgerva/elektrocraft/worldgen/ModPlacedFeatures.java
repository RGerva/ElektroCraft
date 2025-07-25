/**
 * Generic Class: ModPlacedFeatures <T> A generic structure that works with type parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jun.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.worldgen;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.util.ModOresUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class ModPlacedFeatures {

  public static List<ResourceKey<PlacedFeature>> ORE_PLACED_KEY = new ArrayList<>();

  public static void bootstrap(BootstrapContext<PlacedFeature> context) {

    registerAllKey();

    var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

    for (int x = 0; x < ModOresUtils.getOres().size(); x++) {
      register(
          context,
          ORE_PLACED_KEY.get(x),
          configuredFeatures.getOrThrow(ModConfiguredFeatures.ORE_KEY.get(x)),
          ModOrePlacement.commonOrePlacement(
              ModOresUtils.getProperties(ModOresUtils.getOres().get(x)).count(),
              HeightRangePlacement.triangle(
                  VerticalAnchor.absolute(
                      ModOresUtils.getProperties(ModOresUtils.getOres().get(x)).minY()),
                  VerticalAnchor.absolute(
                      ModOresUtils.getProperties(ModOresUtils.getOres().get(x)).maxY()))));
    }
  }

  private static void registerAllKey() {
    for (int x = 0; x < ModOresUtils.getOres().size(); x++) {
      ORE_PLACED_KEY.add(
          registerKey(
              BuiltInRegistries.ITEM
                  .getKey(ModOresUtils.getOres().get(x).asItem())
                  .getPath()
                  .concat("_placed")));
    }
  }

  public static ResourceKey<PlacedFeature> registerKey(String name) {
    return ResourceKey.create(
        Registries.PLACED_FEATURE,
        ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, name));
  }

  private static void register(
      BootstrapContext<PlacedFeature> context,
      ResourceKey<PlacedFeature> key,
      Holder<ConfiguredFeature<?, ?>> configuration,
      List<PlacementModifier> modifiers) {
    context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
  }
}
