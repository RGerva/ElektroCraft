/**
 * Generic Class: ResistorItem <T> A generic structure that works with type parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jun.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.item.resistor;

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

public class ResistorItem extends Item {

  private final int defaultResistance;

  public ResistorItem(Properties properties, int defaultResistance) {
    super(properties);
    this.defaultResistance = defaultResistance;
  }

  @SuppressWarnings("deprecation")
  @Override
  public void appendHoverText(
      ItemStack stack,
      TooltipContext context,
      TooltipDisplay tooltipDisplay,
      Consumer<Component> tooltipAdder,
      TooltipFlag flag) {
    super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);

    if (Screen.hasShiftDown()) {
      int resistance = this.defaultResistance;
      if (stack.has(ModDataComponents.RESISTANCE.get())) {
        Integer resistanceObj = stack.get(ModDataComponents.RESISTANCE.get());
        if (resistanceObj != null) {
          resistance = resistanceObj;
        }
      }

      tooltipAdder.accept(
          Component.translatable("tooltip.elektrocraft.resistance")
              .append(ModUtils.ModResistorUtil.getResistanceWithPrefix(resistance))
              .withStyle(ChatFormatting.GRAY));
    } else {
      tooltipAdder.accept(
          Component.translatable("tooltip.elektrocraft.shift_details")
              .withStyle(ChatFormatting.YELLOW));
    }
  }

  public int getDefaultResistance() {
    return defaultResistance;
  }
}
