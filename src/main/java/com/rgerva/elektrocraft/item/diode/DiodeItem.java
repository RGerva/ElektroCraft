/**
 * Generic Class: DiodeItem <T>
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

package com.rgerva.elektrocraft.item.diode;

import com.rgerva.elektrocraft.component.ModDataComponents;
import com.rgerva.elektrocraft.util.ModUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class DiodeItem extends Item {

    private static final double voltage = 0.7;

    public DiodeItem(Properties properties) {
        super(properties);
    }

    public static double getVoltage() {
        return voltage;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);

        if (Screen.hasShiftDown()) {
            double volt = getVoltage();
            if (stack.has(ModDataComponents.VOLTAGE.get())) {
                Double voltageObj = stack.get(ModDataComponents.VOLTAGE.get());
                if (voltageObj != null) {
                    volt = voltageObj;
                }
            }

            tooltipAdder.accept(Component.translatable("tooltip.elektrocraft.voltage")
                    .append(ModUtils.ModVoltageUtil.getVoltageWithPrefix(volt))
                    .withStyle(ChatFormatting.GRAY));
        } else {
            tooltipAdder.accept(Component.translatable("tooltip.elektrocraft.shift_details")
                    .withStyle(ChatFormatting.YELLOW));
        }
    }
}
