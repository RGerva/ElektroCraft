/**
 * Generic Class: ModItems <T>
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

package com.rgerva.elektrocraft.item;

import com.rgerva.elektrocraft.ElektroCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class ModItems {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(ElektroCraft.MOD_ID);

    public static final DeferredItem<Item> HAMMER = ITEMS.registerItem("hammer",
            (properties) -> new Item(properties
                    .durability(10)
                    .stacksTo(1)) {
                @Override
                public ItemStack getCraftingRemainder(ItemStack itemStack) {
                    ItemStack result = itemStack.copy();
                    result.setDamageValue(result.getDamageValue() + 1);
                    if (result.getDamageValue() >= result.getMaxDamage()) {
                        return ItemStack.EMPTY;
                    }
                    return result;
                }
            });

    public static final DeferredItem<Item> CAPACITOR = ITEMS.registerItem("capacitor",
            (properties) -> new Item(properties));

    public static final DeferredItem<Item> DIODE = ITEMS.registerItem("diode",
            (properties -> new Item(properties)));

    public static final DeferredItem<Item> INDUCTOR = ITEMS.registerItem("inductor",
            (properties -> new Item(properties)));

    public static final DeferredItem<Item> SILICON = ITEMS.registerItem("silicon",
            (properties -> new Item(properties)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
