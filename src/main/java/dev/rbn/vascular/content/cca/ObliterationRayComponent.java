package dev.rbn.vascular.content.cca;

import dev.rbn.vascular.content.item.ObliterationRayItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.BlockBreakingProgressS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class ObliterationRayComponent implements AutoSyncedComponent, CommonTickingComponent {

    public static ComponentKey<ObliterationRayComponent> KEY;

    private final PlayerEntity player;

    private BlockPos targetPos = null;
    private Vec3d hitPos = null;

    private boolean isUsingOR = false;
    private boolean hasTarget = false;

    private int ticksTillRecheck = 0;
    private float breakProgress = 0;

    public ObliterationRayComponent(PlayerEntity player) {
        this.player = player;
    }

    // ========================
    // TICK
    // ========================
    @Override
    public void tick() {
        boolean shouldSync = false;

        boolean using = player.isUsingItem()
                && player.getActiveItem().getItem() instanceof ObliterationRayItem;

        if (using) {
            if (!isUsingOR) {
                isUsingOR = true;
                shouldSync = true;
            }

            if (ticksTillRecheck > 0) {
                ticksTillRecheck--;
            }

            if (ticksTillRecheck <= 0) {
                ticksTillRecheck = 2;

                BlockPos oldTarget = targetPos;
                hasTarget = refindTarget();

                // reset progress if target changed
                if (oldTarget == null || !oldTarget.equals(targetPos)) {
                    resetBreaking();
                }
            }

            if (hasTarget && targetPos != null && hitPos != null) {
                if (player.getEntityWorld() instanceof ServerWorld serverWorld) {

                    // particles
                    serverWorld.spawnParticles(
                            ParticleTypes.END_ROD,
                            hitPos.x, hitPos.y, hitPos.z,
                            2, 0.01, 0.01, 0.01, 0
                    );

                    float hardness = player.getEntityWorld().getBlockState(targetPos).getHardness(player.getEntityWorld(), targetPos);
                    if (hardness > 0) {
                        float speed = 10.0f;

                        breakProgress += speed / hardness;

                        int stage = (int) Math.min(9, breakProgress / 2);

                        serverWorld.setBlockBreakingInfo(
                                player.getId(),
                                targetPos,
                                stage
                        );

                        // break block
                        if (breakProgress >= 20) {
                            serverWorld.breakBlock(targetPos, true, player);
                            breakProgress = 0;
                        }
                    }
                }
            }

        } else {
            if (isUsingOR) {
                isUsingOR = false;
                shouldSync = true;
            }

            resetBreaking();
        }

        if (shouldSync) {
            sync();
        }
    }

    // ========================
    // TARGETING
    // ========================
    public boolean refindTarget() {
        double range = 5.0;

        Vec3d start = player.getCameraPosVec(1.0F);
        Vec3d direction = player.getRotationVec(1.0F);
        Vec3d end = start.add(direction.multiply(range));

        RaycastContext context = new RaycastContext(
                start,
                end,
                RaycastContext.ShapeType.OUTLINE,
                RaycastContext.FluidHandling.NONE,
                player
        );

        BlockHitResult result = player.getEntityWorld().raycast(context);

        if (result.getType() == HitResult.Type.BLOCK) {
            targetPos = result.getBlockPos();
            hitPos = result.getPos();
            return true;
        }

        targetPos = null;
        hitPos = end;
        return false;
    }

    // ========================
    // RESET
    // ========================
    private void resetBreaking() {
        if (targetPos != null && player.getEntityWorld() instanceof ServerWorld serverWorld) {
            serverWorld.setBlockBreakingInfo(player.getId(), targetPos, -1);
        }

        breakProgress = 0;
    }

    // ========================
    // SYNC
    // ========================
    public void sync() {
        if (KEY != null) {
            KEY.sync(player);
        }
    }

    // ========================
    // DATA
    // ========================
    @Override
    public void writeData(WriteView writeView) {
        if (targetPos != null) {
            writeView.putInt("tx", targetPos.getX());
            writeView.putInt("ty", targetPos.getY());
            writeView.putInt("tz", targetPos.getZ());
        }

        if (hitPos != null) {
            writeView.putDouble("hx", hitPos.x);
            writeView.putDouble("hy", hitPos.y);
            writeView.putDouble("hz", hitPos.z);
        }

        writeView.putBoolean("using", isUsingOR);
        writeView.putBoolean("hasTarget", hasTarget);
        writeView.putInt("recheck", ticksTillRecheck);
    }

    @Override
    public void readData(ReadView readView) {
        if (readView.contains("tx")) {
            targetPos = new BlockPos(
                    readView.getInt("tx", 0),
                    readView.getInt("ty", 0),
                    readView.getInt("tz", 0)
            );
        } else {
            targetPos = null;
        }

        if (readView.contains("hx")) {
            hitPos = new Vec3d(
                    readView.getDouble("hx", 0),
                    readView.getDouble("hy", 0),
                    readView.getDouble("hz", 0)
            );
        } else {
            hitPos = null;
        }

        isUsingOR = readView.getBoolean("using", false);
        hasTarget = readView.getBoolean("hasTarget", false);
        ticksTillRecheck = readView.getInt("recheck", 0);
    }

    // ========================
    // GETTERS
    // ========================
    public BlockPos getTargetPos() {
        return targetPos;
    }

    public Vec3d getHitPos() {
        return hitPos;
    }

    public boolean isUsingOR() {
        return isUsingOR;
    }

    public boolean hasTarget() {
        return hasTarget;
    }
}