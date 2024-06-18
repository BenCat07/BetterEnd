package org.betterx.betterend.registry.features;

import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.registry.EndFeatures;
import org.betterx.betterend.world.features.CavePumpkinFeature;
import org.betterx.betterend.world.features.VineFeature;
import org.betterx.betterend.world.features.VineFeatureConfig;
import org.betterx.betterend.world.features.bushes.BushFeature;
import org.betterx.betterend.world.features.bushes.BushFeatureConfig;
import org.betterx.betterend.world.features.terrain.*;
import org.betterx.wover.feature.api.configured.ConfiguredFeatureKey;
import org.betterx.wover.feature.api.configured.ConfiguredFeatureManager;
import org.betterx.wover.feature.api.configured.configurators.WithConfiguration;

import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class EndConfiguredCaveFeatures {
    public static final ConfiguredFeatureKey<WithConfiguration<SmaragdantCrystalFeature, NoneFeatureConfiguration>> SMARAGDANT_CRYSTAL = ConfiguredFeatureManager.configuration(BetterEnd.C.mk("smaragdant_crystal"), EndFeatures.SMARAGDANT_CRYSTAL_FEATURE);
    public static final ConfiguredFeatureKey<WithConfiguration<SingleBlockFeature, SimpleBlockConfiguration>> SMARAGDANT_CRYSTAL_SHARD = ConfiguredFeatureManager.configuration(BetterEnd.C.mk("smaragdant_crystal_shard"), EndFeatures.SINGLE_BLOCK_FEATURE);
    public static final ConfiguredFeatureKey<WithConfiguration<BigAuroraCrystalFeature, NoneFeatureConfiguration>> BIG_AURORA_CRYSTAL = ConfiguredFeatureManager.configuration(BetterEnd.C.mk("big_aurora_crystal"), EndFeatures.BIG_AURORA_CRYSTAL_FEATURE);
    public static final ConfiguredFeatureKey<WithConfiguration<BushFeature, BushFeatureConfig>> CAVE_BUSH = ConfiguredFeatureManager.configuration(BetterEnd.C.mk("cave_bush"), EndFeatures.BUSH_FEATURE);
    public static final ConfiguredFeatureKey<WithConfiguration<SingleBlockFeature, SimpleBlockConfiguration>> CAVE_GRASS = ConfiguredFeatureManager.configuration(BetterEnd.C.mk("cave_grass"), EndFeatures.SINGLE_BLOCK_FEATURE);
    public static final ConfiguredFeatureKey<WithConfiguration<VineFeature, VineFeatureConfig>> RUBINEA = ConfiguredFeatureManager.configuration(BetterEnd.C.mk("rubinea"), EndFeatures.VINE_FEATURE);
    public static final ConfiguredFeatureKey<WithConfiguration<VineFeature, VineFeatureConfig>> MAGNULA = ConfiguredFeatureManager.configuration(BetterEnd.C.mk("magnula"), EndFeatures.VINE_FEATURE);
    public static final ConfiguredFeatureKey<WithConfiguration<StalactiteFeature, StalactiteFeatureConfig>> END_STONE_STALACTITE = ConfiguredFeatureManager.configuration(BetterEnd.C.mk("end_stone_stalactite"), EndFeatures.STALACTITE_FEATURE);
    public static final ConfiguredFeatureKey<WithConfiguration<StalactiteFeature, StalactiteFeatureConfig>> END_STONE_STALAGMITE = ConfiguredFeatureManager.configuration(BetterEnd.C.mk("end_stone_stalagmite"), EndFeatures.STALACTITE_FEATURE);
    public static final ConfiguredFeatureKey<WithConfiguration<StalactiteFeature, StalactiteFeatureConfig>> END_STONE_STALACTITE_CAVEMOSS = ConfiguredFeatureManager.configuration(BetterEnd.C.mk("end_stone_stalactite_cavemoss"), EndFeatures.STALACTITE_FEATURE);
    public static final ConfiguredFeatureKey<WithConfiguration<StalactiteFeature, StalactiteFeatureConfig>> END_STONE_STALAGMITE_CAVEMOSS = ConfiguredFeatureManager.configuration(BetterEnd.C.mk("end_stone_stalagmite_cavemoss"), EndFeatures.STALACTITE_FEATURE);
    public static final ConfiguredFeatureKey<WithConfiguration<CavePumpkinFeature, NoneFeatureConfiguration>> CAVE_PUMPKIN = ConfiguredFeatureManager.configuration(BetterEnd.C.mk("cave_pumpkin"), EndFeatures.CAVE_PUMPKIN_FEATURE);

}
