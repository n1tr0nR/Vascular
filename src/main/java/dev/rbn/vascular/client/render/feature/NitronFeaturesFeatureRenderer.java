package dev.rbn.vascular.client.render.feature;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.client.VascularClient;
import dev.rbn.vascular.client.render.model.NitronFeaturesModel;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;

public class NitronFeaturesFeatureRenderer extends FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {
    private final NitronFeaturesModel model;

    public NitronFeaturesFeatureRenderer(FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> context, EntityRendererFactory.Context loader) {
        super(context);
        model = new NitronFeaturesModel(loader.getPart(VascularClient.NITRON));
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, PlayerEntityRenderState state, float limbAngle, float limbDistance) {
        if (state.getData(VascularClient.IS_GAY_FOX) != null && Boolean.FALSE.equals(state.getData(VascularClient.IS_GAY_FOX))) return;

        matrices.push();

        this.model.resetTransforms();

        this.model.Head.setOrigin(this.getContextModel().head.originX, this.getContextModel().head.originY - 12, this.getContextModel().head.originZ);
        this.model.Head.yaw = this.getContextModel().head.yaw;
        this.model.Head.pitch = this.getContextModel().head.pitch;
        this.model.Head.visible = this.getContextModel().head.visible;

        this.model.Body.setOrigin(this.getContextModel().body.originX, this.getContextModel().body.originY - 12, this.getContextModel().body.originZ);
        this.model.Body.yaw = this.getContextModel().body.yaw;
        this.model.Body.pitch = this.getContextModel().body.pitch;
        this.model.Body.visible = this.getContextModel().body.visible;

        this.model.breasts.resetTransform();
        this.model.breasts.originY = this.model.breasts.originY + 0.5F;

        this.model.RightLeg.setOrigin(this.getContextModel().rightLeg.originX, this.getContextModel().rightLeg.originY, this.getContextModel().rightLeg.originZ);
        this.model.RightLeg.yaw = this.getContextModel().rightLeg.yaw;
        this.model.RightLeg.pitch = this.getContextModel().rightLeg.pitch;
        this.model.RightLeg.visible = this.getContextModel().rightLeg.visible;

        this.model.LeftLeg.setOrigin(this.getContextModel().leftLeg.originX, this.getContextModel().leftLeg.originY, this.getContextModel().leftLeg.originZ);
        this.model.LeftLeg.yaw = this.getContextModel().leftLeg.yaw;
        this.model.LeftLeg.pitch = this.getContextModel().leftLeg.pitch;
        this.model.LeftLeg.visible = this.getContextModel().leftLeg.visible;

        this.model.bow.pitch = (float) ((state.field_53537 * 0.2F) * (Math.PI / 180));
        this.model.tail.pitch = (float) ((state.field_53537 * 0.6F) * (Math.PI / 180));

        this.model.bow.visible = state.equippedHeadStack.isEmpty();
        this.model.breasts.visible = state.equippedChestStack.isEmpty();
        this.model.hood.visible = state.equippedChestStack.isEmpty();

        queue.submitModel(this.model, state, matrices, RenderLayers.entityTranslucent(Vascular.id("textures/entity/nitron_features.png")), light,
                state.hurt ? OverlayTexture.field_32953 : OverlayTexture.DEFAULT_UV, state.outlineColor, null);

        matrices.pop();
    }
}
