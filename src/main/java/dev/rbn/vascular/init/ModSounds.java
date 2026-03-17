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

    RegistryEntry<SoundEvent> STATIC = register("block.static");
    RegistryEntry<SoundEvent> HUM = register("block.hum");
    RegistryEntry<SoundEvent> VHS_ADD = register("block.vhs_add");
    RegistryEntry<SoundEvent> VHS_REMOVE = register("block.vhs_remove");
    RegistryEntry<SoundEvent> MONITOR_POWER_OFF = register("block.monitor_power_off");

    RegistryEntry<SoundEvent> EVENT_HORIZON = register("music.event_horizon");
    RegistryEntry<SoundEvent> THE_WORLD_LOOKS_RED = register("music.the_world_looks_red");

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
