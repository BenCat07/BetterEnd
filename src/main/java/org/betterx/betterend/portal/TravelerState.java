package org.betterx.betterend.portal;

import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.advancements.BECriteria;

import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public final class TravelerState {
    public final Entity entity;
    private BlockPos portalEntrancePos;
    private boolean isInsidePortal;
    private int portalTime;

    public TravelerState(Entity entity) {
        this.entity = entity;
        this.portalEntrancePos = null;
        this.isInsidePortal = false;
    }

    public static TravelerState init(Entity e) {
        return new TravelerState(e);
    }

    public void handleInsidePortal(BlockPos blockPos) {
        if (entity.isOnPortalCooldown()) {
            entity.setPortalCooldown();
            return;
        }
        if (!this.level().isClientSide && !blockPos.equals(this.portalEntrancePos)) {
            this.portalEntrancePos = blockPos.immutable();
        }
        this.isInsidePortal = true;
    }

    public Level level() {
        return this.entity.level();
    }

    @ApiStatus.Internal
    public void portalTick() {
        if (!(this.level() instanceof ServerLevel)) {
            return;
        }

        if (this.isInsidePortal) {
            final int waitTimer = entity.getPortalWaitTime();
            final ServerLevel sourceDimension = (ServerLevel) this.level();

            MinecraftServer minecraftServer = sourceDimension.getServer();
            ServerLevel targetDimension = minecraftServer.getLevel(this.level().dimension() == Level.END
                    ? Level.OVERWORLD
                    : Level.END);

            if (targetDimension != null && !entity.isPassenger() && this.portalTime++ >= waitTimer) {
                this.level().getProfiler().push("end_portal");
                this.portalTime = waitTimer;
                entity.setPortalCooldown();

                this.changeDimension(targetDimension);
                this.level().getProfiler().pop();
            }
            this.isInsidePortal = false;
        } else {
            if (this.portalTime > 0) {
                this.portalTime -= 4;
            }
            if (this.portalTime < 0) {
                this.portalTime = 0;
            }
        }
    }

    @Nullable
    public void changeDimension(ServerLevel targetLevel) {
        if (!(this.level() instanceof ServerLevel) || this.entity.isRemoved()) {
            return;
        }
        this.level().getProfiler().push("be_findEntry");
        this.entity.unRide();

        //EternalRitual.findRitualForActivePortal(this.level(), portalEntrancePos);
        PortalInfo portalInfo = this.findDimensionEntryPoint(targetLevel);
        if (portalInfo == null) {
            return;
        }
        this.level().getProfiler().push("be_reposition");
        teleportEntity(targetLevel, portalInfo);

        this.level().getProfiler().pop();
        this.level().getProfiler().pop();
        return;
    }

    private void teleportEntity(ServerLevel serverLevel, PortalInfo portalInfo) {
        final boolean targetIsEnd = serverLevel.dimension().equals(Level.END);
        final MinecraftServer server = serverLevel.getServer();
        final ServerLevel destination = targetIsEnd ? server.getLevel(Level.END) : server.getLevel(Level.OVERWORLD);
        if (entity instanceof ServerPlayer sp && sp.isCreative()) {
            sp.teleportTo(
                    destination,
                    portalInfo.pos.x + 0.5,
                    portalInfo.pos.y,
                    portalInfo.pos.z + 0.5,
                    entity.getYRot() + 180,
                    entity.getXRot()
            );
            BECriteria.PORTAL_TRAVEL.trigger(sp);
        } else {
            if (entity instanceof ServerPlayer sp) {
                BECriteria.PORTAL_TRAVEL.trigger(sp);
            }
            entity.setPortalCooldown();
        }
    }

    // /execute in the_end run tp 849 84 891
    // /execute in overworld run tp 849 64 891
    @Nullable
    private PortalInfo findDimensionEntryPoint(ServerLevel targetLevel) {
        boolean toEnd = targetLevel.dimension() == Level.END;
        if (this.level().dimension() != Level.END && !toEnd) {
            return null;
        }
        final WorldBorder worldBorder = targetLevel.getWorldBorder();
        final BlockPos startingPos = PortalBuilder.getStartingPos(this.level(), targetLevel, entity, worldBorder);

        return this.getExitPortal(targetLevel, startingPos, toEnd, worldBorder).map(foundRectangle -> {
            Vec3 vec3;
            Direction.Axis axis;
            BlockState blockState = this.level().getBlockState(this.portalEntrancePos);
            if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
                axis = blockState.getValue(BlockStateProperties.HORIZONTAL_AXIS);
                BlockUtil.FoundRectangle foundRectangle2 = BlockUtil.getLargestRectangleAround(
                        this.portalEntrancePos,
                        axis,
                        21,
                        Direction.Axis.Y,
                        21,
                        blockPos -> this.level().getBlockState(blockPos) == blockState
                );
                vec3 = this.getRelativePortalPosition(axis, foundRectangle2);
            } else {
                axis = Direction.Axis.X;
                vec3 = new Vec3(0.5, 0.0, 0.0);
            }
            return PortalShape.createPortalInfo(
                    targetLevel, foundRectangle, axis, vec3,
                    this.entity, entity.getDeltaMovement(), entity.getYRot(), entity.getXRot()
            );
        }).orElse(null);
    }

    protected Optional<BlockUtil.FoundRectangle> getExitPortal(
            ServerLevel targetLevel,
            BlockPos startingPos,
            boolean bl,
            WorldBorder worldBorder
    ) {
        final PortalBuilder builder = new PortalBuilder(this.level(), targetLevel);
        final Optional<BlockUtil.FoundRectangle> portalRectangle = builder.findPortalAround(
                startingPos,
                worldBorder
        );
        if (portalRectangle.isPresent()) {
            return portalRectangle;
        }

        BetterEnd.LOGGER.error("Unable to locate an active portal");
        return portalRectangle;
    }

    protected Vec3 getRelativePortalPosition(Direction.Axis axis, BlockUtil.FoundRectangle foundRectangle) {
        return PortalShape.getRelativePosition(
                foundRectangle,
                axis,
                entity.position(),
                entity.getDimensions(entity.getPose())
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TravelerState) obj;
        return Objects.equals(this.entity, that.entity) &&
                Objects.equals(this.portalEntrancePos, that.portalEntrancePos) &&
                this.isInsidePortal == that.isInsidePortal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, portalEntrancePos, isInsidePortal);
    }

    @Override
    public String toString() {
        return "TravelerState[" +
                "entity=" + entity + ", " +
                "portalEntrancePos=" + portalEntrancePos + ", " +
                "isInsidePortal=" + isInsidePortal + ']';
    }

}
