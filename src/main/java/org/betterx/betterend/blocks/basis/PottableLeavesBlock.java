package org.betterx.betterend.blocks.basis;

import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.blocks.BaseLeavesBlock;
import org.betterx.bclib.interfaces.SurvivesOnBlocks;
import org.betterx.betterend.interfaces.PottablePlant;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

import java.util.List;

public class PottableLeavesBlock extends BaseLeavesBlock implements PottablePlant, SurvivesOnBlocks {

    public PottableLeavesBlock(Block sapling, MapColor color) {
        super(sapling, BehaviourBuilders.createStaticLeaves(color, true));
    }

    public PottableLeavesBlock(Block sapling, MapColor color, int light) {
        super(sapling, BehaviourBuilders.createStaticLeaves(color, true).lightLevel(state -> light));
    }

    @Override
    public boolean canPlantOn(Block block) {
        if (sapling instanceof PottablePlant) {
            return ((PottablePlant) sapling).canPlantOn(block);
        }
        return true;
    }

    @Override
    public List<Block> getSurvivableBlocks() {
        if (sapling instanceof SurvivesOnBlocks pp) {
            return pp.getSurvivableBlocks();
        }
        return List.of();
    }

    @Override
    public String prefixComponent() {
        return "tooltip.bclib.pottable_on";
    }
}
