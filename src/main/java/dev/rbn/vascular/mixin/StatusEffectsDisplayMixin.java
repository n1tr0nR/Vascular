package dev.rbn.vascular.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.rbn.vascular.content.effect.BleedingEffect;
import net.minecraft.client.gui.screen.ingame.StatusEffectsDisplay;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StatusEffectsDisplay.class)
public abstract class StatusEffectsDisplayMixin {
    @WrapOperation(method = "drawStatusEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffectUtil;getDurationText(Lnet/minecraft/entity/effect/StatusEffectInstance;FF)Lnet/minecraft/text/Text;"))
    private Text vascular$durationChange(StatusEffectInstance effect, float multiplier, float tickRate, Operation<Text> original){
        if (effect.getEffectType().value() instanceof BleedingEffect){
            return Text.translatable("effect.duration.bleeding");
        }
        return original.call(effect, multiplier, tickRate);
    }
}
