package dev.rbn.vascular.api.monitor.set.cassette;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.monitor.MonitorContext;
import dev.rbn.vascular.api.monitor.MonitorDisplay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ShoppingCartDisplay extends MonitorDisplay {
    public ShoppingCartDisplay(MonitorContext context) {
        super(context);
    }

    @Override
    public void render(ItemStack stack, BlockPos pos) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null){
            long worldTime = client.world.getTime() % 24000;
            int maxF = 109;
            int ticksPerF = 2;
            int f = (int) ((worldTime / ticksPerF) % maxF);
            this.context.drawTexture(Vascular.id("textures/monitor/cassette/shoppingcart/shopping_cart_" + (f + 1) + ".png"), 0, 0, MonitorContext.MONITOR_SIZE.x, MonitorContext.MONITOR_SIZE.y, 0);
        }
    }
}
