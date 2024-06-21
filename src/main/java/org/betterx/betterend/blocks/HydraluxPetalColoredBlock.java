package org.betterx.betterend.blocks;

import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.interfaces.CustomColorProvider;
import org.betterx.bclib.interfaces.RuntimeBlockModelProvider;
import org.betterx.bclib.util.BlocksHelper;
import org.betterx.betterend.client.models.Patterns;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Optional;
import org.jetbrains.annotations.Nullable;

public class HydraluxPetalColoredBlock extends HydraluxPetalBlock implements CustomColorProvider, RuntimeBlockModelProvider {
    public HydraluxPetalColoredBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public BlockColor getProvider() {
        return (state, world, pos, tintIndex) -> BlocksHelper.getBlockColor(this);
    }

    @Override
    public ItemColor getItemProvider() {
        return (stack, tintIndex) -> BlocksHelper.getBlockColor(this);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public @Nullable BlockModel getBlockModel(ResourceLocation resourceLocation, BlockState blockState) {
        String path = "betterend:block/block_petal_colored";
        Optional<String> pattern = Patterns.createJson(Patterns.BLOCK_PETAL_COLORED, path, path);
        return ModelsHelper.fromPattern(pattern);
    }
}
