package dev.rbn.vascular.api.monitor;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

public record MonitorRenderContext(MatrixStack matrices, VertexConsumerProvider vertexConsumer,
                                   TextRenderer textRenderer, BlockEntityRenderState state) {
}

