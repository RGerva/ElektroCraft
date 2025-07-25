/**
 * Generic Class: ChargerStationScreen <T> A generic structure that works with type parameters.
 *
 * <p>Created by: D56V1OK On: 2025/jul.
 *
 * <p>GitHub: https://github.com/RGerva
 *
 * <p>Copyright (c) 2025 @RGerva. All Rights Reserved.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.rgerva.elektrocraft.gui.screen;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.block.entity.station.ChargingStationEntity;
import com.rgerva.elektrocraft.gui.menu.ChargingStationMenu;
import com.rgerva.elektrocraft.util.ModUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ChargingStationScreen extends AbstractContainerScreen<ChargingStationMenu> {
  private static final ResourceLocation TEXTURE =
      ResourceLocation.fromNamespaceAndPath(
          ElektroCraft.MOD_ID, "textures/gui/container/charger_station_gui.png");

  private static final ResourceLocation TEXTURE_SPRITES =
      ResourceLocation.fromNamespaceAndPath(
          ElektroCraft.MOD_ID, "textures/gui/container/sprites/machine_sprites.png");

  public ChargingStationScreen(
      ChargingStationMenu menu, Inventory playerInventory, Component title) {
    super(menu, playerInventory, title);
    this.imageWidth = 176;
    this.imageHeight = 166;
  }

  @Override
  public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
    super.render(guiGraphics, mouseX, mouseY, partialTick);
    this.renderTooltip(guiGraphics, mouseX, mouseY);
  }

  @Override
  protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
    int x = (this.width - this.imageWidth) / 2;
    int y = (this.height - this.imageHeight) / 2;

    guiGraphics.blit(
        RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

    setProgressArrow(guiGraphics, x, y);
    renderEnergyBar(guiGraphics, x, y);
  }

  public void setProgressArrow(GuiGraphics guiGraphics, int x, int y) {
    int progress = menu.getProgress();
    int maxProgress = menu.getMaxProgress();
    int progressArrowSize = 24;

    int scaled =
        (maxProgress == 0 || progress == 0) ? 0 : progress * progressArrowSize / maxProgress;

    guiGraphics.blit(
        RenderPipelines.GUI_TEXTURED,
        TEXTURE_SPRITES,
        x + 100,
        y + 36,
        0,
        58,
        scaled,
        17,
        256,
        256);
  }

  @Override
  protected void renderTooltip(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
    super.renderTooltip(guiGraphics, mouseX, mouseY);

    if (isHovering(7, 18, 15, 53, mouseX, mouseY)) {
      List<Component> components = new ArrayList<>();

      if (AbstractContainerScreen.hasShiftDown()) {
        components.add(
            Component.translatable(
                "tooltip.elektrocraft.energy_bar_fe",
                ModUtils.ModUnits.toFE(menu.getClientStoredVolts()),
                ModUtils.ModUnits.toFE(menu.getClientMaxVolts())));

        components.add(
            Component.translatable(
                "tooltip.elektrocraft.energy_usage.fe",
                ModUtils.ModUnits.toFE(ChargingStationEntity.VOLTAGE_USAGE)));
      } else {
        components.add(
            Component.translatable(
                "tooltip.elektrocraft.energy_bar_volts",
                menu.getClientStoredVolts(),
                menu.getClientMaxVolts()));

        components.add(
            Component.translatable(
                "tooltip.elektrocraft.energy_usage.volts", ChargingStationEntity.VOLTAGE_USAGE));

        components.add(
            Component.translatable("tooltip.elektrocraft.shift_details")
                .withStyle(ChatFormatting.YELLOW));
      }
      guiGraphics.setTooltipForNextFrame(font, components, Optional.empty(), mouseX, mouseY);
    }
  }

  @Override
  protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    guiGraphics.drawString(
        this.font, this.title, this.titleLabelX, this.titleLabelY, -12566464, false);
    guiGraphics.drawString(
        this.font,
        this.playerInventoryTitle,
        this.inventoryLabelX,
        this.inventoryLabelY + 4,
        -12566464,
        false);
  }

  private float displayedVolts = 0;

  private void renderEnergyBar(GuiGraphics guiGraphics, int x, int y) {
    int stored = menu.getClientStoredVolts();
    int capacity = menu.getClientMaxVolts();
    if (capacity <= 0) return;

    int maxHeight = 53;

    displayedVolts = Mth.lerp(0.1F, displayedVolts, stored);

    int fill = (int) ((displayedVolts / capacity) * maxHeight);
    fill = Mth.clamp(fill, 0, maxHeight + 1);

    guiGraphics.blit(
        RenderPipelines.GUI_TEXTURED,
        TEXTURE_SPRITES,
        x + 8,
        y + 18 + (maxHeight - fill),
        0,
        52 - fill,
        16,
        fill,
        256,
        256);
  }
}
