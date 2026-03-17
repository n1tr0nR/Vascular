package dev.rbn.vascular.api.monitor;

import dev.rbn.vascular.api.monitor.set.MonitorStaticDisplay;
import dev.rbn.vascular.content.block.MonitorBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public class MonitorGeometry implements OrderedRenderCommandQueue.Custom {
    private final MonitorBlockEntityRenderState state;
    private final MatrixStack stack;

    public MonitorGeometry(MonitorBlockEntityRenderState state, MatrixStack stack) {
        this.state = state;
        this.stack = stack;
    }

    @Override
    public void render(MatrixStack.Entry matricesEntry, VertexConsumer vertexConsumer) {
        VertexConsumerProvider provider = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        MonitorRenderContext context = new MonitorRenderContext(this.stack, provider, MinecraftClient.getInstance().textRenderer, state);

        MonitorDisplay display = null;

        if (!this.state.blockState.get(MonitorBlock.POWERED)){
            return;
        }

        if (this.state.stack.isOf(Items.DIRT)){
            display = new MonitorStaticDisplay(new MonitorContext());
            display.render(ItemStack.EMPTY, this.state.pos);
        } else if (this.state.stack.getItem() instanceof VhsItem item){
            display = item.getDisplay();
            display.render(this.state.stack, this.state.pos);
        }

        if (display != null){
            MonitorRenderer.render(display.context, context);
        }
    }
}
