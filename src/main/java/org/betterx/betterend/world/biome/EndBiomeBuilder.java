package org.betterx.betterend.world.biome;

import org.betterx.bclib.interfaces.SurfaceMaterialProvider;
import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.registry.EndFeatures;
import org.betterx.betterend.registry.EndSounds;
import org.betterx.wover.biome.api.BiomeKey;
import org.betterx.wover.biome.api.builder.BiomeBootstrapContext;
import org.betterx.wover.biome.api.builder.BiomeBuilder;
import org.betterx.wover.biome.api.data.BiomeData;
import org.betterx.wover.generator.api.biomesource.WoverBiomeBuilder;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.EndPlacements;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EndBiomeBuilder extends WoverBiomeBuilder.AbstractWoverBiomeBuilder<EndBiomeBuilder> {
    protected boolean hasCave = false;
    protected SurfaceMaterialProvider surface;

    protected EndBiomeBuilder(
            BiomeBootstrapContext context,
            BiomeKey<EndBiomeBuilder> key,
            TagKey<Biome>... biomeTag
    ) {
        super(context, key);
        this.surface = EndBiome.Config.DEFAULT_MATERIAL;
        this
                .music(SoundEvents.MUSIC_END)
                .waterColor(BiomeBuilder.DEFAULT_END_WATER_COLOR)
                .waterFogColor(BiomeBuilder.DEFAULT_END_WATER_FOG_COLOR)
                .fogColor(BiomeBuilder.DEFAULT_END_FOG_COLOR)
                .skyColor(BiomeBuilder.DEFAULT_END_SKY_COLOR)
                .mood(EndSounds.AMBIENT_DUST_WASTELANDS)
                .temperature(BiomeBuilder.DEFAULT_END_TEMPERATURE)
                .downfall(BiomeBuilder.DEFAULT_END_WETNESS)
                .hasPrecipitation(false)
                .tag(biomeTag);
    }

    EndBiomeBuilder configure(EndBiome.Config biomeConfig) {
        this.startSurface()
            .rule(biomeConfig.surfaceMaterial().surface().build())
            .finishSurface();

        this.surface = biomeConfig.surfaceMaterial();

        this.hasCave = biomeConfig.hasCaves();
        if (biomeConfig.hasReturnGateway()) {
            this.feature(GenerationStep.Decoration.SURFACE_STRUCTURES, EndPlacements.END_GATEWAY_RETURN);
        }
        biomeConfig.addCustomBuildData(this);

        EndFeatures.addDefaultFeatures(this, biomeConfig.hasCaves());


        return this;
    }

    public static <C extends EndBiome.Config> EndBiomeKey<C, ?> createKey(
            @NotNull String name
    ) {
        return new EndBiomeKey<>(
                BetterEnd.C.id(name.replace(' ', '_').toLowerCase()),
                null
        );
    }


    public static <C extends EndBiome.Config, PC extends EndBiome.Config> EndBiomeKey<C, PC> createKey(
            @NotNull String name,
            @Nullable EndBiomeKey<PC, ?> parentOrNull
    ) {
        return new EndBiomeKey<>(
                BetterEnd.C.id(name.replace(' ', '_').toLowerCase()),
                parentOrNull
        );
    }

    @Override
    public void registerBiomeData(BootstrapContext<BiomeData> dataContext) {
        final EndBiome biome = new EndBiome(
                fogDensity, key.key, parameters,
                terrainHeight, genChance, edgeSize, vertical, edge, parent,
                hasCave, surface
        );
        biome.datagenSetup();
        dataContext.register(key.dataKey, biome);
    }
}


