package dev.rbn.vascular.content.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.rbn.vascular.api.blood_types.BloodType;
import dev.rbn.vascular.api.genes.Gene;
import net.minecraft.text.Text;

public record BloodBagComponent(BloodType type, Gene gene, int amount) {
    public static final Codec<BloodBagComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BloodType.CODEC.fieldOf("type").forGetter(BloodBagComponent::type),
            Gene.CODEC.fieldOf("gene").forGetter(BloodBagComponent::gene),
            Codec.INT.fieldOf("amount").forGetter(BloodBagComponent::amount)
    ).apply(instance, BloodBagComponent::new));

    public boolean canInsert(BloodType type){
        return this.type == type && amount < 8;
    }
}
