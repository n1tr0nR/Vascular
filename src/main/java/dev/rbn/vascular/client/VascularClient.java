package dev.rbn.vascular.client;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.BloodTypeEntityRegistry;
import dev.rbn.vascular.api.GeneTypeEntityRegistry;
import dev.rbn.vascular.api.blood_types.BloodType;
import dev.rbn.vascular.api.genes.Gene;
import dev.rbn.vascular.client.render.hud.SyringeTooltipRenderer;
import dev.rbn.vascular.client.render.model.NitronFeaturesModel;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.text.Text;

public class VascularClient implements ClientModInitializer {
    public static final EntityModelLayer NITRON = new EntityModelLayer(
            Vascular.id("nitron"), "main"
    );

    public static final RenderStateDataKey<Boolean> IS_GAY_FOX = RenderStateDataKey.create(() -> "is_gay_fox");

    @Override
    public void onInitializeClient() {
        HudElementRegistry.addLast(Vascular.id("syringe"), new SyringeTooltipRenderer());

        EntityModelLayerRegistry.registerModelLayer(NITRON, NitronFeaturesModel::getTexturedModelData);

        //Debug
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            if (minecraftClient.player != null && minecraftClient.world != null){
                BloodType clientType = BloodTypeEntityRegistry.getPlayerBloodType(minecraftClient.player.getUuid());
                Gene clientGene = GeneTypeEntityRegistry.getPlayerGene(minecraftClient.player.getUuid());

                minecraftClient.player.sendMessage(
                        Text.translatable(clientType.getTranslationKey())
                                .append(" | ")
                                .append(Text.translatable(clientGene.getTranslationKey())),
                        true
                );
            }
        });
    }
}
