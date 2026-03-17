package dev.rbn.vascular.api.monitor;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.content.blockEntity.MonitorBlockEntity;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.jspecify.annotations.Nullable;

public class MonitorBlockEntityRenderer implements BlockEntityRenderer<MonitorBlockEntity, MonitorBlockEntityRenderState> {
    public MonitorBlockEntityRenderer(BlockEntityRendererFactory.Context context){
    }

    @Override
    public MonitorBlockEntityRenderState createRenderState() {
        return new MonitorBlockEntityRenderState();
    }

    @Override
    public void updateRenderState(MonitorBlockEntity blockEntity, MonitorBlockEntityRenderState state, float tickProgress, Vec3d cameraPos, ModelCommandRenderer.@Nullable CrumblingOverlayCommand crumblingOverlay) {
        BlockEntityRenderer.super.updateRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
        state.stack = blockEntity.getCurrentVhs();
    }

    @Override
    public void render(MonitorBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        queue.submitCustom(
                matrices,
                RenderLayers.entityTranslucent(Vascular.id("textures/monitor/white.png")),
                new MonitorGeometry(state, matrices)
        );
    }
}
