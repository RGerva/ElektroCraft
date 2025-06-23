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
import com.rgerva.elektrocraft.block.entity.resistor.ResistorAssembleEntity;
import com.rgerva.elektrocraft.gui.ModGUI;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
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

        this.addSlot(new SlotItemHandler(this.entity.itemHandler, ResistorAssembleEntity.BLANK_RESISTOR_SLOT, 40, 35));
        this.addSlot(new SlotItemHandler(this.entity.itemHandler, ResistorAssembleEntity.FIRST_DYE_SLOT, 60, 35));
        this.addSlot(new SlotItemHandler(this.entity.itemHandler, ResistorAssembleEntity.SECOND_DYE_SLOT, 80, 35));
        this.addSlot(new SlotItemHandler(this.entity.itemHandler, ResistorAssembleEntity.THIRD_DYE_SLOT, 100, 35));
        this.addSlot(new SlotItemHandler(this.entity.itemHandler, ResistorAssembleEntity.OUTPUT_SLOT, 140, 35){
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

            if (pIndex < 1) {
                if (!moveItemStackTo(slotStack, 1, 37, true)){
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotStack, slotStackCopy);
            }
            else if (!moveItemStackTo(slotStack, 0, 1, false)){
                return ItemStack.EMPTY;
            }

            if (slotStack.getCount() == 0){
                slot.set(ItemStack.EMPTY);
            }
            else{
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
}
