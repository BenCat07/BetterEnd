package org.betterx.betterend.blocks;

import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.blocks.BaseBlock;
import org.betterx.bclib.interfaces.tools.AddMineableShears;
import org.betterx.betterend.registry.EndBlocks;
import org.betterx.wover.block.api.model.BlockModelProvider;
import org.betterx.wover.block.api.model.WoverBlockModelGenerators;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class GlowingPillarLuminophorBlock extends BaseBlock implements AddMineableShears, BlockModelProvider {
    public static final BooleanProperty NATURAL = EndBlockProperties.NATURAL;

    public GlowingPillarLuminophorBlock() {
        super(BehaviourBuilders
                .createMetal(MapColor.COLOR_ORANGE)
                .strength(0.2F)
                .lightLevel((bs) -> 15)
                .sound(SoundType.GRASS));
        this.registerDefaultState(this.stateDefinition.any().setValue(NATURAL, false));
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return !state.getValue(NATURAL) || world.getBlockState(pos.below()).is(EndBlocks.GLOWING_PILLAR_ROOTS);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(
            BlockState state,
            Direction facing,
            BlockState neighborState,
            LevelAccessor world,
            BlockPos pos,
            BlockPos neighborPos
    ) {
        if (!canSurvive(state, world, pos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            return state;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(NATURAL);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void provideBlockModels(WoverBlockModelGenerators generator) {
        GlowingHymenophoreBlock.provideUnshadedCubeModel(generator, this);
    }
}
