package dev.rbn.vascular.api.monitor.set;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.monitor.MonitorContext;
import dev.rbn.vascular.api.monitor.MonitorDisplay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class MonitorStaticDisplay extends MonitorDisplay {
    public MonitorStaticDisplay(MonitorContext context) {
        super(context);
    }

    @Override
    public void render(ItemStack stack, BlockPos pos) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null){
            long worldTime = client.world.getTime() % 24000;
            int maxF = 4;
            int ticksPerF = 1;
            int f = (int) ((worldTime / ticksPerF) % maxF);
            this.context.drawTexture(Vascular.id("textures/monitor/static" + (f + 1) + ".png"), 0, 0, MonitorContext.MONITOR_SIZE.x, MonitorContext.MONITOR_SIZE.y, 0);
        }
    }
}
