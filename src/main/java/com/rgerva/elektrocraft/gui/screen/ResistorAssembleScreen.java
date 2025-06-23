/**
 * Generic Class: ResistorAssembleScreen <T>
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

package com.rgerva.elektrocraft.gui.screen;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.gui.menu.ResistorAssembleMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ResistorAssembleScreen extends AbstractContainerScreen<ResistorAssembleMenu> {

    protected final ResourceLocation TEXTURE;

    public ResistorAssembleScreen(ResistorAssembleMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        TEXTURE = ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, "textures/gui/container/resistor_assemble_gui.png");
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        super.renderTooltip(guiGraphics, x, y);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                TEXTURE,
                x,
                y,
                0,
                0,
                imageWidth,
                imageHeight,
                256,
                256);
    }
}
