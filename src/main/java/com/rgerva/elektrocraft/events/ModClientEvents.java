/**
 * Generic Class: ModClientEvents <T>
 * A generic structure that works with type parameters.
 * <p>
 * Created by: D56V1OK
 * On: 2025/out.
 * <p>
 * GitHub: https://github.com/RGerva
 * <p>
 * Copyright (c) 2025 @RGerva. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.rgerva.elektrocraft.events;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.block_entities.ModBlockEntities;
import com.rgerva.elektrocraft.block_entities.custom.generator.SolarPanelBlockEntity;
import com.rgerva.elektrocraft.block_entities.custom.stations.ChargingStationEntity;
import com.rgerva.elektrocraft.screen.ModScreen;
import com.rgerva.elektrocraft.screen.custom.generator.SolarPanelScreen;
import com.rgerva.elektrocraft.screen.custom.stations.ChargingStationScreen;
import com.rgerva.elektrocraft.screen.custom.stations.ExtendedCraftingStationScreen;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;


@EventBusSubscriber(modid = ElektroCraft.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        ElektroCraft.LOGGER.info("HELLO FROM CLIENT SETUP");
        ElektroCraft.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModScreen.EXTENDED_CRAFTING_STATION_MENU.get(), ExtendedCraftingStationScreen::new);
        event.register(ModScreen.SOLAR_PANEL_MENU.get(), SolarPanelScreen::new);
        event.register(ModScreen.CHARGING_STATION_MENU.get(), ChargingStationScreen::new);
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Energy.BLOCK, ModBlockEntities.SOLAR_PANEL_ENTITY.get(), SolarPanelBlockEntity::getSolarPanelBattery);
        event.registerBlockEntity(Capabilities.Energy.BLOCK, ModBlockEntities.CHARGING_STATION_ENTITY.get(), ChargingStationEntity::getChargingStationBattery);
    }
}
