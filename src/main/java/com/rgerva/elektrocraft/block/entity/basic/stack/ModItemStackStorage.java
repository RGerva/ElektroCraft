/**
 * Generic Class: ModItemStackStorage <T>
 * A generic structure that works with type parameters.
 * <p>
 * Created by: D56V1OK
 * On: 2025/jul.
 * <p>
 * GitHub: https://github.com/RGerva
 * <p>
 * Copyright (c) 2025 @RGerva. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.rgerva.elektrocraft.block.entity.basic.stack;

import net.neoforged.neoforge.items.ItemStackHandler;

public class ModItemStackStorage extends ItemStackHandler {

    private final Runnable onContentsChanged;

    public ModItemStackStorage(int size, Runnable onContentsChanged) {
        super(size);
        this.onContentsChanged = onContentsChanged;
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        if (onContentsChanged != null) onContentsChanged.run();
    }
}
