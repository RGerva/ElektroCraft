/**
 * Generic Class: ModItems <T>
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

package com.rgerva.elektrocraft.item;

import com.rgerva.elektrocraft.ElektroCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ElektroCraft.MOD_ID);

    //region ORES

    public static final DeferredItem<Item> LEAD_INGOT = ITEMS.register("lead_ingot",
            () -> new Item(new Item.Properties().setId(id("lead_ingot"))));

    public static final DeferredItem<Item> LEAD_NUGGET = ITEMS.register("lead_nugget",
            () -> new Item(new Item.Properties().setId(id("lead_nugget"))));

    public static final DeferredItem<Item> LEAD_RAW = ITEMS.register("lead_raw",
            () -> new Item(new Item.Properties().setId(id("lead_raw"))));

    public static final DeferredItem<Item> LEAD_DUST = ITEMS.register("lead_dust",
            () -> new Item(new Item.Properties().setId(id("lead_dust"))));

    public static final DeferredItem<Item> TIN_INGOT = ITEMS.register("tin_ingot",
            () -> new Item(new Item.Properties().setId(id("tin_ingot"))));

    public static final DeferredItem<Item> TIN_NUGGET = ITEMS.register("tin_nugget",
            () -> new Item(new Item.Properties().setId(id("tin_nugget"))));

    public static final DeferredItem<Item> TIN_RAW = ITEMS.register("tin_raw",
            () -> new Item(new Item.Properties().setId(id("tin_raw"))));

    public static final DeferredItem<Item> TIN_DUST = ITEMS.register("tin_dust",
            () -> new Item(new Item.Properties().setId(id("tin_dust"))));

    public static final DeferredItem<Item> TIN_SOLDER = ITEMS.register("tin_solder",
            () -> new Item(new Item.Properties().setId(id("tin_solder"))));

    //endregion

    public static final DeferredItem<Item> HAMMER = ITEMS.register("hammer",
            () -> new Item(new Item.Properties().setId(id("hammer")).durability(10).stacksTo(1)));

    public static final DeferredItem<Item> TIN_SOLDER_WIRE = ITEMS.register("tin_solder_wire",
            () -> new Item(new Item.Properties().setId(id("tin_solder_wire"))));

    protected static ResourceKey<Item> id(@NotNull String path) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, path));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
