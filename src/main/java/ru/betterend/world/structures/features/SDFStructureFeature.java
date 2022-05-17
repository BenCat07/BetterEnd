package ru.betterend.world.structures.features;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import ru.bclib.sdf.SDF;
import ru.bclib.util.MHelper;
import ru.betterend.world.structures.piece.VoxelPiece;

import java.util.Random;
import net.minecraft.util.RandomSource;
import java.util.function.BiFunction;

public abstract class SDFStructureFeature extends FeatureBaseStructure {
	public SDFStructureFeature(StructureSettings s) {
		super(s);
	}

	public static void generatePieces(StructurePiecesBuilder structurePiecesBuilder, GenerationContext context, BiFunction<BlockPos, RandomSource, SDF> sdf) {
		final RandomSource random = context.random();
		final ChunkPos chunkPos = context.chunkPos();
		final ChunkGenerator chunkGenerator = context.chunkGenerator();
		final LevelHeightAccessor levelHeightAccessor = context.heightAccessor();
		int x = chunkPos.getBlockX(MHelper.randRange(4, 12, random));
		int z = chunkPos.getBlockZ(MHelper.randRange(4, 12, random));
		int y = chunkGenerator.getBaseHeight(x, z, Types.WORLD_SURFACE_WG, levelHeightAccessor, context.randomState());
		if (y > 5) {
			BlockPos start = new BlockPos(x, y, z);
			VoxelPiece piece = new VoxelPiece((world) -> {
				sdf.apply(start, random).fillRecursive(world, start);
			}, random.nextInt());

			structurePiecesBuilder.addPiece(piece);
		}

		//this.calculateBoundingBox();
	}
}
