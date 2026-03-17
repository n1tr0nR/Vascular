package dev.rbn.vascular.api.monitor;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public abstract class MonitorDisplay {
    public final MonitorContext context;
    protected MonitorDisplay(MonitorContext context) {
        this.context = context;
    }

    public abstract void render(ItemStack stack, BlockPos pos);
}
