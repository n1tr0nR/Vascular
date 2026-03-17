package dev.rbn.vascular.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector4f;

public class UIHelper {
    public static @Nullable Vector2i worldToScreen(Vec3d worldPos) {
        if (WorldRenderState.VIEW == null || WorldRenderState.PROJECTION == null)
            return null;

        MinecraftClient client = MinecraftClient.getInstance();
        Vec3d camPos = client.gameRenderer.getCamera().getCameraPos();

        Vector4f pos = new Vector4f(
                (float)(worldPos.x - camPos.x),
                (float)(worldPos.y - camPos.y),
                (float)(worldPos.z - camPos.z),
                1.0f
        );

        pos.mul(WorldRenderState.VIEW);
        pos.mul(WorldRenderState.PROJECTION);

        if (pos.w <= 0.0f) return null;

        pos.div(pos.w);

        int x = (int)((pos.x * 0.5f + 0.5f) * client.getWindow().getScaledWidth());
        int y = (int)((-pos.y * 0.5f + 0.5f) * client.getWindow().getScaledHeight());

        return new Vector2i(x, y);
    }

    public static class WorldRenderState {
        public static Matrix4f VIEW;
        public static Matrix4f PROJECTION;
    }
}