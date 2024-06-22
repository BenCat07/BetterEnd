package org.betterx.datagen.betterend.tags;

import org.betterx.betterend.BetterEnd;
import org.betterx.wover.core.api.ModCore;
import org.betterx.wover.datagen.api.WoverTagProvider;
import org.betterx.wover.tag.api.event.context.TagBootstrapContext;
import org.betterx.wover.tag.api.predefined.CommonBlockTags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

public class BYGBlockTagsProvider extends WoverTagProvider.ForBlocks {
    public BYGBlockTagsProvider(ModCore modCore) {
        super(modCore);
    }

    private static final ResourceKey<Block> IVIS_PHYLIUM = ResourceKey.create(Registries.BLOCK, BetterEnd.BYG.mk("ivis_phylium"));

    @Override
    protected void prepareTags(TagBootstrapContext<Block> context) {
        context.add(CommonBlockTags.END_STONES, IVIS_PHYLIUM);
    }
}
