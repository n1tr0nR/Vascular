package dev.rbn.vascular.api.monitor;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public abstract class MonitorDrawCommand {
    public int layer;

    public MonitorDrawCommand(int layer) {
        this.layer = layer;
    }

    public abstract void render(MonitorRenderContext ctx);

    public void vertex(MatrixStack.Entry entry, VertexConsumer consumer,
                       float x, float y, float z,
                       float r, float g, float b, float a,
                       float u, float v,
                       int light, int overlay,
                       float normalX, float normalY, float normalZ) {

        consumer.vertex(entry.getPositionMatrix(), x, y, z)
                .color(r, g, b, a)
                .texture(u, v)
                .overlay(overlay)
                .light(light)
                .normal(entry, normalX, normalY, normalZ);
    }

    public static float[] hexToRGBA(int hex) {
        return new float[] {
                ((hex >> 24) & 0xFF) / 255f,
                ((hex >> 16) & 0xFF) / 255f,
                ((hex >> 8)  & 0xFF) / 255f,
                (hex & 0xFF) / 255f
        };
    }
}

