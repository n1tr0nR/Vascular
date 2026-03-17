package dev.rbn.vascular.content.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.rbn.vascular.api.blood_types.BloodType;
import dev.rbn.vascular.api.genes.Gene;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;

public record PatientCardComponent(Gene gene, BloodType bloodType, Text name) {
    public static final Codec<PatientCardComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Gene.CODEC.fieldOf("gene").forGetter(PatientCardComponent::gene),
            BloodType.CODEC.fieldOf("type").forGetter(PatientCardComponent::bloodType),
            TextCodecs.CODEC.fieldOf("name").forGetter(PatientCardComponent::name)
    ).apply(instance, PatientCardComponent::new));
}
