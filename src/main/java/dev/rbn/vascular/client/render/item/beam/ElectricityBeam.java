package dev.rbn.vascular.client.render.item.beam;

import dev.rbn.vascular.content.cca.ObliterationRayComponent;
import net.minecraft.client.render.*;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.Random;

public class ElectricityBeam {

    private final ObliterationRayComponent component;
    private final PlayerEntity parent;

    private Vec3d startPosition;
    private Vec3d endPosition;

    private long seed = 0;
    private int lastSeedTick = 0;

    public ElectricityBeam(ObliterationRayComponent component, PlayerEntity parent) {
        this.component = component;
        this.parent = parent;
    }

    private long getSeed() {
        int tick = parent.age;

        if (tick - lastSeedTick > 40) {
            lastSeedTick = tick;
            seed = parent.getEntityWorld().random.nextLong();
        }

        return seed;
    }

    public void updatePositions() {
        startPosition = parent.getEntityPos().add(0, 1.4, 0);

        if (component.getHitPos() != null) {
            endPosition = component.getHitPos();
        } else {
            Vec3d look = parent.getRotationVec(1.0F);
            endPosition = startPosition.add(look.multiply(5.0));
        }
    }

    public void renderBeam(OrderedRenderCommandQueue queue, MatrixStack stack) {
        if (startPosition == null || endPosition == null) return;

        long seed = getSeed();
        Vec3d[] mainPoints = generatePoints(startPosition, endPosition, 12, seed);

        queue.getBatchingQueue(0).submitCustom(stack, RenderLayers.lightning(), (entry, consumer) -> {
            drawConnectedBeam(entry, consumer, mainPoints, 0.1f, component.hasTarget());
            for (int i = 0; i < 4; i++) {
                Vec3d[] arcPoints = generateCurvedPoints(startPosition, endPosition, 10, seed + i * 999);
                drawConnectedBeam(entry, consumer, arcPoints, 0.01f, component.hasTarget());
            }
        });
    }

    private void drawConnectedBeam(MatrixStack.Entry entry, VertexConsumer consumer,
                                   Vec3d[] points, float width, boolean hit) {

        Vec3d prevLeft = null;
        Vec3d prevRight = null;

        for (int i = 0; i < points.length - 1; i++) {
            Vec3d a = points[i];
            Vec3d b = points[i + 1];

            Vec3d dir = b.subtract(a).normalize();

            Vec3d side = dir.crossProduct(new Vec3d(0, 1, 0));
            if (side.lengthSquared() < 0.001) {
                side = dir.crossProduct(new Vec3d(1, 0, 0));
            }
            side = side.normalize().multiply(width);

            Vec3d leftStart = a.add(side);
            Vec3d rightStart = a.subtract(side);
            Vec3d leftEnd = b.add(side);
            Vec3d rightEnd = b.subtract(side);

            if (prevLeft != null && prevRight != null) {
                leftStart = prevLeft;
                rightStart = prevRight;
            }

            drawQuad(entry, consumer, leftStart, rightStart, rightEnd, leftEnd, hit, width);

            prevLeft = leftEnd;
            prevRight = rightEnd;
        }
    }

    private void drawQuad(MatrixStack.Entry entry, VertexConsumer consumer,
                          Vec3d p1, Vec3d p2, Vec3d p3, Vec3d p4,
                          boolean hit, float width) {

        int light = LightmapTextureManager.MAX_LIGHT_COORDINATE;

        Color color = hit
                ? width > 0.05f ? new Color(255, 255, 200) : new Color(255, 255, 0)
                : new Color(255, 50, 50);

        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        float alpha = width > 0.05f ? (hit ? 0.8f : 0.5f) : (hit ? 0.5f : 0.25f);

        vertex(entry, consumer, p1, r, g, b, alpha, 0, 0, light, 0, 1, 0);
        vertex(entry, consumer, p2, r, g, b, alpha, 1, 0, light, 0, 1, 0);
        vertex(entry, consumer, p3, r, g, b, alpha, 1, 1, light, 0, 1, 0);
        vertex(entry, consumer, p4, r, g, b, alpha, 0, 1, light, 0, 1, 0);

        vertex(entry, consumer, p4, r, g, b, alpha, 0, 1, light, 0, -1, 0);
        vertex(entry, consumer, p3, r, g, b, alpha, 1, 1, light, 0, -1, 0);
        vertex(entry, consumer, p2, r, g, b, alpha, 1, 0, light, 0, -1, 0);
        vertex(entry, consumer, p1, r, g, b, alpha, 0, 0, light, 0, -1, 0);
    }

    private void vertex(MatrixStack.Entry entry, VertexConsumer consumer,
                        Vec3d pos,
                        float r, float g, float b, float a,
                        float u, float v,
                        int light,
                        float nx, float ny, float nz) {

        consumer.vertex(entry.getPositionMatrix(),
                        (float) pos.x, (float) pos.y, (float) pos.z)
                .color(r, g, b, a)
                .texture(u, v)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(entry, nx, ny, nz);
    }

    public Vec3d[] generatePoints(Vec3d start, Vec3d end, int segments, long seed) {
        Vec3d[] points = new Vec3d[segments + 1];
        points[0] = start;
        points[segments] = end;

        Random random = new Random(seed);

        Vec3d dir = end.subtract(start).normalize();
        Vec3d perpendicular = dir.crossProduct(new Vec3d(0, 1, 0));
        if (perpendicular.lengthSquared() < 0.01) {
            perpendicular = dir.crossProduct(new Vec3d(1, 0, 0));
        }
        perpendicular = perpendicular.normalize();

        for (int i = 1; i < segments; i++) {
            float t = i / (float) segments;

            Vec3d pos = start.lerp(end, t);
            double offset = (random.nextDouble() - 0.5) * 0.5;

            points[i] = pos.add(perpendicular.multiply(offset));
        }

        return points;
    }

    public Vec3d[] generateCurvedPoints(Vec3d start, Vec3d end, int segments, long seed) {
        Vec3d[] points = new Vec3d[segments + 1];
        points[0] = start;
        points[segments] = end;

        Random random = new Random(seed);

        Vec3d dir = end.subtract(start).normalize();

        Vec3d side = dir.crossProduct(new Vec3d(0, 1, 0));
        if (side.lengthSquared() < 0.01) {
            side = dir.crossProduct(new Vec3d(1, 0, 0));
        }
        side = side.normalize();

        Vec3d up = dir.crossProduct(side).normalize();

        Vec3d curveDir = side.multiply(random.nextDouble() - 0.5)
                .add(up.multiply(random.nextDouble() - 0.5))
                .normalize();

        double maxOffset = random.nextDouble() * 0.5;

        for (int i = 1; i < segments; i++) {
            float t = i / (float) segments;

            Vec3d base = start.lerp(end, t);
            double curve = Math.sin(t * Math.PI);

            Vec3d offset = curveDir.multiply(curve * maxOffset);

            offset = offset.add(side.multiply((random.nextDouble() - 0.5) * 0.25));
            offset = offset.add(up.multiply((random.nextDouble() - 0.5) * 0.25));

            points[i] = base.add(offset);
        }

        return points;
    }

    public Vec3d getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Vec3d startPosition) {
        this.startPosition = startPosition;
    }

    public Vec3d getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Vec3d endPosition) {
        this.endPosition = endPosition;
    }
}