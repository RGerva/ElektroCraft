/**
 * Generic Class: ResistorAssembleEntity <T>
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

package com.rgerva.elektrocraft.block.entity.resistor;

import com.rgerva.elektrocraft.ElektroCraft;
import com.rgerva.elektrocraft.block.entity.ModBlockEntities;
import com.rgerva.elektrocraft.component.ModDataComponents;
import com.rgerva.elektrocraft.gui.menu.ResistorAssembleMenu;
import com.rgerva.elektrocraft.item.ModItems;
import com.rgerva.elektrocraft.util.ModItemProperties;
import com.rgerva.elektrocraft.util.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class ResistorAssembleEntity extends BlockEntity implements MenuProvider {
    public static final int BLANK_RESISTOR_SLOT = 0;
    public static final int FIRST_DYE_SLOT = 1;
    public static final int SECOND_DYE_SLOT = 2;
    public static final int THIRD_DYE_SLOT = 3;
    public static final int OUTPUT_SLOT = 4;

    public final ItemStackHandler itemHandler = new ItemStackHandler(5) {

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public ResistorAssembleEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.RESISTOR_ASSEMBLE_ENTITY.get(), pos, blockState);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.elektrocraft.resistor_assemble");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ResistorAssembleMenu(i, inventory, this);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, ResistorAssembleEntity entity) {
        if (level.isClientSide) {
            return;
        }

        ItemStack blank = entity.itemHandler.getStackInSlot(BLANK_RESISTOR_SLOT);
        ItemStack first = entity.itemHandler.getStackInSlot(FIRST_DYE_SLOT);
        ItemStack second = entity.itemHandler.getStackInSlot(SECOND_DYE_SLOT);
        ItemStack third = entity.itemHandler.getStackInSlot(THIRD_DYE_SLOT);
        ItemStack output = entity.itemHandler.getStackInSlot(OUTPUT_SLOT);

        if (blank.is(ModItems.BLANK_RESISTOR.get())
                && first.getItem() instanceof DyeItem dye1
                && second.getItem() instanceof DyeItem dye2
                && third.getItem() instanceof DyeItem dye3) {

            try {
                ModItemProperties.ResistorColorCode color1 = ModItemProperties.ResistorColorCode.fromDyeColor(first.getItem());
                ModItemProperties.ResistorColorCode color2 = ModItemProperties.ResistorColorCode.fromDyeColor(second.getItem());
                ModItemProperties.ResistorColorCode color3 = ModItemProperties.ResistorColorCode.fromDyeColor(third.getItem());

                int resistance = ModUtils.ModResistorUtil.calculateResistance(color1, color2, color3);

                boolean shouldCreate = true;

                if (!output.isEmpty() && output.is(ModItems.BLANK_RESISTOR.get())) {
                    Integer existingResistanceObj = output.get(ModDataComponents.RESISTANCE.get());
                    if (existingResistanceObj != null) {
                        int existingResistance = existingResistanceObj;
                        if (existingResistance == resistance) {
                            shouldCreate = false;
                        }
                    }
                }

                if (shouldCreate) {
                    ItemStack painted = new ItemStack(ModItems.BLANK_RESISTOR.get());
                    painted.set(ModDataComponents.RESISTANCE.get(), resistance);
                    entity.itemHandler.setStackInSlot(OUTPUT_SLOT, painted);

                    blank.shrink(1);
                    first.shrink(1);
                    second.shrink(1);
                    third.shrink(1);
                }

            } catch (IllegalArgumentException e) {
                ElektroCraft.LOGGER.warn("Wrong color code: {}", e.getMessage());
            }

        } else {
            if (!output.isEmpty()) {
                entity.itemHandler.setStackInSlot(OUTPUT_SLOT, ItemStack.EMPTY);
            }
        }
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        valueOutput.putChild("resistor_assemble.inventory", itemHandler);
        itemHandler.serialize(valueOutput);
        super.saveAdditional(valueOutput);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);

        itemHandler.deserialize(valueInput);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public void onDataPacket(Connection net, ValueInput valueInput) {
        super.onDataPacket(net, valueInput);
        loadAdditional(valueInput);
    }
}
