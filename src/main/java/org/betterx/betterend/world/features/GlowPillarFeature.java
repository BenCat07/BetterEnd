package org.betterx.betterend.world.features;

import org.betterx.betterend.blocks.basis.EndPlantWithAgeBlock;
import org.betterx.betterend.registry.EndBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;

public class GlowPillarFeature extends ScatterFeature<ScatterFeatureConfig> {
    public GlowPillarFeature() {
        super(ScatterFeatureConfig.CODEC);
    }

    @Override
    public boolean canGenerate(
            ScatterFeatureConfig cfg,
            WorldGenLevel world,
            RandomSource random,
            BlockPos center,
            BlockPos blockPos,
            float radius
    ) {
        return EndBlocks.GLOWING_PILLAR_SEED.defaultBlockState().canSurvive(world, blockPos);
    }

    @Override
    public void generate(
            ScatterFeatureConfig cfg, WorldGenLevel world, RandomSource random, BlockPos blockPos
    ) {
        EndPlantWithAgeBlock seed = ((EndPlantWithAgeBlock) EndBlocks.GLOWING_PILLAR_SEED);
        seed.growAdult(world, random, blockPos);
    }

    @Override
    protected int getChance() {
        return 10;
    }
}
