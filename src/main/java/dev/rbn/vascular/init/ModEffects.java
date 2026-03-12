package dev.rbn.vascular.init;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.content.effect.BleedingEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public interface ModEffects {
    RegistryEntry<StatusEffect> BLEEDING = register("bleeding", new BleedingEffect());

    static RegistryEntry<StatusEffect> register(String name, StatusEffect effect){
        return Registry.registerReference(Registries.STATUS_EFFECT, Vascular.id(name), effect);
    }

    static void init(){}
}
