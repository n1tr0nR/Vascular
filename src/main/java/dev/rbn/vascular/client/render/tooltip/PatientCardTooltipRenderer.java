package dev.rbn.vascular.client.render.tooltip;

import dev.rbn.vascular.content.data.PatientCardComponent;
import dev.rbn.vascular.init.ModDataComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.HoveredTooltipPositioner;
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipPositioner;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2ic;

import java.util.List;
import java.util.Optional;

public class PatientCardTooltipRenderer extends TooltipBackgroundRenderer {
    private final DrawContext context;

    public PatientCardTooltipRenderer(DrawContext context) {
        this.context = context;
    }

    public void drawTooltip(TextRenderer textRenderer, List<Text> text, Optional<TooltipData> data, int x, int y, @Nullable Identifier texture, ItemStack stack) {
        List<TooltipComponent> list = text.stream().map(Text::asOrderedText).map(TooltipComponent::of).collect(Util.toArrayList());
        data.ifPresent((datax) -> list.add(list.isEmpty() ? 0 : 1, TooltipComponent.of(datax)));
        this.drawTooltip(textRenderer, list, x, y, stack);
    }

    private void drawTooltip(TextRenderer textRenderer, List<TooltipComponent> components, int x, int y, ItemStack stack) {
        if (!components.isEmpty()) {
            if (context.tooltipDrawer == null) {
                context.tooltipDrawer = () -> this.drawTooltipImmediately(textRenderer, components, x, y, HoveredTooltipPositioner.INSTANCE, null, stack);
            }
        }
    }

    public void drawTooltipImmediately(TextRenderer textRenderer, List<TooltipComponent> components, int x, int y, TooltipPositioner positioner, @Nullable Identifier texture, ItemStack stack) {
        int i = 0;
        int j = components.size() == 1 ? -2 : 0;

        for(TooltipComponent tooltipComponent : components) {
            int k = tooltipComponent.getWidth(textRenderer);
            if (k > i) {
                i = k;
            }

            j += tooltipComponent.getHeight(textRenderer);
        }

        int l = i;
        int m = j;
        Vector2ic vector2ic = positioner.getPosition(context.getScaledWindowWidth(), context.getScaledWindowHeight(), x, y, i, j);
        int n = vector2ic.x();
        int o = vector2ic.y();
        context.getMatrices().pushMatrix();
        TooltipBackgroundRenderer.render(context, n, o, i, j, texture);
        int p = o;

        for(int q = 0; q < components.size(); ++q) {
            TooltipComponent tooltipComponent2 = (TooltipComponent)components.get(q);
            tooltipComponent2.drawText(context, textRenderer, n, p);
            p += tooltipComponent2.getHeight(textRenderer) + (q == 0 ? 2 : 0);
        }

        p = o;

        for(int q = 0; q < components.size(); ++q) {
            TooltipComponent tooltipComponent2 = (TooltipComponent)components.get(q);
            tooltipComponent2.drawItems(textRenderer, n, p, l, m, context);
            p += tooltipComponent2.getHeight(textRenderer) + (q == 0 ? 2 : 0);
        }

        PatientCardComponent component = stack.get(ModDataComponents.PATIENT_CARD);
        if (component != null){
            float scrollSpeed = 0.025F;

            long time = MinecraftClient.getInstance().world.getTimeOfDay() % 24000;
            float partialTicks = MinecraftClient.getInstance().getRenderTickCounter().getTickProgress(false);
            float totalTime = time + partialTicks;

            int textureWidth = 80;
            int textureHeight = 32;

            int scroll = (int)((totalTime * scrollSpeed * textureWidth) % textureWidth);

            Identifier dnaStrand = component.gene().getDnaStrandTextureLocation();

            int remaining = textureWidth - scroll;

            // Draw the right side of the texture
            this.context.drawGuiTexture(
                    RenderPipelines.GUI_TEXTURED,
                    dnaStrand,
                    160,
                    160,
                    scroll,
                    0,
                    n,
                    o + 31,
                    remaining,
                    textureHeight,
                    0xFFFFFFFF
            );

            // Draw the wrapped left side
            if (scroll > 0) {
                this.context.drawGuiTexture(
                        RenderPipelines.GUI_TEXTURED,
                        dnaStrand,
                        160,
                        160,
                        0,
                        0,
                        n + remaining,
                        o + 31,
                        scroll,
                        textureHeight,
                        0xFFFFFFFF
                );
            }
        }

        context.getMatrices().popMatrix();
    }
}
