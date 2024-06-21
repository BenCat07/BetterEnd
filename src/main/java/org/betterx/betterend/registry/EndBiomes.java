package org.betterx.betterend.registry;

import org.betterx.bclib.api.v2.LifeCycleAPI;
import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.world.biome.EndBiome;
import org.betterx.betterend.world.biome.EndBiomeBuilder;
import org.betterx.betterend.world.biome.EndBiomeKey;
import org.betterx.betterend.world.biome.air.BiomeIceStarfield;
import org.betterx.betterend.world.biome.cave.*;
import org.betterx.betterend.world.biome.land.*;
import org.betterx.betterend.world.generator.GeneratorOptions;
import org.betterx.wover.biome.api.data.BiomeCodecRegistry;
import org.betterx.wover.biome.api.data.BiomeDataRegistry;
import org.betterx.wover.generator.api.biomesource.WoverBiomePicker;
import org.betterx.wover.generator.impl.map.hex.HexBiomeMap;
import org.betterx.wover.state.api.WorldState;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.Objects;
import java.util.Optional;

public class EndBiomes {
    public static WoverBiomePicker CAVE_BIOMES = null;
    private static HexBiomeMap caveBiomeMap;
    private static long lastSeed;

    public static final EndBiomeKey<AmberLandBiome, ?> AMBER_LAND = EndBiomeBuilder.createKey("amber_land");
    public static final EndBiomeKey<BlossomingSpiresBiome, ?> BLOSSOMING_SPIRES = EndBiomeBuilder.createKey("blossoming_spires");
    public static final EndBiomeKey<ChorusForestBiome, ?> CHORUS_FOREST = EndBiomeBuilder.createKey("chorus_forest");
    public static final EndBiomeKey<CrystalMountainsBiome, ?> CRYSTAL_MOUNTAINS = EndBiomeBuilder.createKey("crystal_mountains");
    public static final EndBiomeKey<DragonGraveyardsBiome, ?> DRAGON_GRAVEYARDS = EndBiomeBuilder.createKey("dragon_graveyards");
    public static final EndBiomeKey<DryShrublandBiome, ?> DRY_SHRUBLAND = EndBiomeBuilder.createKey("dry_shrubland");
    public static final EndBiomeKey<DustWastelandsBiome, ?> DUST_WASTELANDS = EndBiomeBuilder.createKey("dust_wastelands");
    public static final EndBiomeKey<FoggyMushroomlandBiome, ?> FOGGY_MUSHROOMLAND = EndBiomeBuilder.createKey("foggy_mushroomland");
    public static final EndBiomeKey<GlowingGrasslandsBiome, ?> GLOWING_GRASSLANDS = EndBiomeBuilder.createKey("glowing_grasslands");
    public static final EndBiomeKey<BiomeIceStarfield, ?> ICE_STARFIELD = EndBiomeBuilder.createKey("ice_starfield");
    public static final EndBiomeKey<LanternWoodsBiome, ?> LANTERN_WOODS = EndBiomeBuilder.createKey("lantern_woods");
    public static final EndBiomeKey<MegalakeBiome, ?> MEGALAKE = EndBiomeBuilder.createKey("megalake");
    public static final EndBiomeKey<ShadowForestBiome, ?> SHADOW_FOREST = EndBiomeBuilder.createKey("shadow_forest");
    public static final EndBiomeKey<SulphurSpringsBiome, ?> SULPHUR_SPRINGS = EndBiomeBuilder.createKey("sulphur_springs");
    public static final EndBiomeKey<UmbrellaJungleBiome, ?> UMBRELLA_JUNGLE = EndBiomeBuilder.createKey("umbrella_jungle");
    public static final EndBiomeKey<UmbraValleyBiome, ?> UMBRA_VALLEY = EndBiomeBuilder.createKey("umbra_valley");

    public static final EndBiomeKey<MegalakeGroveBiome, MegalakeBiome> MEGALAKE_GROVE = EndBiomeBuilder.createKey("megalake_grove", MEGALAKE);
    public static final EndBiomeKey<NeonOasisBiome, DustWastelandsBiome> NEON_OASIS = EndBiomeBuilder.createKey("neon_oasis", DUST_WASTELANDS);
    public static final EndBiomeKey<PaintedMountainsBiome, DustWastelandsBiome> PAINTED_MOUNTAINS = EndBiomeBuilder.createKey("painted_mountains", DUST_WASTELANDS);

