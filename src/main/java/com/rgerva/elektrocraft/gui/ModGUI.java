/**
 * Generic Class: ModGUI <T>
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

package com.rgerva.elektrocraft.gui;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.gui.menu.ChargingStationMenu;
import com.rgerva.elektrocraft.gui.menu.ResistorAssembleMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModGUI {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(BuiltInRegistries.MENU, ElektroCraft.MOD_ID);


    public static final Supplier<MenuType<ResistorAssembleMenu>> RESISTOR_ASSEMBLE_MENU =
            registerMenuType("resistor_assemble", ResistorAssembleMenu::new);

    public static final Supplier<MenuType<ChargingStationMenu>> CHARGER_STATION_MENU =
            registerMenuType("charger_station", ChargingStationMenu::new);


    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus modEventBus) {
        MENUS.register(modEventBus);
    }
}
