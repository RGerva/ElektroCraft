/**
 * Generic Class: CapacitorItem <T> A generic structure that works with type parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jun.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.item.capacitor;

import com.rgerva.elektrocraft.component.ModDataComponents;
import com.rgerva.elektrocraft.util.ModUtils;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

public class CapacitorItem extends Item {

  private static final double defaultCapacitance = 0.0;

  public CapacitorItem(Properties properties) {
    super(properties);
  }

  public static double getDefaultCapacitance() {
    return defaultCapacitance;
  }

  @Override
  @SuppressWarnings("deprecation")
  public void appendHoverText(
      ItemStack stack,
      TooltipContext context,
      TooltipDisplay tooltipDisplay,
      Consumer<Component> tooltipAdder,
      TooltipFlag flag) {
    super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);

    if (Screen.hasShiftDown()) {
      double capacity = getDefaultCapacitance();
      if (stack.has(ModDataComponents.CAPACITANCE.get())) {
        Double capacitanceObj = stack.get(ModDataComponents.CAPACITANCE.get());
        if (capacitanceObj != null) {
          capacity = capacitanceObj;
        }
      }

      tooltipAdder.accept(
          Component.translatable("tooltip.elektrocraft.capacitance")
              .append(ModUtils.ModCapacitanceUtil.getCapacitanceWithPrefix(capacity))
              .withStyle(ChatFormatting.GRAY));
    } else {
      tooltipAdder.accept(
          Component.translatable("tooltip.elektrocraft.shift_details")
              .withStyle(ChatFormatting.YELLOW));
    }
  }
}
