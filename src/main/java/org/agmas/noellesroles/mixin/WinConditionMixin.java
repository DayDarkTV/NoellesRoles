package org.agmas.noellesroles.mixin;

import dev.doctor4t.trainmurdermystery.api.TMMRoles;
import dev.doctor4t.trainmurdermystery.cca.GameWorldComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.agmas.noellesroles.Noellesroles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Mixin(GameWorldComponent.class)
public abstract class WinConditionMixin {


    @Shadow public abstract List<UUID> getAllWithRole(TMMRoles.Role role);

    @Shadow public abstract HashMap<UUID, TMMRoles.Role> getRoles();

    @Redirect(method = "serverTick", at = @At(value = "INVOKE", target = "Ldev/doctor4t/trainmurdermystery/cca/GameWorldComponent;isInnocent(Lnet/minecraft/entity/player/PlayerEntity;)Z"))
    public boolean winCondition(GameWorldComponent instance, PlayerEntity player) {
        if (instance.getRole(player) != null) {
            if (instance.getRole(player).equals(Noellesroles.JESTER)) return false;
        }
        return instance.isInnocent(player);
    }
    @Redirect(method = "serverTick", at = @At(value = "INVOKE", target = "Ldev/doctor4t/trainmurdermystery/cca/GameWorldComponent;getAllWithRole(Ldev/doctor4t/trainmurdermystery/api/TMMRoles$Role;)Ljava/util/List;"))
    public List<UUID> winCondition(GameWorldComponent instance, TMMRoles.Role role) {
        ArrayList<UUID> killerRoles = new ArrayList<>();
        getRoles().forEach((u,r)->{
            if (!r.isInnocent()) {
                killerRoles.addAll(getAllWithRole(r));
            }
        });
        return killerRoles;
    }



}
