package dev.rbn.vascular.content.cca;

import dev.rbn.vascular.Vascular;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class ModCCA implements EntityComponentInitializer {
    public static final ComponentKey<ObliterationRayComponent> OBLITERATION_RAY = register("obliteration_ray", ObliterationRayComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(OBLITERATION_RAY, ObliterationRayComponent::new, RespawnCopyStrategy.NEVER_COPY);
    }

    public static <T extends Component> ComponentKey<T> register(String id, Class<T> componentClass) {
        return ComponentRegistry.getOrCreate(Vascular.id(id), componentClass);
    }
}
