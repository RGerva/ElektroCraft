/**
 * Generic Class: ModGameEvents <T> A generic structure that works with type parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jun.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.events;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.command.ModCommands;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

@EventBusSubscriber(modid = ElektroCraft.MOD_ID, value = Dist.DEDICATED_SERVER)
public class ModGameEvents {

  @SubscribeEvent
  public static void onCommandsRegister(RegisterCommandsEvent event) {
    ConfigCommand.register(event.getDispatcher());
    ModCommands.configCommand(event.getDispatcher());
  }
}
