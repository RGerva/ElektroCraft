/**
 * Generic Class: SolarPanelBlock <T>
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

package com.rgerva.elektrocraft.block.custom.generator;

import com.mojang.serialization.MapCodec;
import com.rgerva.elektrocraft.block_entities.ModBlockEntities;
import com.rgerva.elektrocraft.block_entities.custom.generator.SolarPanelBlockEntity;
import com.rgerva.elektrocraft.capabilities.ModCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class SolarPanelBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    private static final MapCodec<SolarPanelBlock> CODEC = simpleCodec(SolarPanelBlock::new);
    private static final VoxelShape SHAPE = createShape();
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public SolarPanelBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(getStateDefinition().any().setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    private static VoxelShape createShape() {
        ArrayList<VoxelShape> shapes = new ArrayList<>();
        shapes.add(box(0, 0, 0, 16, 1, 16));//bottom
        shapes.add(box(7, 1, 7, 9, 9, 9));//mainpillar
        shapes.add(box(6, 1, 9, 7, 9, 10));//pillar1
        shapes.add(box(9, 1, 9, 10, 9, 10));//pillar2
        shapes.add(box(9, 1, 6, 10, 9, 7));//pillar3
        shapes.add(box(6, 1, 6, 7, 9, 7));//pillar4
        shapes.add(box(0, 9, 0, 16, 12, 16));//top

        VoxelShape combinedShape = Shapes.empty();
        for (VoxelShape shape : shapes) {
            combinedShape = Shapes.joinUnoptimized(combinedShape, shape, BooleanOp.OR);
        }
        return combinedShape;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceLiquid(@Nullable LivingEntity entity, BlockGetter blockGetter, BlockPos pos, BlockState state, Fluid fluid) {
        return SimpleWaterloggedBlock.super.canPlaceLiquid(entity, blockGetter, pos, state, fluid);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SolarPanelBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.SOLAR_PANEL_ENTITY.get(), SolarPanelBlockEntity::tick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player instanceof ServerPlayer serverPlayer) {
            level.getBlockEntity(pos, ModBlockEntities.SOLAR_PANEL_ENTITY.get()).ifPresent(solarPanelBlockEntity ->
                    serverPlayer.openMenu(solarPanelBlockEntity, pos));
        }

        return InteractionResult.SUCCESS_SERVER;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, ItemStack toolStack, boolean willHarvest, FluidState fluid) {
        return willHarvest || super.onDestroyedByPlayer(state, level, pos, player, toolStack, false, fluid);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
        level.removeBlock(pos, false);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SolarPanelBlockEntity) {
                EnergyHandler energyStored = level.getCapability(Capabilities.Energy.BLOCK, pos, null);
                int energy = stack.getOrDefault(ModCapabilities.MOD_ENERGY.get(), 0);
                if (energyStored != null) {
                    energyStored.insert(energy, null);
                }
            }
        }

        super.setPlacedBy(level, pos, state, placer, stack);
    }
}
