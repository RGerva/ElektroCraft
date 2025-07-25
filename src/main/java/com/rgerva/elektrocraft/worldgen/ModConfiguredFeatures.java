/**
 * Generic Class: ModConfiguredFeatures <T> A generic structure that works with type parameters.
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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class ModConfiguredFeatures {

  public static List<ResourceKey<ConfiguredFeature<?, ?>>> ORE_KEY = new ArrayList<>();

  public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    ModOresUtils.setOresProperties();
    registerAllKey();

    RuleTest stoneReplacebles = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
    RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);

    for (int x = 0; x < ModOresUtils.getOres().size(); x++) {
      List<OreConfiguration.TargetBlockState> Ores =
          List.of(
              OreConfiguration.target(
                  stoneReplacebles, ModOresUtils.getOres().get(x).defaultBlockState()),
              OreConfiguration.target(
                  deepslateReplaceables, ModOresUtils.getDeepslateOre().get(x).defaultBlockState()),
              OreConfiguration.target(
                  netherrackReplaceables, ModOresUtils.getNetherOre().get(x).defaultBlockState()),
              OreConfiguration.target(
                  endReplaceables, ModOresUtils.getEndOre().get(x).defaultBlockState()));

      register(
          context,
          ORE_KEY.get(x),
          Feature.ORE,
          new OreConfiguration(
              Ores, ModOresUtils.getProperties(ModOresUtils.getOres().get(x)).veinSize()));
    }
  }

  private static void registerAllKey() {
    for (int x = 0; x < ModOresUtils.getOres().size(); x++) {
      ORE_KEY.add(
          registerKey(
              BuiltInRegistries.ITEM.getKey(ModOresUtils.getOres().get(x).asItem()).getPath()));
    }
  }

  public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
    return ResourceKey.create(
        Registries.CONFIGURED_FEATURE,
        ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, name));
  }

  private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
      BootstrapContext<ConfiguredFeature<?, ?>> context,
      ResourceKey<ConfiguredFeature<?, ?>> key,
      F feature,
      FC configuration) {
    context.register(key, new ConfiguredFeature<>(feature, configuration));
  }
}
