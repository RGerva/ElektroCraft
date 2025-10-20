/**
 * Generic Class: SolarPanelScreen <T>
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

package com.rgerva.elektrocraft.screen.custom.generator;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.block_entities.custom.generator.SolarPanelBlockEntity;
import com.rgerva.elektrocraft.screen.menu.generator.SolarPanelMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class SolarPanelScreen extends AbstractContainerScreen<SolarPanelMenu> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ElektroCraft.MOD_ID, "textures/gui/solar_panel.png");
    private final SolarPanelBlockEntity entity;

    public SolarPanelScreen(SolarPanelMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.entity = menu.entity;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);

        if (mouseX > leftPos + 7 && mouseX < leftPos + 29 && mouseY > topPos + 10 && mouseY < topPos + 77) {
            Component component = Component.translatable("gui.elektrocraft.energy", getPercent());
            ClientTooltipComponent clientTooltipComponent = ClientTooltipComponent.create(component.getVisualOrderText());

            guiGraphics.renderTooltip(font, List.of(clientTooltipComponent), mouseX, mouseY, DefaultTooltipPositioner.INSTANCE, null);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Component energy = Component.translatable("gui.elektrocraft.stored_energy", getEnergyFormatted(entity.energyClient));
        guiGraphics.drawString(font, energy, (imageWidth / 2 - font.width(energy) / 2) + 14, 20,
                0xFF333333, false);

        Component maxEnergy = Component.translatable("gui.elektrocraft.capacity", getEnergyFormatted(entity.getCapacity()));
        guiGraphics.drawString(font, maxEnergy, (imageWidth / 2 - font.width(maxEnergy) / 2) + 14, 30,
                0xFF333333, false);

        Component generation = Component.translatable("gui.elektrocraft.generation", entity.energyProductionClient);
        guiGraphics.drawString(font, generation, (imageWidth / 2 - font.width(generation) / 2) + 14, 40,
                0xFF333333, false);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouse) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos, this.topPos, 0, 0,
                this.imageWidth, this.imageHeight, 256, 256);

        int y = this.getEnergyScaled(60);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos + 10, this.topPos + 12 + y,
                this.imageWidth, 0, 16, 60 - y, 256, 256);
    }

    private int getEnergyScaled(int pixels) {
        return pixels - (pixels * getPercent() / 100);
    }

    private String getEnergyFormatted(int energy) {
        if (energy >= 10000) {
            return (energy / 1000) + " kFE";
        } else {
            return energy + " FE";
        }
    }

    private int getPercent() {
        long currentEnergy = entity.energyClient;
        int maxEnergy = entity.getCapacity();

        long result = currentEnergy * 100 / maxEnergy;

        return (int) result;
    }
}
