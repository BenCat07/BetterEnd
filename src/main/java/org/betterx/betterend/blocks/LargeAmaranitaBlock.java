package org.betterx.betterend.blocks;

import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.behaviours.interfaces.BehaviourPlant;
import org.betterx.bclib.blocks.BlockProperties;
import org.betterx.bclib.blocks.BlockProperties.TripleShape;
import org.betterx.betterend.blocks.basis.EndPlantBlock;
import org.betterx.betterend.interfaces.survives.SurvivesOnEndBone;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LargeAmaranitaBlock extends EndPlantBlock implements BehaviourPlant, SurvivesOnEndBone {
    public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
    private static final VoxelShape SHAPE_BOTTOM = Block.box(4, 0, 4, 12, 14, 12);
    private static final VoxelShape SHAPE_TOP = Shapes.or(Block.box(1, 3, 1, 15, 16, 15), SHAPE_BOTTOM);

    public LargeAmaranitaBlock() {
        super(BehaviourBuilders
                .createWalkablePlant(MapColor.COLOR_RED)
                .ignitedByLava()
                .lightLevel((state) -> (state.getValue(SHAPE) == TripleShape.TOP) ? 15 : 0)
        );
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
        return state.getValue(SHAPE) == TripleShape.TOP ? SHAPE_TOP : SHAPE_BOTTOM;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(SHAPE);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        TripleShape shape = state.getValue(SHAPE);
        if (shape == TripleShape.BOTTOM) {
            return isTerrain(world.getBlockState(pos.below())) && world.getBlockState(pos.above()).is(this);
        } else if (shape == TripleShape.TOP) {
            return world.getBlockState(pos.below()).is(this);
        } else {
            return world.getBlockState(pos.below()).is(this) && world.getBlockState(pos.above()).is(this);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public boolean isTerrain(BlockState state) {
        return SurvivesOnEndBone.super.isTerrain(state);
    }
}
