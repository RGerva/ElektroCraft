/**
 * Generic Class: ResistorAssembleMenu <T>
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

package com.rgerva.elektrocraft.gui.menu;

import com.rgerva.elektrocraft.block.ModBlocks;
import com.rgerva.elektrocraft.block.entity.station.ResistorAssembleEntity;
import com.rgerva.elektrocraft.gui.ModGUI;
import com.rgerva.elektrocraft.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ResistorAssembleMenu extends AbstractContainerMenu {

    private final Level level;
    private final ResistorAssembleEntity entity;

    public ResistorAssembleMenu(int pContainerId, Inventory inv, FriendlyByteBuf buffer) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(buffer.readBlockPos()));
    }

    public ResistorAssembleMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(ModGUI.RESISTOR_ASSEMBLE_MENU.get(), containerId);
        this.level = inventory.player.level();
        this.entity = (ResistorAssembleEntity) blockEntity;

        this.addSlot(new SlotItemHandler(this.entity.itemHandler, ResistorAssembleEntity.BLANK_RESISTOR_SLOT, 9, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItems.BLANK_RESISTOR.get());
            }
        });

        this.addSlot(new SlotItemHandler(this.entity.itemHandler, ResistorAssembleEntity.FIRST_DYE_SLOT, 48, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() instanceof DyeItem;
            }
        });

        this.addSlot(new SlotItemHandler(this.entity.itemHandler, ResistorAssembleEntity.SECOND_DYE_SLOT, 68, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() instanceof DyeItem;
            }
        });

        this.addSlot(new SlotItemHandler(this.entity.itemHandler, ResistorAssembleEntity.THIRD_DYE_SLOT, 87, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() instanceof DyeItem;
            }
        });

        this.addSlot(new SlotItemHandler(this.entity.itemHandler, ResistorAssembleEntity.OUTPUT_SLOT, 147, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++) {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++) {
                this.addSlot(new Slot(inventory, playerInvCol + playerInvRow * 9 + 9, 8 + playerInvCol * 18, 84 + playerInvRow * 18));
            }
        }

        for (int hotHarSlot = 0; hotHarSlot < 9; hotHarSlot++) {
            this.addSlot(new Slot(inventory, hotHarSlot, 8 + hotHarSlot * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        ItemStack slotStackCopy = ItemStack.EMPTY;
        Slot slot = slots.get(pIndex);

        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            slotStackCopy = slotStack.copy();

            // Se o item está nos slots de entrada (0-3), mover para inventário do jogador
            if (pIndex >= 0 && pIndex <= 3) {
                if (!moveItemStackTo(slotStack, 4, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotStack, slotStackCopy);
            } else {
                if (isResistor(slotStack)) {
                    if (!moveItemStackTo(slotStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (isDye(slotStack)) {
                    if (!moveItemStackTo(slotStack, 1, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    return ItemStack.EMPTY;
                }
            }

            if (slotStack.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == slotStackCopy.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, slotStack);
            broadcastChanges();
        }

        return slotStackCopy;
    }


    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, entity.getBlockPos()),
                player, ModBlocks.RESISTOR_ASSEMBLE.get());
    }

    private boolean isResistor(ItemStack stack) {
        return stack.getItem() == ModItems.BLANK_RESISTOR.get();
    }

    private boolean isDye(ItemStack stack) {
        return stack.getItem() instanceof DyeItem;
    }
}
