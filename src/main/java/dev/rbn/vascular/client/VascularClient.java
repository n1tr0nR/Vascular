package dev.rbn.vascular.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.monitor.MonitorBlockEntityRenderer;
import dev.rbn.vascular.client.util.PlayerBloodTaking;
import dev.rbn.vascular.client.render.entity.BloodWeaponEntityRenderer;
import dev.rbn.vascular.client.render.hud.SyringeTooltipRenderer;
import dev.rbn.vascular.client.render.item.beam.ElectricityBeam;
import dev.rbn.vascular.client.render.model.NitronFeaturesModel;
import dev.rbn.vascular.client.util.UIHelper;
import dev.rbn.vascular.content.cca.ModCCA;
import dev.rbn.vascular.content.cca.ObliterationRayComponent;
import dev.rbn.vascular.content.item.ObliterationRayItem;
import dev.rbn.vascular.init.ModBlocks;
import dev.rbn.vascular.init.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.fabricmc.fabric.impl.client.rendering.BlockEntityRendererRegistryImpl;
import net.fabricmc.fabric.impl.client.rendering.EntityRendererRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
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

        BlockRenderLayerMap.putBlock(ModBlocks.MEDICAL_BED, BlockRenderLayer.CUTOUT);

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

        EntityRendererRegistryImpl.register(ModEntities.BLOOD_WEAPON, BloodWeaponEntityRenderer::new);

        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.world != null){
                for (PlayerEntity player : client.world.getPlayers()){
                    ObliterationRayComponent component = ModCCA.OBLITERATION_RAY.get(player);
                    if (player.isUsingItem() && player.getActiveItem().getItem() instanceof ObliterationRayItem){
                        context.matrices().push();

                        Vec3d camera = client.gameRenderer.getCamera().getCameraPos();
                        context.matrices().translate(-camera.x, -camera.y, -camera.z);

                        ElectricityBeam beam = new ElectricityBeam(component, player);
                        beam.updatePositions();
                        beam.renderBeam(context.commandQueue(), context.matrices());

                        context.matrices().pop();
                    }
                }
            }
        });
    }
}