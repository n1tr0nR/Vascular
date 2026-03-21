package dev.rbn.vascular.api.monitor.set;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.init.VascularGeneTypes;
import dev.rbn.vascular.api.blood_types.set.HumanBloodType;
import dev.rbn.vascular.api.monitor.MonitorContext;
import dev.rbn.vascular.api.monitor.MonitorDisplay;
import dev.rbn.vascular.content.data.PatientCardComponent;
import dev.rbn.vascular.init.ModDataComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class MonitorBloodTypeDisplay extends MonitorDisplay {
    public MonitorBloodTypeDisplay(MonitorContext context) {
        super(context);
    }

    @Override
    public void render(ItemStack stack, BlockPos pos) {
        PatientCardComponent component = stack.get(ModDataComponents.PATIENT_CARD);
        if (component != null){
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.world != null){
                long worldTime = client.world.getTime() % 24000;
                int maxF = 4;
                int ticksPerF = 1;
                int f = (int) ((worldTime / ticksPerF) % maxF);
                this.context.drawTexture(Vascular.id("textures/monitor/static_low" + (f + 1) + ".png"), 0, 0, MonitorContext.MONITOR_SIZE.x, MonitorContext.MONITOR_SIZE.y, 0);
            }

            this.context.fill(1, 1, 41, 41, 0xFF707070, 1);
            this.context.fill(2, 2, 40, 40, 0xFF000000, 2);

            this.context.drawText(
                    Text.translatable(component.gene().getTranslationKey()),
                    5, 80,
                    0xFFFFFFFF,
                    3
            );

            this.context.drawTexture(
                    Vascular.id("textures/item/patient_card_bound.png"),
                    21 - 8, 21 - 8, 16, 16, 3
            );

            Identifier dnaLocation = component.gene().getDnaStrandTextureLocation();
            Identifier better = Identifier.of(dnaLocation.getNamespace(), "textures/gui/sprites/" + dnaLocation.getPath() + ".png");

            this.context.drawTexture(
                    better,
                    58, 75, 160, 160, 3
            );

            this.context.drawText(
                    Text.translatable("item.syringe.tooltip.target").formatted(Formatting.DARK_GRAY)
                            .append(": ").append(component.name().copy().formatted(Formatting.GRAY).formatted(component.gene().equals(VascularGeneTypes.BLOODLUST) ? Formatting.OBFUSCATED : Formatting.GRAY)),
                    5,
                    45,
                    0xFFFFFFFF,
                    3
            );

            this.context.drawText(
                    Text.translatable("monitor.display.danger").formatted(Formatting.DARK_GRAY)
                            .append(": "),
                    45,
                    5,
                    0xFFFFFFFF,
                    3
            );

            Text stars = Text.literal("");
            if (component.gene().dangerLevel() == 1){
                stars = Text.literal("★").formatted(Formatting.WHITE).append(Text.literal("☆☆☆☆").formatted(Formatting.DARK_GRAY));
            }
            if (component.gene().dangerLevel() == 2){
                stars = Text.literal("★★").formatted(Formatting.WHITE).append(Text.literal("☆☆☆").formatted(Formatting.DARK_GRAY));
            }
            if (component.gene().dangerLevel() == 3){
                stars = Text.literal("★★★").formatted(Formatting.YELLOW).append(Text.literal("☆☆").formatted(Formatting.DARK_GRAY));
            }
            if (component.gene().dangerLevel() == 4){
                stars = Text.literal("★★★★").formatted(Formatting.GOLD).append(Text.literal("☆").formatted(Formatting.DARK_GRAY));
            }
            if (component.gene().dangerLevel() == 5){
                stars = Text.literal("★★★★★").formatted(Formatting.RED);

                this.context.drawText(
                        Text.literal("Exercise caution.").formatted(Formatting.GRAY),
                        45,
                        15,
                        0xFFFFFFFF,
                        3
                );
                this.context.drawText(
                        Text.literal("Public Danger.").formatted(Formatting.RED),
                        45,
                        25,
                        0xFFFFFFFF,
                        3
                );
            }
            if (stars.getString().isEmpty()){
                stars = Text.literal("X").formatted(Formatting.RED);
            }

            this.context.drawText(
                    stars,
                    70,
                    5,
                    0xFFFFFFFF,
                    3
            );

            if (component.bloodType() instanceof HumanBloodType humanBloodType){
                this.context.drawText(
                        Text.translatable("item.syringe.tooltip.blood").formatted(Formatting.DARK_GRAY)
                                .append(": ").append(
                                    Text.translatable(humanBloodType.getBloodTypeTranslationKey(component.name().getString()))
                                            .formatted(Formatting.GRAY)
                                ),
                        5,
                        55,
                        0xFFFFFFFF,
                        3
                );
            } else {
                this.context.drawText(
                        Text.translatable("item.syringe.tooltip.blood").formatted(Formatting.DARK_GRAY)
                                .append(": ").append(
                                        Text.translatable(component.bloodType().getTranslationKey())
                                                .formatted(Formatting.GRAY)
                                ),
                        5,
                        55,
                        0xFFFFFFFF,
                        3
                );
            }
        } else {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.world != null){
                long worldTime = client.world.getTime() % 24000;
                int maxF = 4;
                int ticksPerF = 1;
                int f = (int) ((worldTime / ticksPerF) % maxF);
                this.context.drawTexture(Vascular.id("textures/monitor/static_low" + (f + 1) + ".png"), 0, 0, MonitorContext.MONITOR_SIZE.x, MonitorContext.MONITOR_SIZE.y, 0);
            }

            this.context.drawText(
                    Text.literal("[ Empty Card ]"),
                    MonitorContext.MONITOR_SIZE.x / 2 - MinecraftClient.getInstance().textRenderer.getWidth(Text.literal("[ Empty Card ]")) / 2,
                    40,
                    0xFFFFFFFF,
                    1
            );
            this.context.drawText(
                    Text.literal("Card requires a patient."),
                    MonitorContext.MONITOR_SIZE.x / 2 - MinecraftClient.getInstance().textRenderer.getWidth(Text.literal("Card requires a patient.")) / 2,
                    55,
                    0xFF606060,
                    1
            );
        }
    }
}
