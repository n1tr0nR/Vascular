package dev.rbn.vascular.client.render.hud;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.content.data.SyringeComponent;
import dev.rbn.vascular.init.ModDataComponents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;

public class SyringeTooltipRenderer implements HudElement {
    private static final Identifier TEXTURE = Vascular.id("syringe");

    @Override
    public void render(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        /*if (client.crosshairTarget instanceof EntityHitResult result && client.player != null){
            SyringeComponent component = client.player.getStackInHand(Hand.MAIN_HAND).get(ModDataComponents.DNA);
            if (result.getEntity() instanceof PlayerEntity player && component != null){
                BloodType target = BloodType.getBloodType(player);
                boolean canRecieve = target.canRecieveFrom(component.type());

                int x = drawContext.getScaledWindowWidth() / 2;
                int y = drawContext.getScaledWindowHeight() / 2;

                drawContext.drawGuiTexture(
                        RenderPipelines.GUI_TEXTURED,
                        TEXTURE,
                        48, 48,
                        0, canRecieve ? 18 : 0,
                        x - 9, y + 5,
                        18, 18,
                        0xFFFFFFFF
                );
            }
        }*/
    }
}
