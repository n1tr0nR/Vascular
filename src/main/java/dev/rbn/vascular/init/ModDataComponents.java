package dev.rbn.vascular.init;

import com.mojang.serialization.Codec;
import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.content.data.BloodBagComponent;
import dev.rbn.vascular.content.data.PatientCardComponent;
import dev.rbn.vascular.content.data.SyringeComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public interface ModDataComponents {
    ComponentType<SyringeComponent> DNA = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Vascular.id("dna"),
            ComponentType.<SyringeComponent>builder().codec(SyringeComponent.CODEC).build()
    );

    ComponentType<BloodBagComponent> BLOOD_BAG = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Vascular.id("blood_bag"),
            ComponentType.<BloodBagComponent>builder().codec(BloodBagComponent.CODEC).build()
    );

    ComponentType<PatientCardComponent> PATIENT_CARD = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Vascular.id("patient_card"),
            ComponentType.<PatientCardComponent>builder().codec(PatientCardComponent.CODEC).build()
    );

    ComponentType<SoundEvent> CASSETTE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Vascular.id("cassette"),
            ComponentType.<SoundEvent>builder().codec(SoundEvent.CODEC).build()
    );

    ComponentType<Boolean> EXPLODE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Vascular.id("explode"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    static void init(){}
}
