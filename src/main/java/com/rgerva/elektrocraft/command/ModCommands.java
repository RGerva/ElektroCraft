/**
 * Generic Class: ModCommands <T> A generic structure that works with type parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jul.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.rgerva.elektrocraft.config.ModConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class ModCommands {

  public static void configCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
    dispatcher.register(
        Commands.literal("elektrocraft")
            .then(
                Commands.literal("config")
                    .executes(
                        ctx -> {
                          int fePerVolt = ModConfig.fePerVolt;
                          ctx.getSource()
                              .sendSuccess(
                                  () ->
                                      Component.literal(
                                          "Actual config: 1 Volt = " + fePerVolt + " FE"),
                                  false);
                          return Command.SINGLE_SUCCESS;
                        })));
  }
}
