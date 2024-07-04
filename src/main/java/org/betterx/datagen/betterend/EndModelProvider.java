package org.betterx.datagen.betterend;

import org.betterx.bclib.blocks.BaseVineBlock;
import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.blocks.basis.PedestalBlock;
import org.betterx.betterend.client.models.EndModels;
import org.betterx.betterend.registry.EndBlocks;
import org.betterx.wover.block.api.BlockProperties;
import org.betterx.wover.block.api.BlockRegistry;
import org.betterx.wover.block.api.model.WoverBlockModelGenerators;
import org.betterx.wover.core.api.IntegrationCore;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.datagen.api.provider.WoverModelProvider;

import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class EndModelProvider extends WoverModelProvider {
    @Override
    protected void bootstrapItemModels(ItemModelGenerators itemModelGenerator) {

    }

    @Override
    protected void bootstrapBlockStateModels(WoverBlockModelGenerators generator) {
        this.addFromRegistry(
                generator,
                BlockRegistry.forMod(BetterEnd.C),
                true,
                ModelOverides.create()
                             .override(EndBlocks.TWISTED_VINE, createTwistedVineModel(generator))
                             .override(EndBlocks.AMBER_MOSS, createAmberMossModel(generator))
                             .override(EndBlocks.AMBER_MOSS_PATH, createAmberMossPathModel(generator, EndBlocks.AMBER_MOSS))
                             .override(EndBlocks.QUARTZ_PEDESTAL, block -> PedestalBlock.provideBlockModel(generator, new TextureMapping()
                                     .put(TextureSlot.TOP, IntegrationCore.MINECRAFT.mk("block/quartz_pillar_top"))
                                     .put(TextureSlot.BOTTOM, IntegrationCore.MINECRAFT.mk("block/quartz_block_bottom"))
                                     .put(EndModels.BASE, IntegrationCore.MINECRAFT.mk("block/quartz_block_side"))
                                     .put(EndModels.PILLAR, IntegrationCore.MINECRAFT.mk("block/quartz_pillar")), block))
                             .override(EndBlocks.PURPUR_PEDESTAL, block -> PedestalBlock.provideBlockModel(generator, new TextureMapping()
                                     .put(TextureSlot.TOP, IntegrationCore.MINECRAFT.mk("block/purpur_pillar_top"))
                                     .put(TextureSlot.BOTTOM, IntegrationCore.MINECRAFT.mk("block/purpur_block"))
                                     .put(EndModels.BASE, IntegrationCore.MINECRAFT.mk("block/purpur_block"))
                                     .put(EndModels.PILLAR, IntegrationCore.MINECRAFT.mk("block/purpur_pillar")), block))
                             .override(EndBlocks.NEON_CACTUS_BLOCK_STAIRS, block -> {
                                 final var id = TextureMapping.getBlockTexture(block);
                                 final var texture = BetterEnd.C.mk("block/neon_cactus_block");
                                 final var mapping = new TextureMapping()
                                         .put(TextureSlot.TOP, texture.withSuffix("_top"))
                                         .put(TextureSlot.BOTTOM, texture.withSuffix("_top"))
                                         .put(TextureSlot.SIDE, texture.withSuffix("_side"));
                                 final var stairs = EndModels.LIT_STAIRS.create(id, mapping, generator.modelOutput());
                                 final var stairs_outer = EndModels.LIT_STAIRS_OUTER.create(id.withSuffix("_outer"), mapping, generator.modelOutput());
                                 final var stairs_inner = EndModels.LIT_STAIRS_INNER.create(id.withSuffix("_inner"), mapping, generator.modelOutput());
                                 generator.createStairsWithModels(block, stairs, stairs_outer, stairs_inner);
                             })
                             .ignore(EndBlocks.CRYSTAL_GRASS)
                             .ignore(EndBlocks.CYAN_MOSS)
                             .override(EndBlocks.AERIDIUM, generator::createFlatItem)
                             .ignore(EndBlocks.BUSHY_GRASS)
                             .ignore(EndBlocks.JUNGLE_FERN)
                             .ignore(EndBlocks.BULB_MOSS)
                             .ignore(EndBlocks.FLAMAEA)
                             .ignore(EndBlocks.CAVE_BUSH)
                             .ignore(EndBlocks.BULB_VINE)
                             .ignore(EndBlocks.BULB_VINE_SEED)
                             .ignore(EndBlocks.SILK_MOTH_NEST)
                             .override(EndBlocks.SILK_MOTH_HIVE, generator::delegateItemModel)
                             .ignore(EndBlocks.SMARAGDANT_SUBBLOCKS.stairs)
                             .ignore(EndBlocks.SMARAGDANT_SUBBLOCKS.wall)
                             .ignore(EndBlocks.SMARAGDANT_SUBBLOCKS.brick_stairs)
                             .ignore(EndBlocks.GOLD_CHANDELIER)
                             .ignore(EndBlocks.IRON_CHANDELIER)
                             .ignore(EndBlocks.LUCERNIA_OUTER_LEAVES)
                             .ignore(EndBlocks.SULPHUR_CRYSTAL)
                             .override(EndBlocks.MOSSY_OBSIDIAN, generator::delegateItemModel)
                             .override(EndBlocks.HYDROTHERMAL_VENT, generator::delegateItemModel)
                             .ignore(EndBlocks.FLAVOLITE_RUNED_ETERNAL)
                             .ignore(EndBlocks.FLAVOLITE_RUNED)
                             .ignore(EndBlocks.MOSSY_GLOWSHROOM_HYMENOPHORE)
                             .ignore(EndBlocks.END_LOTUS_SEED)
                             .ignore(EndBlocks.END_LOTUS_LEAF)
                             .override(EndBlocks.CAVE_PUMPKIN, generator::delegateItemModel)
                             .ignore(EndBlocks.NEON_CACTUS_BLOCK)
                             .ignore(EndBlocks.FILALUX)
                             .ignore(EndBlocks.CREEPING_MOSS)
                             .ignore(EndBlocks.RESPAWN_OBELISK)
                             .override(EndBlocks.TWISTED_UMBRELLA_MOSS, b -> generator.createFlatItem(b, BetterEnd.C.mk("item/twisted_umbrella_moss_small")))
                             .override(EndBlocks.TWISTED_UMBRELLA_MOSS_TALL, b -> generator.createFlatItem(b, BetterEnd.C.mk("item/twisted_umbrella_moss_large")))
                             .override(EndBlocks.UMBRELLA_MOSS, b -> generator.createFlatItem(b, BetterEnd.C.mk("item/umbrella_moss_small")))
                             .override(EndBlocks.UMBRELLA_MOSS_TALL, b -> generator.createFlatItem(b, BetterEnd.C.mk("item/umbrella_moss_large")))
                             .ignore(EndBlocks.BLOSSOM_BERRY)
                             .ignore(EndBlocks.CAVE_PUMPKIN_SEED)
                             .ignore(EndBlocks.CHORUS_MUSHROOM)
                             .ignore(EndBlocks.SHADOW_BERRY)
                             .ignore(EndBlocks.LUMECORN_SEED)
                             .ignore(EndBlocks.HYDRALUX_SAPLING)
                             .ignore(EndBlocks.CHORUS_GRASS)
                             .ignore(EndBlocks.SMALL_JELLYSHROOM)
                             .ignore(EndBlocks.CAVE_GRASS)
                             .ignore(EndBlocks.GLOWING_PILLAR_SEED)
                             .ignore(EndBlocks.END_LILY_SEED)
                             .ignore(EndBlocks.BLUE_VINE_SEED)
                             .ignore(EndBlocks.TUBE_WORM)
                             .ignore(EndBlocks.AMBER_ROOT)
                             .ignore(EndBlocks.PURPLE_POLYPORE)
                             .ignore(EndBlocks.NEON_CACTUS)
                             .ignore(EndBlocks.AURANT_POLYPORE)
                             .ignore(EndBlocks.NEEDLEGRASS)
                             .ignore(EndBlocks.VIOLECITE.flowerPot)
                             .ignore(EndBlocks.UMBRALITH.flowerPot)
                             .ignore(EndBlocks.SULPHURIC_ROCK.flowerPot)
                             .ignore(EndBlocks.VIRID_JADESTONE.flowerPot)
                             .ignore(EndBlocks.AZURE_JADESTONE.flowerPot)
                             .ignore(EndBlocks.SANDY_JADESTONE.flowerPot)
                             .ignore(EndBlocks.FLAVOLITE.flowerPot)
                             .ignore(EndBlocks.ENDSTONE_FLOWER_POT)
                             .ignore(EndBlocks.SMARAGDANT_CRYSTAL_SHARD)
                             .override(EndBlocks.END_LOTUS_STEM, generator::delegateItemModel)
                             .ignore(EndBlocks.THALLASIUM.chandelier)
                             .ignore(EndBlocks.TERMINITE.chandelier)
                             .ignore(EndBlocks.RUTISCUS)
                             .ignore(EndBlocks.MURKWEED)
                             .ignore(EndBlocks.LANCELEAF_SEED)
                             .ignore(EndBlocks.CHARNIA_RED)
                             .ignore(EndBlocks.CHARNIA_CYAN)
                             .ignore(EndBlocks.CHARNIA_GREEN)
                             .ignore(EndBlocks.CHARNIA_ORANGE)
                             .ignore(EndBlocks.CHARNIA_PURPLE)
                             .ignore(EndBlocks.CHARNIA_LIGHT_BLUE)
                             .ignore(EndBlocks.BOLUX_MUSHROOM)
                             .ignore(EndBlocks.SMALL_AMARANITA_MUSHROOM)
                             .ignore(EndBlocks.BUBBLE_CORAL)
                             .ignore(EndBlocks.FILALUX_WINGS)
                             .override(EndBlocks.SALTEAGO, generator::createFlatItem)
                             .ignore(EndBlocks.RUBINEA)
                             .ignore(EndBlocks.RUSCUS)
                             .ignore(EndBlocks.TENANEA_FLOWERS)
                             .ignore(EndBlocks.MAGNULA)
                             .override(EndBlocks.CLAWFERN, generator::createFlatItem)
                             .override(EndBlocks.LARGE_AMARANITA_MUSHROOM, generator::createFlatItem)
                             .override(EndBlocks.LANCELEAF, generator::createFlatItem)
                             .override(EndBlocks.END_LILY, generator::createFlatItem)
                             .override(EndBlocks.LUMECORN, generator::createFlatItem)
                             .override(EndBlocks.END_LOTUS_FLOWER, generator::createFlatItem)
                             .override(EndBlocks.HYDRALUX, generator::createFlatItem)
                             .override(EndBlocks.LAMELLARIUM, generator::createFlatItem)
                             .override(EndBlocks.LUTEBUS, generator::createFlatItem)
                             .override(EndBlocks.MOSSY_GLOWSHROOM_CAP, generator::delegateItemModel)
                             .override(EndBlocks.MOSSY_GLOWSHROOM_FUR, generator::createFlatItem)
                             .override(EndBlocks.AMARANITA_FUR, generator::createFlatItem)
                             .override(EndBlocks.ORANGO, generator::createFlatItem)
                             .override(EndBlocks.FRACTURN, generator::createFlatItem)
                             .override(EndBlocks.GLOBULAGUS, generator::createFlatItem)
                             .override(EndBlocks.FLAMMALIX, generator::createFlatItem)
                             .override(EndBlocks.AMBER_GRASS, generator::createFlatItem)
                             .override(EndBlocks.BLOOMING_COOKSONIA, generator::createFlatItem)
                             .override(EndBlocks.BLUE_VINE, generator::createFlatItem)
                             .override(EndBlocks.BLUE_VINE_FUR, generator::createFlatItem)
                             .ignore(EndBlocks.DENSE_VINE)
                             .override(EndBlocks.GLOWING_PILLAR_LEAVES, generator::createFlatItem)
                             .override(EndBlocks.GLOWING_PILLAR_ROOTS, generator::createFlatItem)
                             //.ignore(BYGBlocks.IVIS_MOSS).ignore(BYGBlocks.IVIS_VINE).ignore(BYGBlocks.NIGHTSHADE_MOSS)
                             .override(EndBlocks.JUNGLE_GRASS, generator::createFlatItem)
                             .ignore(EndBlocks.JUNGLE_VINE)
                             .ignore(EndBlocks.POND_ANEMONE)
                             .override(EndBlocks.SHADOW_PLANT, generator::createFlatItem)
                             .override(EndBlocks.TAIL_MOSS, generator::createFlatItem)
                             .override(EndBlocks.SULPHURIC_ROCK.stone, generator::delegateItemModel)
                             .override(EndBlocks.TENANEA_OUTER_LEAVES, generator::delegateItemModel)
                             .override(EndBlocks.UMBRALITH.stone, b -> generator.delegateItemModel(b, BetterEnd.C.mk("block/umbralith_5")))
                             .override(EndBlocks.TWISTED_MOSS, generator::createFlatItem)
                             .override(EndBlocks.VAIOLUSH_FERN, generator::createFlatItem)
                             .ignore(EndBlocks.BRIMSTONE)
                             .override(EndBlocks.HELIX_TREE_LEAVES, generator::delegateItemModel)
                             .override(EndBlocks.MENGER_SPONGE, generator::delegateItemModel)
                             .override(EndBlocks.MENGER_SPONGE_WET, generator::delegateItemModel)
                             .override(EndBlocks.VIOLECITE.brickWall, b -> generator.delegateItemModel(b, BetterEnd.C.mk("block/violecite_bricks_wall_post")))
                             .ignore(EndBlocks.SMARAGDANT_SUBBLOCKS.brick_wall)
        );
    }

    private static ModelOverides.@NotNull BlockModelProvider createAmberMossPathModel(
            WoverBlockModelGenerators generator,
            Block baseBlock
    ) {
        return block -> {
            final var endStone = TextureMapping.getBlockTexture(Blocks.END_STONE);
            final var baseTexture = TextureMapping.getBlockTexture(block, "_top");
            final var models = List.of("_1", "_2", "_3").stream().map(suffix -> {
                final var side = TextureMapping.getBlockTexture(baseBlock, "_side" + suffix);
                final var mapping = new TextureMapping()
                        .put(TextureSlot.BOTTOM, endStone)
                        .put(TextureSlot.TOP, baseTexture)
                        .put(TextureSlot.SIDE, side);
                return EndModels.PATH.createWithSuffix(block, suffix, mapping, generator.modelOutput());
            }).toList();

            buildRotated(generator, block, models);
        };
    }

    private static ModelOverides.@NotNull BlockModelProvider createAmberMossModel(WoverBlockModelGenerators generator) {
        return block -> {
            final var endStone = TextureMapping.getBlockTexture(Blocks.END_STONE);
            final var baseTexture = TextureMapping.getBlockTexture(block, "_top");
            final var models = List.of("_1", "_2", "_3").stream().map(suffix -> {
                final var side = TextureMapping.getBlockTexture(block, "_side" + suffix);
                final var mapping = new TextureMapping()
                        .put(TextureSlot.DOWN, endStone)
                        .put(TextureSlot.UP, baseTexture)
                        .put(TextureSlot.PARTICLE, side)
                        .put(TextureSlot.EAST, side)
                        .put(TextureSlot.NORTH, side)
                        .put(TextureSlot.SOUTH, side)
                        .put(TextureSlot.WEST, side);
                return ModelTemplates.CUBE.createWithSuffix(block, suffix, mapping, generator.modelOutput());
            }).toList();

            buildRotated(generator, block, models);
        };
    }

    private static void buildRotated(WoverBlockModelGenerators generator, Block block, List<ResourceLocation> models) {
        final ArrayList<Variant> variants = new ArrayList<>(models.size() * 4);
        models.forEach(model -> {
            variants.add(Variant.variant().with(VariantProperties.MODEL, model));
            variants.add(Variant.variant()
                                .with(VariantProperties.MODEL, model)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90));
            variants.add(Variant.variant()
                                .with(VariantProperties.MODEL, model)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180));
            variants.add(Variant.variant()
                                .with(VariantProperties.MODEL, model)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270));
        });

        generator.acceptBlockState(MultiVariantGenerator.multiVariant(block, variants.toArray(new Variant[0])));
        generator.delegateItemModel(block, models.get(0));
    }

    private static ModelOverides.@NotNull BlockModelProvider createTwistedVineModel(WoverBlockModelGenerators generator) {
        return block -> {
            final var itemTextureLocation = TextureMapping.getBlockTexture(block, "_bottom");
            var bottomMapping = new TextureMapping()
                    .put(TextureSlot.TEXTURE, itemTextureLocation);
            var middleMapping = new TextureMapping()
                    .put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(block));
            var topMapping = new TextureMapping()
                    .put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(block))
                    .put(EndModels.ROOTS, TextureMapping.getBlockTexture(block, "_roots"));

            var bottom_1 = EndModels.CROSS_NO_DISTORTION.createWithSuffix(block, "_bottom_1", bottomMapping, generator.modelOutput());
            var bottom_2 = EndModels.CROSS_NO_DISTORTION_INVERTED.createWithSuffix(block, "_bottom_2", bottomMapping, generator.modelOutput());
            var middle_1 = EndModels.CROSS_NO_DISTORTION.createWithSuffix(block, "_middle_1", middleMapping, generator.modelOutput());
            var middle_2 = EndModels.CROSS_NO_DISTORTION_INVERTED.createWithSuffix(block, "_middle_2", middleMapping, generator.modelOutput());
            var top = EndModels.TWISTED_VINE.createWithSuffix(block, "_top", topMapping, generator.modelOutput());

            generator.acceptBlockState(MultiVariantGenerator
                    .multiVariant(block)
                    .with(PropertyDispatch.property(BaseVineBlock.SHAPE)
                                          .select(
                                                  BlockProperties.TripleShape.TOP,
                                                  Variant.variant().with(VariantProperties.MODEL, top)
                                          )
                                          .select(
                                                  BlockProperties.TripleShape.MIDDLE,
                                                  List.of(
                                                          Variant.variant().with(VariantProperties.MODEL, middle_1),
                                                          Variant.variant().with(VariantProperties.MODEL, middle_2)
                                                  )

                                          )
                                          .select(
                                                  BlockProperties.TripleShape.BOTTOM,
                                                  List.of(
                                                          Variant.variant().with(VariantProperties.MODEL, bottom_1),
                                                          Variant.variant().with(VariantProperties.MODEL, bottom_2)
                                                  )
                                          )
                    )
            );

            generator.createFlatItem(block, itemTextureLocation);
        };
    }

    public EndModelProvider(ModCore modCore) {
        super(modCore);
    }
}
