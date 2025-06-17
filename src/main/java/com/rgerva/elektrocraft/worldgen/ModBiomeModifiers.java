package com.rgerva.elektrocraft.worldgen;

import com.rgerva.elektrocraft.ElektroCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ModBiomeModifiers {

    public static List<ResourceKey<BiomeModifier>> OVERWORLD_ADD_ORE = new ArrayList<>();
    public static List<ResourceKey<BiomeModifier>> NETHER_ADD_ORE = new ArrayList<>();
    public static List<ResourceKey<BiomeModifier>> END_ADD_ORE = new ArrayList<>();

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {

        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, name));
    }
}
