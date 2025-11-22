package org.agmas.noellesroles.client.mixin;

import dev.doctor4t.trainmurdermystery.api.TMMRoles;
import dev.doctor4t.trainmurdermystery.cca.GameWorldComponent;
import dev.doctor4t.trainmurdermystery.client.TMMClient;
import dev.doctor4t.trainmurdermystery.client.gui.RoundTextRenderer;
import dev.doctor4t.trainmurdermystery.game.GameFunctions;
import dev.doctor4t.trainmurdermystery.util.AnnounceWelcomePayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.agmas.noellesroles.Noellesroles;
import org.agmas.noellesroles.bartender.BartenderPlayerComponent;
import org.agmas.noellesroles.client.NoellesrolesClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(TMMClient.class)
public abstract class InstinctMixin {


    @Shadow public static KeyBinding instinctKeybind;

    @Inject(method = "isInstinctEnabled", at = @At("HEAD"), cancellable = true)
    private static void b(CallbackInfoReturnable<Boolean> cir) {
        GameWorldComponent gameWorldComponent = (GameWorldComponent) GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
        if (gameWorldComponent.isRole(MinecraftClient.getInstance().player, Noellesroles.JESTER)) {
            if (instinctKeybind.isPressed()) {
                cir.setReturnValue(true);
                cir.cancel();
            }
        }
    }

    @Inject(method = "getInstinctHighlight", at = @At("HEAD"), cancellable = true)
    private static void b(Entity target, CallbackInfoReturnable<Integer> cir) {
        GameWorldComponent gameWorldComponent = (GameWorldComponent) GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
        if (target instanceof PlayerEntity) {
            if (!((PlayerEntity)target).isSpectator()) {
                BartenderPlayerComponent bartenderPlayerComponent = (BartenderPlayerComponent) BartenderPlayerComponent.KEY.get((PlayerEntity) target);
                if (gameWorldComponent.isRole(MinecraftClient.getInstance().player, Noellesroles.BARTENDER) && bartenderPlayerComponent.glowTicks > 0) {
                    cir.setReturnValue(Color.ORANGE.getRGB());
                }
            }
        }
        if (target instanceof PlayerEntity && TMMClient.isInstinctEnabled()) {
            if (!((PlayerEntity)target).isSpectator()) {
                if (gameWorldComponent.isRole(MinecraftClient.getInstance().player, Noellesroles.JESTER) && TMMClient.isPlayerAliveAndInSurvival()) {
                    cir.setReturnValue(Color.PINK.getRGB());
                }
                if (GameFunctions.isPlayerSpectatingOrCreative(MinecraftClient.getInstance().player)) {
                    TMMRoles.Role role = gameWorldComponent.getRole((PlayerEntity) target);
                    if (role == null) {
                        cir.setReturnValue(TMMRoles.CIVILIAN.color());
                    } else {
                        cir.setReturnValue(role.color());
                    }
                }
            }
        }
    }
}
