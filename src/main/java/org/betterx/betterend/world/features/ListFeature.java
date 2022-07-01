package org.betterx.betterend.world.features;


import org.betterx.bclib.util.StructureHelper;
import org.betterx.worlds.together.tag.v3.CommonBlockTags;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.List;

public class ListFeature extends NBTFeature {
    private final List<StructureInfo> list;
    private StructureInfo selected;

    public ListFeature(List<StructureInfo> list, BlockState defaultBlock) {
        super(defaultBlock);
        this.list = list;
    }

    @Override
    protected StructureTemplate getStructure(WorldGenLevel world, BlockPos pos, RandomSource random) {
        selected = list.get(random.nextInt(list.size()));
        return selected.getStructure();
    }

    @Override
    protected boolean canSpawn(WorldGenLevel world, BlockPos pos, RandomSource random) {
        int cx = pos.getX() >> 4;
        int cz = pos.getZ() >> 4;
        return ((cx + cz) & 1) == 0 && pos.getY() > 58
                && world.getBlockState(pos).isAir()
                && world.getBlockState(pos.below()).is(CommonBlockTags.TERRAIN);
    }

    @Override
    protected Rotation getRotation(WorldGenLevel world, BlockPos pos, RandomSource random) {
        return Rotation.getRandom(random);
    }

    @Override
    protected Mirror getMirror(WorldGenLevel world, BlockPos pos, RandomSource random) {
        return Mirror.values()[random.nextInt(3)];
    }

    @Override
    protected int getYOffset(StructureTemplate structure, WorldGenLevel world, BlockPos pos, RandomSource random) {
        return selected.offsetY;
    }

    @Override
    protected TerrainMerge getTerrainMerge(WorldGenLevel world, BlockPos pos, RandomSource random) {
        return selected.terrainMerge;
    }

    @Override
    protected void addStructureData(StructurePlaceSettings data) {
    }

    public static final class StructureInfo {
        public final TerrainMerge terrainMerge;
        public final String structurePath;
        public final int offsetY;

        private StructureTemplate structure;

        public StructureInfo(String structurePath, int offsetY, TerrainMerge terrainMerge) {
            this.terrainMerge = terrainMerge;
            this.structurePath = structurePath;
            this.offsetY = offsetY;
        }

        public StructureTemplate getStructure() {
            if (structure == null) {
                structure = StructureHelper.readStructure(structurePath);
            }
            return structure;
        }
    }
}
