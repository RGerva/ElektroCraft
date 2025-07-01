/**
 * Generic Class: ChargerStationScreen <T>
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

package com.rgerva.elektrocraft.gui.screen;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.gui.menu.ChargerStationMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class ChargerStationScreen extends AbstractContainerScreen<ChargerStationMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, "textures/gui/container/charger_station_gui.png");

    public ChargerStationScreen(ChargerStationMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        // 🔲 Fundo
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        int progress = menu.getProgress();
        int maxProgress = menu.getMaxProgress();
        int storedVolts = menu.getStoredVolts();
        int maxVolts = menu.getMaxVolts();

        // ⚙️ Barra de progresso
        if (maxProgress > 0) {
            int progressWidth = 24;
            int scaled = (int) ((float) progress / maxProgress * progressWidth);
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE,
                    x + 80, y + 34,
                    176, 0,
                    scaled, 16,
                    256, 256
            );
        }

        // ⚡ Barra de energia
        if (maxVolts > 0) {
            int barHeight = 48;
            int scaled = (int) ((float) storedVolts / maxVolts * barHeight);
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE,
                    x + 11, y + 16 + (barHeight - scaled),
                    176, 16 + (barHeight - scaled),
                    6, scaled,
                    256, 256
            );
        }
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);

        int barX = leftPos + 11;
        int barY = topPos + 16;
        int barW = 6;
        int barH = 48;

        if (isHovering(barX, barY, barW, barH, mouseX, mouseY)) {
            int stored = menu.getStoredVolts();
            int max = menu.getMaxVolts();
            Component tooltip = Component.literal(stored + " V / " + max + " V");
            guiGraphics.renderTooltip(this.font, List.of((ClientTooltipComponent) tooltip), mouseX, mouseY, DefaultTooltipPositioner.INSTANCE, null);
        }

    }

    private boolean isHovering(int x, int y, int w, int h, int mouseX, int mouseY) {
        return mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + h;
    }

}
