package dev.rbn.vascular.init;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.content.entity.BloodWeaponEntity;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public interface ModEntities {
    EntityType<BloodWeaponEntity> BLOOD_WEAPON = register("blood_weapon", EntityType.Builder.<BloodWeaponEntity>create(BloodWeaponEntity::new, SpawnGroup.MISC)
            .dimensions(0.5F, 0.5F));

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Vascular.id(id));
        return Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
    }

    static void init(){}
}
