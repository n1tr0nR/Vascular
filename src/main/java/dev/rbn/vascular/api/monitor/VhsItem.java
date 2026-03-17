package dev.rbn.vascular.api.monitor;

import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;

public interface VhsItem {
    MonitorDisplay getDisplay();
    boolean loop();
    @Nullable SoundEvent sound(ItemStack stack);
    default String soundDisplay(ItemStack stack){
        return sound(stack) != null ? "sound." + sound(stack).id().getNamespace() + "." + sound(stack).id().getPath() + ".description" : "";
    }
}
