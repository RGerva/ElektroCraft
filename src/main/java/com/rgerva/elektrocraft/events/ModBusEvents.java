/**
 * Generic Class: ModBusEvents <T>
 * A generic structure that works with type parameters.
 * <p>
 * Created by: D56V1OK
 * On: 2025/jun.
 * <p>
 * GitHub: https://github.com/RGerva
 * <p>
 * Copyright (c) 2025 @RGerva. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.rgerva.elektrocraft.events;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.gui.ModGUI;
import com.rgerva.elektrocraft.gui.screen.ResistorAssembleScreen;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = ElektroCraft.MOD_ID)
public class ModBusEvents {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        ElektroCraft.LOGGER.info("HELLO FROM CLIENT SETUP");
        ElektroCraft.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {

        event.register(ModGUI.RESISTOR_ASSEMBLE_MENU.get(), ResistorAssembleScreen::new);
    }
}
