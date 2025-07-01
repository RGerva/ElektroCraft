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
import com.rgerva.elektrocraft.gui.screen.ChargerStationScreen;
import com.rgerva.elektrocraft.gui.screen.ResistorAssembleScreen;
import com.rgerva.elektrocraft.util.ModUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.Locale;
import java.util.OptionalDouble;

@EventBusSubscriber(modid = ElektroCraft.MOD_ID, value = Dist.CLIENT)
public class ModBusEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        ElektroCraft.LOGGER.info("HELLO FROM CLIENT SETUP");
        ElektroCraft.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModGUI.RESISTOR_ASSEMBLE_MENU.get(), ResistorAssembleScreen::new);
        event.register(ModGUI.CHARGER_STATION_MENU.get(), ChargerStationScreen::new);
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        OptionalDouble eps = ModUtils.ModCapacitanceUtil.getDielectricConstant(item);
        if (eps.isPresent()) {
            if(Screen.hasShiftDown()){
                double value = eps.getAsDouble();
                String formatted = String.format(Locale.ENGLISH, "ε = %.2f", value);
                event.getToolTip()
                        .add(Component.literal(formatted)
                        .withStyle(ChatFormatting.AQUA));
            }else{
                event.getToolTip().add(Component.translatable("tooltip.elektrocraft.shift_details")
                        .withStyle(ChatFormatting.YELLOW));
            }
        }
    }
}
