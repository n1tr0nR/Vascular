package dev.rbn.vascular.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.monitor.MonitorBlockEntityRenderer;
import dev.rbn.vascular.client.blood.PlayerBloodTaking;
import dev.rbn.vascular.client.render.hud.SyringeTooltipRenderer;
import dev.rbn.vascular.client.render.model.NitronFeaturesModel;
import dev.rbn.vascular.client.util.UIHelper;
import dev.rbn.vascular.init.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.fabricmc.fabric.impl.client.rendering.BlockEntityRendererRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import org.joml.Matrix4f;

public class VascularClient implements ClientModInitializer {
    public static final EntityModelLayer NITRON = new EntityModelLayer(
            Vascular.id("nitron"), "main"
    );

    public static PlayerBloodTaking bloodTaking;

    public static final RenderStateDataKey<Boolean> IS_GAY_FOX = RenderStateDataKey.create(() -> "is_gay_fox");

    @Override
    public void onInitializeClient() {
        HudElementRegistry.addLast(Vascular.id("syringe"), new SyringeTooltipRenderer());

        EntityModelLayerRegistry.registerModelLayer(NITRON, NitronFeaturesModel::getTexturedModelData);

        BlockEntityRendererRegistryImpl.register(ModBlocks.MONITOR_BE, MonitorBlockEntityRenderer::new);

        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            bloodTaking.tick();
        });
        bloodTaking = new PlayerBloodTaking(MinecraftClient.getInstance());

        WorldRenderEvents.BEFORE_TRANSLUCENT.register(context -> {
            UIHelper.WorldRenderState.VIEW = new Matrix4f(RenderSystem.getModelViewMatrix());
            UIHelper.WorldRenderState.PROJECTION = new Matrix4f(MinecraftClient.getInstance().gameRenderer.getProjectionMatrix(0));
        });
    }
}