package dev.rbn.vascular.client.render.hud;

import com.sun.jna.platform.win32.COM.util.IComEnum;
import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.client.VascularClient;
import dev.rbn.vascular.client.util.UIHelper;
import dev.rbn.vascular.content.data.SyringeComponent;
import dev.rbn.vascular.init.ModDataComponents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.joml.Vector2i;

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

        if (VascularClient.bloodTaking.isTakingBlood()){
            drawContext.drawText(
                    client.textRenderer,
                    Text.literal("Taking Blood"),
                    (drawContext.getScaledWindowWidth() / 2) - client.textRenderer.getWidth(Text.literal("Taking Blood")) / 2,
                    drawContext.getScaledWindowHeight() / 2 + 20,
                    0xFFff3b1c,
                    true
            );

            int amountFull = VascularClient.bloodTaking.getBloodTook();

            for (int i = 0; i < 5; i++) {
                Identifier HEARTS_TEXTURE = Identifier.ofVanilla("hud/heart/container");

                drawContext.drawGuiTexture(
                        RenderPipelines.GUI_TEXTURED,
                        HEARTS_TEXTURE,
                        (drawContext.getScaledWindowWidth() / 2) - 20 + (i * 8),
                        drawContext.getScaledWindowHeight() / 2 + 8,
                        9, 9
                );

                int heartIndex = i * 2;
                if (amountFull >= heartIndex + 2) {
                    drawContext.drawGuiTexture(
                            RenderPipelines.GUI_TEXTURED,
                            Identifier.ofVanilla("hud/heart/full"),
                            (drawContext.getScaledWindowWidth() / 2) - 20 + (i * 8),
                            drawContext.getScaledWindowHeight() / 2 + 8,
                            9, 9
                    );
                } else if (amountFull == heartIndex + 1) {
                    drawContext.drawGuiTexture(
                            RenderPipelines.GUI_TEXTURED,
                            Identifier.ofVanilla("hud/heart/half"),
                            (drawContext.getScaledWindowWidth() / 2) - 20 + (i * 8),
                            drawContext.getScaledWindowHeight() / 2 + 8,
                            9, 9
                    );
                }
            }

            Identifier ITEM;

            if (amountFull > 9){
                ITEM = Vascular.id("hud/bleed/blood_scythe");
            } else if (amountFull > 6){
                ITEM = Vascular.id("hud/bleed/blood_spear");
            } else if (amountFull > 3){
                ITEM = Vascular.id("hud/bleed/blood_knife");
            } else {
                ITEM = Vascular.id("hud/bleed/blood_dagger");
            }

            drawContext.drawGuiTexture(
                    RenderPipelines.GUI_TEXTURED,
                    ITEM,
                    (drawContext.getScaledWindowWidth() / 2) - 8,
                    drawContext.getScaledWindowHeight() / 2 + 30,
                    16, 16
            );
        }

        if (VascularClient.bloodTaking.lastBloodThrown != null && client.world != null){
            Entity entity = VascularClient.bloodTaking.lastBloodThrown;
            Vector2i pos = UIHelper.worldToScreen(
                    entity.getEntityPos()
            );
            if (pos != null){
                drawContext.drawText(
                        client.textRenderer,
                        Text.literal("AA"),
                        pos.x, pos.y,
                        0xFFFFFFFF,
                        true
                );
            }
        }
    }
}
