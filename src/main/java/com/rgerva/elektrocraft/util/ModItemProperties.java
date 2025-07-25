/**
 * Generic Class: ModItemProperties <T> A generic structure that works with type parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jun.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ModItemProperties {
  public enum ResistorColorCode {
    BLACK(0, "black", 0x000000, Items.BLACK_DYE),
    BROWN(1, "brown", 0x8B4513, Items.BROWN_DYE),
    RED(2, "red", 0xFF0000, Items.RED_DYE),
    ORANGE(3, "orange", 0xFFA500, Items.ORANGE_DYE),
    YELLOW(4, "yellow", 0xFFFF00, Items.YELLOW_DYE),
    GREEN(5, "green", 0x008000, Items.GREEN_DYE),
    BLUE(6, "blue", 0x0000FF, Items.BLUE_DYE),
    VIOLET(7, "violet", 0xEE82EE, Items.PURPLE_DYE),
    GRAY(8, "grey", 0x808080, Items.GRAY_DYE),
    WHITE(9, "white", 0xFFFFFF, Items.WHITE_DYE);

    private final int digit;
    private final String colorName;
    private final int hexColor;
    private final Item dyeItem;

    ResistorColorCode(int digit, String colorName, int hexColor, Item dyeItem) {
      this.digit = digit;
      this.colorName = colorName;
      this.hexColor = hexColor;
      this.dyeItem = dyeItem;
    }

    public static ResistorColorCode fromDyeColor(Item dyeColor) {
      for (ResistorColorCode color : values()) {
        if (color.getDyeItem() == dyeColor) {
          return color;
        }
      }
      throw new IllegalArgumentException("Unknow color: " + dyeColor);
    }

    public int getDigit() {
      return digit;
    }

    public String getColorName() {
      return colorName;
    }

    public int getHexColor() {
      return hexColor;
    }

    public Item getDyeItem() {
      return dyeItem;
    }
  }
}
