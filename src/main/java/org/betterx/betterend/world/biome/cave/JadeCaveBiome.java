package org.betterx.betterend.world.biome.cave;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeSettings;
import org.betterx.bclib.interfaces.SurfaceMaterialProvider;
import org.betterx.bclib.util.WeightedList;
import org.betterx.betterend.noise.OpenSimplexNoise;
import org.betterx.betterend.registry.EndBlocks;
import org.betterx.betterend.world.biome.EndBiomeBuilder;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JadeCaveBiome extends EndCaveBiome.Config {
    public static final MapCodec<Biome> CODEC = EndCaveBiome.simpleCaveBiomeCodec(JadeCaveBiome.Biome::new);
    public static final KeyDispatchDataCodec<JadeCaveBiome.Biome> KEY_CODEC = KeyDispatchDataCodec.of(CODEC);

    public static class Biome extends EndCaveBiome {
        private static final OpenSimplexNoise WALL_NOISE = new OpenSimplexNoise("jade_cave".hashCode());
        private static final OpenSimplexNoise DEPTH_NOISE = new OpenSimplexNoise("depth_noise".hashCode());
        private static final BlockState[] JADE = new BlockState[3];


        public Biome(ResourceKey<net.minecraft.world.level.biome.Biome> biomeID, BCLBiomeSettings settings) {
            super(biomeID, settings);
        }

        @Override
        public KeyDispatchDataCodec<? extends EndCaveBiome> codec() {
            return JadeCaveBiome.KEY_CODEC;
        }

        protected Biome(
                float fogDensity,
                @NotNull ResourceKey<net.minecraft.world.level.biome.Biome> biome,
                @NotNull List<Climate.ParameterPoint> parameterPoints,
                float terrainHeight,
                float genChance,
                int edgeSize,
                boolean vertical,
                @Nullable ResourceKey<net.minecraft.world.level.biome.Biome> edge,
                @Nullable ResourceKey<net.minecraft.world.level.biome.Biome> parent,
                boolean hasCaves,
                SurfaceMaterialProvider surface,
                WeightedList<Holder<ConfiguredFeature<?, ?>>> floorFeatures,
                WeightedList<Holder<ConfiguredFeature<?, ?>>> ceilFeatures
        ) {
            super(
                    fogDensity, biome, parameterPoints, terrainHeight,
                    genChance, edgeSize, vertical,
                    edge, parent,
                    hasCaves, surface, floorFeatures, ceilFeatures
            );
        }

        @Override
        public BlockState getWall(BlockPos pos) {
            double depth = DEPTH_NOISE.eval(pos.getX() * 0.02, pos.getZ() * 0.02) * 0.2 + 0.5;
            int index = Mth.floor((pos.getY() + WALL_NOISE.eval(
                    pos.getX() * 0.2,
                    pos.getZ() * 0.2
            ) * 1.5) * depth + 0.5);
            index = Mth.abs(index) % 3;
            return JADE[index];
        }

        static {
            JADE[0] = EndBlocks.VIRID_JADESTONE.stone.defaultBlockState();
            JADE[1] = EndBlocks.AZURE_JADESTONE.stone.defaultBlockState();
            JADE[2] = EndBlocks.SANDY_JADESTONE.stone.defaultBlockState();
        }
    }

    public JadeCaveBiome() {
        super();
    }


    @Override
    public void addCustomBuildData(EndBiomeBuilder builder) {
        super.addCustomBuildData(builder);
        builder.fogColor(118, 150, 112)
               .fogDensity(2.0F)
               .waterAndFogColor(95, 223, 255);
    }


}
