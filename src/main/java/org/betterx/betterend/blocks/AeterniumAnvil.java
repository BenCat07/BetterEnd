package org.betterx.betterend.blocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import org.betterx.bclib.items.BaseAnvilItem;
import org.betterx.betterend.blocks.basis.EndAnvilBlock;
import org.betterx.betterend.item.material.EndToolMaterial;
import org.betterx.betterend.registry.EndBlocks;

public class AeterniumAnvil extends EndAnvilBlock {
    public AeterniumAnvil() {
        super(EndBlocks.AETERNIUM_BLOCK.defaultMaterialColor(), EndToolMaterial.AETERNIUM.getLevel());
    }

    @Override
    public int getMaxDurability() {
        return 8;
    }

    @Override
    public BlockItem getCustomItem(ResourceLocation blockID, FabricItemSettings settings) {
        return new BaseAnvilItem(this, settings.fireproof());
    }
}
