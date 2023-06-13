package org.betterx.betterend.blocks;

import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.blocks.BaseBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public class HydraluxPetalBlock extends BaseBlock.Wood {
    public HydraluxPetalBlock() {
        this(
                BehaviourBuilders
                        .createWalkablePlant(MapColor.PODZOL)
                        .strength(1)
                        .sound(SoundType.WART_BLOCK)
        );
    }

    public HydraluxPetalBlock(Properties settings) {
        super(settings);
    }

    @Override
    public void fallOn(Level level, BlockState blockState, BlockPos blockPos, Entity entity, float f) {
    }
}
