package dev.rbn.vascular.mixin;

import dev.rbn.vascular.api.GeneTypeEntityRegistry;
import dev.rbn.vascular.api.VascularGeneTypes;
import dev.rbn.vascular.client.VascularClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Shadow
    @Final
    public GameOptions options;

    @Inject(method = "handleInputEvents", at = @At("TAIL"))
    private void vascular$takeBlood(CallbackInfo ci){
        if (this.player != null) {
            if (this.player.isSneaking() && this.options.useKey.isPressed() && this.player.getActiveOrMainHandStack().isEmpty()
            && GeneTypeEntityRegistry.getPlayerGene(this.player.getUuid()).equals(VascularGeneTypes.BLOODLUST)){
                VascularClient.bloodTaking.startTakingBlood();
            } else {
                VascularClient.bloodTaking.stopTakingBlood();
            }
        }
    }
}
