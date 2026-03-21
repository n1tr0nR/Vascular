package dev.rbn.vascular.api.monitor.draw;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.monitor.MonitorContext;
import dev.rbn.vascular.api.monitor.MonitorRenderContext;
import dev.rbn.vascular.content.block.MonitorBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

public class FillCommand extends MonitorDrawCommand {
    public final int x1, y1, x2, y2;
    public final int color;

    public FillCommand(int x1, int y1, int x2, int y2, int color, int layer) {
        super(layer);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
    }

    @Override
    public void render(MonitorRenderContext ctx) {
        VertexConsumer consumer = ctx.vertexConsumer().getBuffer(RenderLayers.entityTranslucent(Vascular.id("textures/monitor/white.png")));
        MatrixStack stack = ctx.matrices();
        BlockEntityRenderState state = ctx.state();

        stack.push();

        Vec3d cameraPos = MinecraftClient.getInstance().gameRenderer.getCamera().getCameraPos();
        BlockPos pos = state.pos;

        stack.translate(
                pos.getX() - cameraPos.x,
                pos.getY() - cameraPos.y,
                pos.getZ() - cameraPos.z
        );

        if (state.blockState.contains(MonitorBlock.FACING)){
            Direction facing = state.blockState.get(MonitorBlock.FACING);
            if (facing.equals(Direction.EAST)){
                stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
                stack.translate(0, 0, -1);
            }
            if (facing.equals(Direction.WEST)){
                stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
                stack.translate(-1, 0, 0);
            }
            if (facing.equals(Direction.SOUTH)){
                stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                stack.translate(-1, 0, -1);
            }
        }

        stack.scale(0.00625F, 0.00625F, 0.00625F);
        float offset = this.layer * 0.01F;
        stack.translate(MonitorContext.MONITOR_SIZE.x + 10, MonitorContext.MONITOR_SIZE.y + 50, 9.9 - offset);

        float[] colors = hexToRGBA(this.color);

        stack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));

        this.vertex(stack.peek(), consumer, x1, y1, 0, colors[1], colors[2], colors[3], colors[0], 0, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, 0, 0, 1);
        this.vertex(stack.peek(), consumer, x2, y1, 0, colors[1], colors[2], colors[3], colors[0], 1, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, 0, 0, 1);
        this.vertex(stack.peek(), consumer, x2, y2, 0, colors[1], colors[2], colors[3], colors[0], 1, 1, LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, 0, 0, 1);
        this.vertex(stack.peek(), consumer, x1, y2, 0, colors[1], colors[2], colors[3], colors[0], 0, 1, LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, 0, 0, 1);

        stack.pop();
    }
}
