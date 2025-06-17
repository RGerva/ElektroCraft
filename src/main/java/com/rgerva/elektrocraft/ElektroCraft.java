/**
 * Class: ElektroCraft <T>
 * Created by: D56V1OK
 * On: 2025/jun.
 * <p>
 * GitHub: https://github.com/RGerva
 * <p>
 * Copyright (c) 2025 @RGerva. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rgerva.elektrocraft;

import com.mojang.logging.LogUtils;
import com.rgerva.elektrocraft.block.ModBlocks;
import com.rgerva.elektrocraft.block.entity.ModBlockEntities;
import com.rgerva.elektrocraft.capabilities.ModCapabilities;
import com.rgerva.elektrocraft.component.ModDataComponents;
import com.rgerva.elektrocraft.config.ModConfig;
import com.rgerva.elektrocraft.creative.ModCreativeTab;
import com.rgerva.elektrocraft.effect.ModEffects;
import com.rgerva.elektrocraft.enchantment.ModEnchantmentEffects;
import com.rgerva.elektrocraft.entity.ModEntities;
import com.rgerva.elektrocraft.fluid.ModFluids;
import com.rgerva.elektrocraft.gui.ModGUI;
import com.rgerva.elektrocraft.item.ModItems;
import com.rgerva.elektrocraft.loot.ModLootModifiers;
import com.rgerva.elektrocraft.network.ModMessages;
import com.rgerva.elektrocraft.particles.ModParticles;
import com.rgerva.elektrocraft.potion.ModPotions;
import com.rgerva.elektrocraft.recipe.ModRecipes;
import com.rgerva.elektrocraft.sound.ModSounds;
import com.rgerva.elektrocraft.villager.ModVillagers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(ElektroCraft.MOD_ID)
public class ElektroCraft {
    public static final String MOD_ID = "elektrocraft";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ElektroCraft(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        ModCreativeTab.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModDataComponents.register(modEventBus);
        ModSounds.register(modEventBus);

        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);

        ModEnchantmentEffects.register(modEventBus);
        ModEntities.register(modEventBus);

        ModVillagers.register(modEventBus);
        ModParticles.register(modEventBus);

        ModLootModifiers.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        ModGUI.register(modEventBus);
        ModRecipes.register(modEventBus);

        ModFluids.register(modEventBus);

        modEventBus.addListener(ModCreativeTab::addCreative);
        modEventBus.addListener(ModCapabilities::registerCapabilities);
        modEventBus.addListener(ModMessages::register);

        modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, ModConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("{}{}", ModConfig.magicNumberIntroduction, ModConfig.magicNumber);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        ElektroCraft.LOGGER.info("HELLO from server starting");
    }

}
