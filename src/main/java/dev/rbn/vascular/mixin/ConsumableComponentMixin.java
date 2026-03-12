package dev.rbn.vascular.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.rbn.vascular.content.data.BloodBagComponent;
import dev.rbn.vascular.init.ModDataComponents;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ConsumableComponent.class)
public abstract class ConsumableComponentMixin {
    @WrapOperation(method = "consume", at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/ConsumableComponent;getConsumeTicks()I"))
    private int vascular$modifyDrinkTime(ConsumableComponent instance, Operation<Integer> original, LivingEntity user, ItemStack stack, Hand hand){
        BloodBagComponent component = stack.get(ModDataComponents.BLOOD_BAG);
        if (component != null){
            return component.amount() * 8;
        }
        return original.call(instance);
    }
}
