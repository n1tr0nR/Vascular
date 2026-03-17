package dev.rbn.vascular.api.genes.set;

import dev.rbn.vascular.Vascular;
import dev.rbn.vascular.api.VascularBloodTypes;
import dev.rbn.vascular.api.blood_types.BloodType;
import dev.rbn.vascular.api.blood_types.set.HumanBloodType;
import dev.rbn.vascular.api.blood_types.set.RotBloodType;
import dev.rbn.vascular.api.genes.Gene;
import dev.rbn.vascular.content.data.BloodBagComponent;
import dev.rbn.vascular.init.ModEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class BloodlustGene extends Gene {
    public BloodlustGene() {
        super(Vascular.id("bloodlust"), Vascular.id("dna_strand_bloodlust"));
    }

    @Override
    public void onDrinkBlood(BloodType bloodType, PlayerEntity player, World world, BloodBagComponent bag) {
        if (bloodType instanceof RotBloodType){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 160, 1));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 60, 5));
        } else if (!bloodType.equals(cannotDrink())){
            float finalHealthBoost = bag.amount() * 1.5F;
            player.heal(finalHealthBoost);
            if (world instanceof ServerWorld serverWorld){
                serverWorld.spawnParticles(
                        ParticleTypes.HEART,
                        player.getX(),
                        player.getY() + 1,
                        player.getZ(),
                        player.getRandom().nextBetween(4,6),
                        0.1F, 0.3F, 0.1F,
                        0
                );
            }
        }
    }

    @Override
    public int dangerLevel() {
        return 5;
    }

    public BloodType cannotDrink(){ //for easy overrides if people HATE ME
        return VascularBloodTypes.PYRO;
    }
}
