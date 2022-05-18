package org.betterx.betterend.world.structures.features;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import org.betterx.bclib.util.StructureHelper;
import org.betterx.bclib.world.structures.BCLStructure;
import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.registry.EndStructures;
import org.betterx.betterend.world.structures.piece.NBTPiece;

import java.util.Optional;

public class EternalPortalStructure extends FeatureBaseStructure {
    private static final ResourceLocation STRUCTURE_ID = BetterEnd.makeID("portal/eternal_portal");
    private static final StructureTemplate STRUCTURE = StructureHelper.readStructure(STRUCTURE_ID);

    public EternalPortalStructure(StructureSettings s) {
        super(s);
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        if (!BCLStructure.isValidBiome(context)) return Optional.empty();

        final ChunkPos chunkPos = context.chunkPos();
        final ChunkGenerator chunkGenerator = context.chunkGenerator();
        final LevelHeightAccessor levelHeightAccessor = context.heightAccessor();

        long x = (long) chunkPos.x * (long) chunkPos.x;
        long z = (long) chunkPos.z * (long) chunkPos.z;
        if (x + z < 1024L) {
            return Optional.empty();
        }
        if (chunkGenerator.getBaseHeight(
                chunkPos.getBlockX(8),
                chunkPos.getBlockZ(8),
                Heightmap.Types.WORLD_SURFACE_WG,
                levelHeightAccessor,
                context.randomState()
                                        ) < 5) {
            return Optional.empty();
        }
        return super.findGenerationPoint(context);
    }

    @Override
    public StructureType<EternalPortalStructure> type() {
        return EndStructures.ETERNAL_PORTAL.structureType;
    }

    protected void generatePieces(StructurePiecesBuilder structurePiecesBuilder, GenerationContext context) {
        final RandomSource random = context.random();
        final ChunkPos chunkPos = context.chunkPos();
        final ChunkGenerator chunkGenerator = context.chunkGenerator();
        final LevelHeightAccessor levelHeightAccessor = context.heightAccessor();
        int x = chunkPos.getBlockX(8);
        int z = chunkPos.getBlockZ(8);
        int y = chunkGenerator.getBaseHeight(x, z, Types.WORLD_SURFACE_WG, levelHeightAccessor, context.randomState());
        structurePiecesBuilder.addPiece(new NBTPiece(
                STRUCTURE_ID,
                STRUCTURE,
                new BlockPos(x, y - 4, z),
                random.nextInt(5),
                true,
                random
        ));
    }
}
