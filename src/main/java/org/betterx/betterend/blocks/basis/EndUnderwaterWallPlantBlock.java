package org.betterx.betterend.blocks.basis;

import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.blocks.BaseUnderwaterWallPlantBlock;
import org.betterx.betterend.interfaces.survives.SurvivesOnEndStone;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public class EndUnderwaterWallPlantBlock extends BaseUnderwaterWallPlantBlock implements SurvivesOnEndStone {

    public EndUnderwaterWallPlantBlock(MapColor color) {
        super(BehaviourBuilders.createWaterPlant(color));
    }


    @Override
    public boolean isTerrain(BlockState state) {
        return SurvivesOnEndStone.super.isTerrain(state);
    }
}
