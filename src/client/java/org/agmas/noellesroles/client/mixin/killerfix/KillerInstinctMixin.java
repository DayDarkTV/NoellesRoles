package org.agmas.noellesroles.client.mixin.killerfix;

import dev.doctor4t.trainmurdermystery.api.TMMRoles;
import dev.doctor4t.trainmurdermystery.cca.GameWorldComponent;
import dev.doctor4t.trainmurdermystery.client.TMMClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TMMClient.class)
public class KillerInstinctMixin {
    @Redirect(method = "getInstinctHighlight", at = @At(value = "INVOKE", target = "Ldev/doctor4t/trainmurdermystery/cca/GameWorldComponent;isRole(Lnet/minecraft/entity/player/PlayerEntity;Ldev/doctor4t/trainmurdermystery/api/TMMRoles$Role;)Z", ordinal = 0))
    private static boolean b(GameWorldComponent instance, PlayerEntity player, TMMRoles.Role role) {
        if (instance.getRole(player) == null) { return false; } else {
            return !instance.getRole(player).isInnocent();
        }
    }
}
