package dev.rbn.vascular.content.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.rbn.vascular.api.blood_types.BloodType;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;

public record SyringeComponent(String uuid, Text playerName, BloodType type) {
    public static final Codec<SyringeComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("uuid").forGetter(SyringeComponent::uuid),
            TextCodecs.CODEC.fieldOf("playerName").forGetter(SyringeComponent::playerName),
            BloodType.CODEC.fieldOf("bloodType").forGetter(SyringeComponent::type)
    ).apply(instance, SyringeComponent::new));
}