    public static final EndBiomeKey<EmptyEndCaveBiome, ?> EMPTY_END_CAVE = EndBiomeBuilder.createKey("empty_end_cave");
    public static final EndBiomeKey<EmptySmaragdantCaveBiome, ?> EMPTY_SMARAGDANT_CAVE = EndBiomeBuilder.createKey("empty_smaragdant_cave");
    public static final EndBiomeKey<LushSmaragdantCaveBiome, ?> LUSH_SMARAGDANT_CAVE = EndBiomeBuilder.createKey("lush_smaragdant_cave");
    public static final EndBiomeKey<EmptyAuroraCaveBiome, ?> EMPTY_AURORA_CAVE = EndBiomeBuilder.createKey("empty_aurora_cave");
    public static final EndBiomeKey<LushAuroraCaveBiome, ?> LUSH_AURORA_CAVE = EndBiomeBuilder.createKey("new LushAuroraCaveBiome()");
    public static final EndBiomeKey<JadeCaveBiome, ?> JADE_CAVE = EndBiomeBuilder.createKey("jade_cave");


    public static void register() {
        BiomeCodecRegistry.register(BetterEnd.C.mk("biome"), EndBiome.KEY_CODEC);
        BiomeCodecRegistry.register(BetterEnd.C.mk("cave_biome"), EndCaveBiome.KEY_CODEC);
        BiomeCodecRegistry.register(BetterEnd.C.mk("empty_aurora_cave_biome"), EmptyAuroraCaveBiome.KEY_CODEC);
        BiomeCodecRegistry.register(BetterEnd.C.mk("empty_end_cave_biome"), EmptyEndCaveBiome.KEY_CODEC);
        BiomeCodecRegistry.register(BetterEnd.C.mk("empty_smaragdant_cave_biome"), EmptySmaragdantCaveBiome.KEY_CODEC);
        BiomeCodecRegistry.register(BetterEnd.C.mk("jade_cave_biome"), JadeCaveBiome.KEY_CODEC);
        BiomeCodecRegistry.register(BetterEnd.C.mk("lush_aurora_cave_biome"), LushAuroraCaveBiome.KEY_CODEC);
        BiomeCodecRegistry.register(BetterEnd.C.mk("lush_smaragdant_cave_biome"), LushSmaragdantCaveBiome.KEY_CODEC);

        LifeCycleAPI.onLevelLoad(EndBiomes::onWorldLoad);
    }

    private static void onWorldLoad(ServerLevel level, long seed, Registry<Biome> registry) {
        var dataRegistry = WorldState
                .allStageRegistryAccess()
                .registry(BiomeDataRegistry.BIOME_DATA_REGISTRY)
                .orElseThrow();


        if (CAVE_BIOMES == null || CAVE_BIOMES.biomeRegistry != registry) {
            CAVE_BIOMES = new WoverBiomePicker(Biomes.END_HIGHLANDS);
            registry.getTag(EndTags.IS_END_CAVE)
                    .map(tag -> tag
                            .stream()
                            .map(Holder::unwrapKey)
                            .filter(Optional::isPresent)
                            .map(Optional::orElseThrow)
                            .map(k -> dataRegistry.get(k.location()))
                            .filter(Objects::nonNull)
                    ).ifPresent(
                            list -> list.forEach(data -> {
                                CAVE_BIOMES.addBiome(data);
                            })
                    );

            CAVE_BIOMES.rebuild();
            caveBiomeMap = null;
        }

        if (caveBiomeMap == null || lastSeed != seed) {
            caveBiomeMap = new HexBiomeMap(seed, GeneratorOptions.getBiomeSizeCaves(), CAVE_BIOMES);
            lastSeed = seed;
        }
    }

    /**
     * Put integration sub-biome {@link EndBiome} into subbiomes list and registers it.
     *
     * @param biomeConfig - {@link EndBiome.Config} instance
     * @return registered {@link EndBiome}
     */
    public static EndBiome registerSubBiomeIntegration(EndBiome.Config biomeConfig) {
        //TODO: 1.19.3 this was don on runtime, but biomes are now created in DataGen, so we need a fix...
        return EndBiome.create(biomeConfig, BiomeAPI.BiomeType.END_LAND);
    }

    /**
     * Link integration sub-biome with parent.
     *
     * @param biome  - {@link EndBiome} instance
     * @param parent - {@link ResourceLocation} parent id
     */
    public static void addSubBiomeIntegration(EndBiome biome, ResourceLocation parent) {

        BCLBiome parentBiome = BiomeAPI.getBiome(parent);
        if (!BCLBiomeRegistry.isEmptyBiome(parentBiome) && biome.getParentBiome().getID().equals(biome.getID())) {
            parentBiome.addSubBiome(biome);
        }

    }

    public static WoverBiomePicker.PickableBiome getCaveBiome(int x, int z) {
        return caveBiomeMap.getBiome(x, 5, z);
    }

}
