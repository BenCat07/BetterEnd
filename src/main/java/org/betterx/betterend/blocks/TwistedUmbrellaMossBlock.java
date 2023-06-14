package org.betterx.betterend.blocks;

import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.behaviours.interfaces.BehaviourPlant;
import org.betterx.bclib.blocks.BaseDoublePlantBlock;
import org.betterx.bclib.util.BlocksHelper;
import org.betterx.betterend.blocks.basis.EndPlantBlock;
import org.betterx.betterend.interfaces.survives.SurvivesOnJungleMossOrMycelium;
import org.betterx.betterend.registry.EndBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class TwistedUmbrellaMossBlock extends EndPlantBlock implements BehaviourPlant, SurvivesOnJungleMossOrMycelium {
    public TwistedUmbrellaMossBlock() {
        super(BehaviourBuilders.createPlant(MapColor.COLOR_BLUE).lightLevel((state) -> 12));
    }

    @Environment(EnvType.CLIENT)
    public boolean hasEmissiveLighting(BlockGetter world, BlockPos pos) {
        return true;
    }

    @Environment(EnvType.CLIENT)
    public float getAmbientOcclusionLightLevel(BlockGetter world, BlockPos pos) {
        return 1F;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return world.isEmptyBlock(pos.above());
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        int rot = world.random.nextInt(4);
        BlockState bs = EndBlocks.TWISTED_UMBRELLA_MOSS_TALL.defaultBlockState()
                                                            .setValue(BaseDoublePlantBlock.ROTATION, rot);
        BlocksHelper.setWithoutUpdate(world, pos, bs);
        BlocksHelper.setWithoutUpdate(world, pos.above(), bs.setValue(BaseDoublePlantBlock.TOP, true));
    }

    @Override
    public boolean isTerrain(BlockState state) {
        return SurvivesOnJungleMossOrMycelium.super.isTerrain(state);
    }
}
