package dev.rbn.vascular.client.render.entity;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.client.render.entity.state.BloodWeaponEntityRenderState;
import dev.rbn.vascular.content.entity.BloodWeaponEntity;
import dev.rbn.vascular.init.ModEntities;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class BloodWeaponEntityRenderer extends EntityRenderer<BloodWeaponEntity, BloodWeaponEntityRenderState> {
    private final ItemModelManager modelManager;

    public BloodWeaponEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.modelManager = context.getItemModelManager();
    }

    @Override
    public BloodWeaponEntityRenderState createRenderState() {
        return new BloodWeaponEntityRenderState(ItemStack.EMPTY);
    }

    @Override
    public void updateRenderState(BloodWeaponEntity entity, BloodWeaponEntityRenderState state, float tickProgress) {
        super.updateRenderState(entity, state, tickProgress);
        state.stack = entity.getStack();
        state.pitch = entity.getPitch(tickProgress);
        state.yaw = entity.getYaw(tickProgress);

        this.modelManager.clearAndUpdate(state.itemState, state.stack, ItemDisplayContext.NONE, entity.getEntityWorld(), null, 0);
    }

    @Override
    public void render(BloodWeaponEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        matrices.push();

        matrices.translate(0, 0.25F, 0);

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(state.yaw));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(state.pitch));

        float lerpedAge = MathHelper.lerp(
                MinecraftClient.getInstance().getRenderTickCounter().getTickProgress(false), state.age - 1f, state.age - 0f);

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(lerpedAge * 25));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float) Math.sin(lerpedAge) * 10));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) Math.cos(lerpedAge) * 10));

        queue.getBatchingQueue(0).submitCustom(matrices, RenderLayers.entityTranslucent(Vascular.id("textures/render/blood.png")), (matricesEntry, vertexConsumer) -> {
            vertex(matricesEntry, vertexConsumer, -0.75F, 0, -0.75F, 1, 1, 1, 1, 0, 0, state.light, OverlayTexture.DEFAULT_UV, 0, -1, 0);
            vertex(matricesEntry, vertexConsumer,  0.75F, 0, -0.75F, 1, 1, 1, 1, 1, 0, state.light, OverlayTexture.DEFAULT_UV, 0, -1, 0);
            vertex(matricesEntry, vertexConsumer,  0.75F, 0,  0.75F, 1, 1, 1, 1, 1, 1, state.light, OverlayTexture.DEFAULT_UV, 0, -1, 0);
            vertex(matricesEntry, vertexConsumer, -0.75F, 0,  0.75F, 1, 1, 1, 1, 0, 1, state.light, OverlayTexture.DEFAULT_UV, 0, -1, 0);
        });

        matrices.pop();

        matrices.push();

        matrices.translate(0, 0.15F, 0);

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(state.yaw));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(state.pitch));

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(lerpedAge * 20));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float) Math.sin(lerpedAge) * 30));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) Math.cos(lerpedAge) * 5));

        queue.getBatchingQueue(0).submitCustom(matrices, RenderLayers.entityTranslucentEmissive(Vascular.id("textures/render/blood.png")), (matricesEntry, vertexConsumer) -> {
            vertex(matricesEntry, vertexConsumer, -0.75F, 0, -0.75F, 1, 1, 1, 0.5F, 0, 0, state.light, OverlayTexture.DEFAULT_UV, 0, -1, 0);
            vertex(matricesEntry, vertexConsumer,  0.75F, 0, -0.75F, 1, 1, 1, 0.5F, 1, 0, state.light, OverlayTexture.DEFAULT_UV, 0, -1, 0);
            vertex(matricesEntry, vertexConsumer,  0.75F, 0,  0.75F, 1, 1, 1, 0.5F, 1, 1, state.light, OverlayTexture.DEFAULT_UV, 0, -1, 0);
            vertex(matricesEntry, vertexConsumer, -0.75F, 0,  0.75F, 1, 1, 1, 0.5F, 0, 1, state.light, OverlayTexture.DEFAULT_UV, 0, -1, 0);
        });

        matrices.pop();

        super.render(state, matrices, queue, cameraState);
    }

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
}
