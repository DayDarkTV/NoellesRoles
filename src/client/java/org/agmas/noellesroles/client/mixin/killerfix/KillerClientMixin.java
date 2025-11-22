package org.agmas.noellesroles.client.mixin.killerfix;

import dev.doctor4t.trainmurdermystery.cca.GameWorldComponent;
import dev.doctor4t.trainmurdermystery.client.TMMClient;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TMMClient.class)
public class KillerClientMixin {
    @Shadow public static GameWorldComponent gameComponent;

    @Inject(method = "isKiller", at = @At(value = "HEAD"), cancellable = true)
    private static void a(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(gameComponent != null && gameComponent.getRole(MinecraftClient.getInstance().player) != null && !gameComponent.getRole(MinecraftClient.getInstance().player).isInnocent());
        cir.cancel();
    }
}
