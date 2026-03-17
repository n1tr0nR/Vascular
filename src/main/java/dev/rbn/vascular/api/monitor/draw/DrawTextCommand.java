package dev.rbn.vascular.api.monitor.draw;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.monitor.MonitorContext;
import dev.rbn.vascular.api.monitor.MonitorDrawCommand;
import dev.rbn.vascular.api.monitor.MonitorRenderContext;
import dev.rbn.vascular.content.block.MonitorBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

public class DrawTextCommand extends MonitorDrawCommand {
    private final Text text;
    private final int x;
    private final int y;
    private final int color;

    public DrawTextCommand(Text text, int x, int y, int color, int layer) {
        super(layer);
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    @Override
    public void render(MonitorRenderContext ctx) {
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

        stack.scale(0.00625F, 0.00625F, -0.00625F);
        float offset = this.layer * 0.01F;
        stack.translate(MonitorContext.MONITOR_SIZE.x + 10, MonitorContext.MONITOR_SIZE.y + 50, -9.9 + offset);

        stack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));


        ctx.textRenderer().draw(
                text,
                x, y,
                color,
                true,
                stack.peek().getPositionMatrix(),
                ctx.vertexConsumer(),
                TextRenderer.TextLayerType.NORMAL,
                0x00FFFFFF,
                LightmapTextureManager.MAX_LIGHT_COORDINATE
        );

        stack.pop();
    }
}
