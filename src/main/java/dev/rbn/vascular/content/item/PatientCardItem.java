package dev.rbn.vascular.content.item;

import dev.rbn.vascular.api.monitor.MonitorContext;
import dev.rbn.vascular.api.monitor.MonitorDisplay;
import dev.rbn.vascular.api.monitor.VhsItem;
import dev.rbn.vascular.api.monitor.set.MonitorBloodTypeDisplay;
import dev.rbn.vascular.api.monitor.set.MonitorStaticDisplay;
import dev.rbn.vascular.content.data.PatientCardComponent;
import dev.rbn.vascular.init.ModDataComponents;
import dev.rbn.vascular.init.ModSounds;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

import java.text.Normalizer;
import java.util.List;

public class PatientCardItem extends Item implements VhsItem {
    public PatientCardItem(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, List<Text> list) {
        PatientCardComponent component = stack.get(ModDataComponents.PATIENT_CARD);
        if (component != null){
            list.add(Text.translatable("item.syringe.tooltip.blood").formatted(Formatting.DARK_GRAY).append(": ").append(
                    Text.translatable(component.bloodType().getTranslationKey()).withColor(component.bloodType().getBloodColor())
            ));
            list.add(Text.translatable("item.syringe.tooltip.gene").formatted(Formatting.DARK_GRAY).append(": ").append(
                    Text.translatable(component.gene().getTranslationKey()).formatted(Formatting.GRAY)
            ));
            list.add(Text.empty());
            list.add(Text.empty());
            list.add(Text.translatable("item.syringe.tooltip.target").formatted(Formatting.DARK_GRAY).append(": ").append(component.name()
                    .copy().formatted(Formatting.GRAY)));
        }
    }

    @Override
    public MonitorDisplay getDisplay() {
        return new MonitorBloodTypeDisplay(new MonitorContext());
    }

    @Override
    public boolean loop() {
        return true;
    }

    @Override
    public @Nullable SoundEvent sound(ItemStack stack) {
        return ModSounds.HUM.value();
    }
}
