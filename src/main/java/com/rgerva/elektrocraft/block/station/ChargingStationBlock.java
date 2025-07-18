/**
 * Generic Class: ChargingStationBlock <T>
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

package com.rgerva.elektrocraft.block.station;

import com.mojang.serialization.MapCodec;
import com.rgerva.elektrocraft.block.entity.ModBlockEntities;
import com.rgerva.elektrocraft.block.entity.station.ChargingStationEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ChargingStationBlock extends BaseEntityBlock {

    private static final MapCodec<ChargingStationBlock> CODEC = simpleCodec(ChargingStationBlock::new);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public ChargingStationBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ChargingStationEntity(blockPos, blockState);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.CHARGING_STATION_ENTITY.get(), ChargingStationEntity::tick);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos blockPos, RandomSource random) {
        if (!level.isClientSide) return;

        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (!(blockEntity instanceof ChargingStationEntity station)) return;

        double x = blockPos.getX() + 0.5;
        double y = blockPos.getY() + 0.9;
        double z = blockPos.getZ() + 0.5;

        int progress = station.getProgress();
        int maxProgress = station.getMaxProgress();
        long stored = station.getEnergy().getEnergyStored();
        long capacity = station.getEnergy().getMaxEnergyStored();

        if (stored == 0) return;

        if (progress > 0 && progress < maxProgress) {
            int count = 3 + random.nextInt(2);
            for (int i = 0; i < count; i++) {
                double dx = x + (random.nextDouble() - 0.5) * 0.4;
                double dy = y + random.nextDouble() * 0.2;
                double dz = z + (random.nextDouble() - 0.5) * 0.4;

                float shade = 0.6F + random.nextFloat() * 0.3F;
                int dustColor = ((int)(shade * 255) << 16) | ((int)(shade * 255) << 8) | (int)(shade * 255);

                level.addParticle(new DustParticleOptions(dustColor, 1.0F), dx, dy, dz, 0.0, 0.001, 0.0);
            }

            float progressPercent = (float) progress / maxProgress;
            float energyPercent = capacity > 0 ? (float) stored / capacity : 0F;

            boolean isInVoltageArcWindow =
                    progressPercent >= 0.7F && energyPercent >= 0.05F && energyPercent <= 0.2F;

            if (isInVoltageArcWindow && random.nextFloat() < 0.2F) {
                int sparkCount = 3 + random.nextInt(3);
                for (int i = 0; i < sparkCount; i++) {
                    double dx = (random.nextDouble() - 0.5) * 0.3;
                    double dy = 0.1 + random.nextDouble() * 0.4;
                    double dz = (random.nextDouble() - 0.5) * 0.3;

                    double motionX = (random.nextDouble() - 0.5) * 0.02;
                    double motionY = 0.02 + random.nextDouble() * 0.02;
                    double motionZ = (random.nextDouble() - 0.5) * 0.02;

                    level.addParticle(ParticleTypes.ELECTRIC_SPARK, x + dx, y + dy, z + dz, motionX, motionY, motionZ);
                }

            }
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        BlockEntity entity = level.getBlockEntity(pos);
        if(entity instanceof ChargingStationEntity){
            player.openMenu((ChargingStationEntity) entity, pos);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        BlockEntity entity = level.getBlockEntity(pos);
        if (entity instanceof ChargingStationEntity) {
            return ((ChargingStationEntity) entity).getSignalStrength();
        }
        return 0;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ChargingStationEntity entity) {
            entity.drops();
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
