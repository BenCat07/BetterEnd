package org.betterx.betterend.mixin.common;

import org.betterx.bclib.util.StructureHelper;
import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.world.generator.GeneratorOptions;
import org.betterx.wover.state.api.WorldConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(EndPodiumFeature.class)
public class EndPodiumFeatureMixin {
    @Unique
    private static BlockPos be_portalPosition;

    @Final
    @Shadow
    private boolean active;

    @Inject(method = "place", at = @At("HEAD"), cancellable = true)
    private void be_place(
            FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext,
            CallbackInfoReturnable<Boolean> info
    ) {
        if (!GeneratorOptions.hasPortal()) {
            info.setReturnValue(false);
            info.cancel();
        } else if (GeneratorOptions.replacePortal()) {
            RandomSource random = featurePlaceContext.random();
            WorldGenLevel world = featurePlaceContext.level();
            BlockPos blockPos = be_updatePortalPos(world);
            String path = active
                    ? "portal/end_portal_active"
                    : "portal/end_portal_inactive";
            StructureTemplate structure = StructureHelper.readStructure(BetterEnd.C.mk(path));
            Vec3i size = structure.getSize();
            blockPos = blockPos.offset(-(size.getX() >> 1), -3, -(size.getZ() >> 1));
            structure.placeInWorld(world, blockPos, blockPos, new StructurePlaceSettings(), random, 2);
            info.setReturnValue(true);
            info.cancel();
        }
    }

    @ModifyVariable(method = "place", ordinal = 0, at = @At("HEAD"), argsOnly = true)
    private FeaturePlaceContext<NoneFeatureConfiguration> be_setPosOnGround(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        WorldGenLevel world = featurePlaceContext.level();
        BlockPos pos = be_updatePortalPos(world);
        return new FeaturePlaceContext<NoneFeatureConfiguration>(
                Optional.empty(),
                world,
                featurePlaceContext.chunkGenerator(),
                featurePlaceContext.random(),
                pos,
                featurePlaceContext.config()
        );
    }

    @Unique
    private BlockPos be_updatePortalPos(WorldGenLevel world) {
        CompoundTag compound = WorldConfig.getRootTag(BetterEnd.C);
        be_portalPosition = NbtUtils.readBlockPos(compound, "portal").orElse(new BlockPos(0, 0, 0));

        if (be_portalPosition.getY() == 0) {
			/*if (GeneratorOptions.useNewGenerator()) {
				int y = TerrainGenerator.getHeight(0, 0, world.getLevel().getChunkSource().getGenerator().getBiomeSource());
				be_portalPosition = new BlockPos(0, y, 0);
			}
			else {
				be_portalPosition = new BlockPos(0, 65, 0);
			}*/
            int y = world.getHeight(Types.WORLD_SURFACE, 0, 0);
            be_portalPosition = new BlockPos(0, y, 0);
            WorldConfig.getRootTag(BetterEnd.C).put("portal", NbtUtils.writeBlockPos(be_portalPosition));
            WorldConfig.saveFile(BetterEnd.C);
        }

        return be_portalPosition;
    }
}
