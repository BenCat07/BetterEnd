package org.betterx.betterend;

import org.betterx.bclib.api.v2.dataexchange.DataExchangeAPI;
import org.betterx.betterend.advancements.BECriteria;
import org.betterx.betterend.api.BetterEndPlugin;
import org.betterx.betterend.commands.CommandRegistry;
import org.betterx.betterend.config.Configs;
import org.betterx.betterend.effects.EndPotions;
import org.betterx.betterend.integration.Integrations;
import org.betterx.betterend.integration.trinkets.Elytra;
import org.betterx.betterend.network.RitualUpdate;
import org.betterx.betterend.recipe.builders.InfusionRecipe;
import org.betterx.betterend.registry.*;
import org.betterx.betterend.tab.CreativeTabs;
import org.betterx.betterend.util.BonemealPlants;
import org.betterx.betterend.util.LootTableUtil;
import org.betterx.betterend.world.generator.EndLandBiomeDecider;
import org.betterx.betterend.world.generator.GeneratorOptions;
import org.betterx.worlds.together.world.WorldConfig;
import org.betterx.wover.core.api.Logger;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.generator.api.biomesource.end.BiomeDecider;

import net.minecraft.resources.ResourceLocation;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.util.List;

public class BetterEnd implements ModInitializer {
    public static final ModCore C = ModCore.create("betterend");
    public static final ModCore TRINKETS_CORE = ModCore.create("trinkets");
    public static final String MOD_ID = C.namespace;
    public static final Logger LOGGER = C.LOG;

    public static final ModCore BYG = ModCore.create("byg");
    public static final ModCore NOURISH = ModCore.create("nourish");
    public static final ModCore FLAMBOYANT = ModCore.create("flamboyant");
    public static final ResourceLocation BYG_ADDITIONS_PACK = C.addDatapack(BYG);

    @Override
    public void onInitialize() {
        WorldConfig.registerModCache(MOD_ID);

        EndNumericProviders.register();
        EndPortals.loadPortals();
        EndSounds.register();
        EndMenuTypes.ensureStaticallyLoaded();
        EndBlockEntities.register();
        EndPoiTypes.register();
        EndFeatures.register();
        EndEntities.register();
        EndBiomes.register();
        EndTags.register();
        EndBlocks.ensureStaticallyLoaded();
        EndItems.register();
        EndTemplates.ensureStaticallyLoaded();
        EndEnchantments.register();
        EndPotions.register();
        InfusionRecipe.register();
        EndStructures.register();
        BonemealPlants.init();
        GeneratorOptions.init();
        LootTableUtil.init();
        CommandRegistry.register();
        EndParticles.ensureStaticallyLoadedServerside();
        BECriteria.register();
        FabricLoader.getInstance()
                    .getEntrypoints("betterend", BetterEndPlugin.class)
                    .forEach(BetterEndPlugin::register);
        Integrations.init();
        Configs.saveConfigs();
        CreativeTabs.register();

        if (GeneratorOptions.useNewGenerator()) {
            BiomeDecider.registerHighPriorityDecider(C.mk("end_land"), new EndLandBiomeDecider());
        }

        DataExchangeAPI.registerDescriptors(List.of(
                RitualUpdate.DESCRIPTOR
        ));

        if (TRINKETS_CORE.isLoaded()) {
            Elytra.register();
        }
    }
}
