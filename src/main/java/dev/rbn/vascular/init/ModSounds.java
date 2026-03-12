package dev.rbn.vascular.init;

import dev.rbn.vascular.Vascular;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;


public interface ModSounds {
    RegistryEntry<SoundEvent> BAG_CREATE = register("item.bag_create");
    RegistryEntry<SoundEvent> SYRINGE_USE = register("item.syringe_use");
    RegistryEntry<SoundEvent> BLOOD_DRINK = register("item.blood_drink");

    static void init() {}

    static RegistryEntry<SoundEvent> register(String name) {
        Identifier id = Vascular.id(name);
        return Registry.registerReference(
                Registries.SOUND_EVENT,
                id,
                SoundEvent.of(id)
        );
    }
}
