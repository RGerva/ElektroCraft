/**
 * Generic Class: ModOrePlacement <T> A generic structure that works with type parameters.
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

import java.util.List;
import net.minecraft.world.level.levelgen.placement.*;

public class ModOrePlacement {
  public static List<PlacementModifier> orePlacement(
      PlacementModifier pCountPlacement, PlacementModifier pHeightRange) {
    return List.of(pCountPlacement, InSquarePlacement.spread(), pHeightRange, BiomeFilter.biome());
  }

  public static List<PlacementModifier> commonOrePlacement(
      int pCount, PlacementModifier pHeightRange) {
    return orePlacement(CountPlacement.of(pCount), pHeightRange);
  }

  public static List<PlacementModifier> rareOrePlacement(
      int pChance, PlacementModifier pHeightRange) {
    return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
  }
}
