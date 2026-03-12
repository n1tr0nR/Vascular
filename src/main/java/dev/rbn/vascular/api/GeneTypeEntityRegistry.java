package dev.rbn.vascular.api;

import dev.rbn.vascular.api.genes.Gene;
import net.minecraft.entity.EntityType;

import java.util.HashMap;
import java.util.UUID;

public class GeneTypeEntityRegistry {
    public static final HashMap<EntityType<?>, Gene> LINKED_ENTITIES = new HashMap<>();
    public static final HashMap<UUID, Gene> LINKED_PLAYERS = new HashMap<>();

    public static void addEntityToGene(EntityType<?> entityType, Gene type){
        LINKED_ENTITIES.put(entityType, type);
    }

    public static Gene getEntityGene(EntityType<?> entityType){
        return LINKED_ENTITIES.getOrDefault(entityType, VascularGeneTypes.HUMAN);
    }

    public static void addPlayerToGene(UUID playerUUID, Gene type){
        LINKED_PLAYERS.put(playerUUID, type);
    }

    public static Gene getPlayerGene(UUID playerUUID){
        return LINKED_PLAYERS.getOrDefault(playerUUID, VascularGeneTypes.HUMAN);
    }
}
