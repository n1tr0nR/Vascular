package dev.rbn.vascular.mixin;

import dev.rbn.vascular.api.BloodTypeEntityRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends PlayerLikeEntity implements ContainerUser {
    @Shadow
    public abstract @Nullable ItemEntity dropItem(ItemStack stack, boolean retainOwnership);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "handleFallDamage", at = @At("RETURN"))
    private void vascular$dropBlood(double fallDistance, float damagePerDistance, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        boolean tookDamage = cir.getReturnValue();
        if (tookDamage && this.getRandom().nextInt(5) == 0) {
            this.dropItem(BloodTypeEntityRegistry.getPlayerBloodType(this.getUuid()).bloodItem().getDefaultStack(), true);
        }
    }
}
