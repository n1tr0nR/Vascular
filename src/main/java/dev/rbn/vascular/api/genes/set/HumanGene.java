package dev.rbn.vascular.api.genes.set;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.blood_types.BloodType;
import dev.rbn.vascular.api.blood_types.set.RotBloodType;
import dev.rbn.vascular.api.genes.Gene;
import dev.rbn.vascular.content.data.BloodBagComponent;
import dev.rbn.vascular.init.ModEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class HumanGene extends Gene {
    public HumanGene() {
        super(Vascular.id("human"), Vascular.id("human"));
    }

    @Override
    public void OnDrinkBlood(BloodType bloodType, PlayerEntity player, World world, BloodBagComponent bag) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 160, 1));
        if (bloodType instanceof RotBloodType){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 160, 10));
            player.addStatusEffect(new StatusEffectInstance(ModEffects.BLEEDING, Integer.MAX_VALUE, 10));
        }
    }
}
