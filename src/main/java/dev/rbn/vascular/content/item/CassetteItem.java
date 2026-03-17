package dev.rbn.vascular.content.item;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.monitor.MonitorContext;
import dev.rbn.vascular.api.monitor.MonitorDisplay;
import dev.rbn.vascular.api.monitor.VhsItem;
import dev.rbn.vascular.api.monitor.set.MonitorBloodTypeDisplay;
import dev.rbn.vascular.api.monitor.set.MonitorEHDisplay;
import dev.rbn.vascular.content.data.PatientCardComponent;
import dev.rbn.vascular.init.ModDataComponents;
import dev.rbn.vascular.init.ModSounds;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CassetteItem extends Item implements VhsItem, dev.rbn.vascular.api.monitor.CassetteItem {
    public CassetteItem(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, List<Text> list) {
        list.add(Text.literal("- ").append(Text.translatable(this.soundDisplay(stack)).formatted(Formatting.GOLD)).formatted(Formatting.DARK_GRAY));
    }

    @Override
    public MonitorDisplay getDisplay() {
        return new MonitorEHDisplay(new MonitorContext());
    }

    @Override
    public boolean loop() {
        return true;
    }

    @Override
    public @Nullable SoundEvent sound(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.CASSETTE, null);
    }

    @Override
    public Identifier getTexture(World world, ItemStack stack) {
        SoundEvent event = stack.getOrDefault(ModDataComponents.CASSETTE, null);
        if (event != null){
            if (event.equals(ModSounds.EVENT_HORIZON.value())){
                long worldTime = world.getTime() % 24000;
                int maxF = 4;
                int ticksPerF = 1;
                int f = (int) ((worldTime / ticksPerF) % maxF);
                return Vascular.id("textures/monitor/event_horizon/event_horizon_" + (f + 1) + ".png");
            }
            if (event.equals(ModSounds.THE_WORLD_LOOKS_RED.value())){
                long worldTime = world.getTime() % 24000;
                int maxF = 4;
                int ticksPerF = 1;
                int f = (int) ((worldTime / ticksPerF) % maxF);
                return Vascular.id("textures/monitor/twlr/twlr_" + (f + 1) + ".png");
            }
        }
        long worldTime = world.getTime() % 24000;
        int maxF = 4;
        int ticksPerF = 1;
        int f = (int) ((worldTime / ticksPerF) % maxF);
        return Vascular.id("textures/monitor/static_low" + (f + 1) + ".png");
    }
}
