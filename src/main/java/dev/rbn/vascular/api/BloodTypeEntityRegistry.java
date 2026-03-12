package dev.rbn.vascular.api;

import dev.rbn.vascular.api.blood_types.BloodType;
import net.minecraft.entity.EntityType;

import java.util.HashMap;
import java.util.UUID;

public class BloodTypeEntityRegistry {
    public static final HashMap<EntityType<?>, BloodType> LINKED_ENTITIES = new HashMap<>();
    public static final HashMap<UUID, BloodType> LINKED_PLAYERS = new HashMap<>();

    public static void addEntityToBloodType(EntityType<?> entityType, BloodType type){
        LINKED_ENTITIES.put(entityType, type);
    }

    public static BloodType getEntityBloodType(EntityType<?> entityType){
        return LINKED_ENTITIES.getOrDefault(entityType, VascularBloodTypes.HUMAN);
    }

    public static void addPlayerToBloodType(UUID playerUUID, BloodType type){
        LINKED_PLAYERS.put(playerUUID, type);
    }

    public static BloodType getPlayerBloodType(UUID playerUUID){
        return LINKED_PLAYERS.getOrDefault(playerUUID, VascularBloodTypes.HUMAN);
    }
}
