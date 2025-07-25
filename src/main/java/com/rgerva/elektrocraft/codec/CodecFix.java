/**
 * Generic Class: CodecFix <T> A generic structure that works with type parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jul.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.codec;

import com.mojang.serialization.Codec;
import net.minecraft.world.item.ItemStack;

public class CodecFix {
  private CodecFix() {}

  public static final Codec<ItemStack> ITEM_STACK_CODEC = ItemStack.CODEC;
}
