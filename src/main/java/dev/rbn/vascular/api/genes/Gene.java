package dev.rbn.vascular.api.genes;

import com.mojang.serialization.Codec;
import dev.rbn.vascular.api.VascularGeneTypes;
import dev.rbn.vascular.api.blood_types.BloodType;
import dev.rbn.vascular.content.data.BloodBagComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public abstract class Gene {
    private final Identifier id;
    private final Identifier dnaStrandTextureLocation;

    protected Gene(Identifier id, Identifier dnaStrandTextureLocation) {
        this.id = id;
        this.dnaStrandTextureLocation = dnaStrandTextureLocation;
    }

    public Identifier getDnaStrandTextureLocation() {
        return dnaStrandTextureLocation;
    }

    public Identifier getId() {
        return id;
    }

    public String getTranslationKey(){
        return "gene." + getId().getNamespace() + "." + getId().getPath();
    }

    public abstract void onDrinkBlood(BloodType bloodType, PlayerEntity player, World world, BloodBagComponent bag);

    public static final Codec<Gene> CODEC = Identifier.CODEC.xmap(
            VascularGeneTypes::get,
            Gene::getId
    );

    public abstract int dangerLevel();
}
