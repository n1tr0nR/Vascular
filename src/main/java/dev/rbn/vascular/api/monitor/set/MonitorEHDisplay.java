package dev.rbn.vascular.api.monitor.set;

import dev.rbn.vascular.api.monitor.CassetteItem;
import dev.rbn.vascular.api.monitor.MonitorContext;
import dev.rbn.vascular.api.monitor.MonitorDisplay;
import dev.rbn.vascular.api.monitor.VhsItem;
import dev.rbn.vascular.init.ModDataComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

public class MonitorEHDisplay extends MonitorDisplay {
    public MonitorEHDisplay(MonitorContext context) {
        super(context);
    }

    @Override
    public void render(ItemStack stack, BlockPos pos) { //https://voicechanger.io/voicemaker/#!/{%22effects%22:[{%22name%22:%22telephone%22,%22params%22:{%22magnitude%22:0.96,%22lowPassFreq%22:6133,%22highPassFreq%22:500}}],%22version%22:1} <- funky cool filter
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null && stack.getItem() instanceof CassetteItem cassetteItem && stack.getItem() instanceof VhsItem vhsItem) {
            this.context.drawTexture(cassetteItem.getTexture(client.world, stack), 0, 0, MonitorContext.MONITOR_SIZE.x, MonitorContext.MONITOR_SIZE.y, 0);

            this.context.drawText(
                    Text.literal("Now Playing:").formatted(Formatting.DARK_GRAY),
                    5, 5, 0xFFFFFFFF, 1
            );

            this.context.drawText(
                    Text.translatable(vhsItem.soundDisplay(stack) + ".1").formatted(Formatting.GRAY),
                    5, 20, 0xFFFFFFFF, 1
            );

            this.context.drawText(
                    Text.translatable(vhsItem.soundDisplay(stack) + ".2").formatted(Formatting.GOLD),
                    5, 30, 0xFFFFFFFF, 1
            );
        }
    }
}
