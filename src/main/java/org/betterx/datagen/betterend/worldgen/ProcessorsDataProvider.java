package org.betterx.datagen.betterend.worldgen;

import org.betterx.bclib.api.v3.datagen.ProcessorHelper;
import org.betterx.bclib.complexmaterials.set.stone.StoneSlots;
import org.betterx.betterend.registry.EndBlocks;
import org.betterx.betterend.registry.EndProcessors;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import com.google.common.collect.ImmutableList;

public class ProcessorsDataProvider {

    public static void bootstrap(BootstapContext<StructureProcessorList> bootstapContext) {
        ProcessorHelper.register(
                bootstapContext,
                EndProcessors.WEATHERED_10_PERCENT,
                ImmutableList.of(new RuleProcessor(ImmutableList.of(new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.END_STONE_BRICKS, 0.1f),
                        AlwaysTrueTest.INSTANCE,
                        EndBlocks.END_STONE_BRICK_VARIATIONS.getBlock(StoneSlots.WEATHERED).defaultBlockState()
                ))))
        );

        ProcessorHelper.register(
                bootstapContext,
                EndProcessors.CRACK_20_PERCENT,
                ImmutableList.of(new RuleProcessor(ImmutableList.of(new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.END_STONE_BRICKS, 0.2f),
                        AlwaysTrueTest.INSTANCE,
                        EndBlocks.END_STONE_BRICK_VARIATIONS.getBlock(StoneSlots.CRACKED).defaultBlockState()
                ))))
        );

        ProcessorHelper.register(
                bootstapContext,
                EndProcessors.CRACK_AND_WEATHER,
                ImmutableList.of(new RuleProcessor(ImmutableList.of(
                        new ProcessorRule(
                                new RandomBlockMatchTest(Blocks.END_STONE_BRICKS, 0.2f),
                                AlwaysTrueTest.INSTANCE,
                                EndBlocks.END_STONE_BRICK_VARIATIONS.getBlock(StoneSlots.CRACKED).defaultBlockState()
                        ),
                        new ProcessorRule(
                                new RandomBlockMatchTest(Blocks.END_STONE_BRICKS, 0.1f),
                                AlwaysTrueTest.INSTANCE,
                                EndBlocks.END_STONE_BRICK_VARIATIONS.getBlock(StoneSlots.WEATHERED).defaultBlockState()
                        )
                )))
        );
    }
}
