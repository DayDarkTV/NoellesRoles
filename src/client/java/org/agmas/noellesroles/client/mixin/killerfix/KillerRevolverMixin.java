package org.agmas.noellesroles.client.mixin.killerfix;

import dev.doctor4t.trainmurdermystery.api.TMMRoles;
import dev.doctor4t.trainmurdermystery.cca.GameWorldComponent;
import dev.doctor4t.trainmurdermystery.index.TMMItems;
import dev.doctor4t.trainmurdermystery.index.tag.TMMItemTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class KillerRevolverMixin {
    @Shadow public abstract ItemStack getStack();

    @Shadow @Nullable public abstract Entity getOwner();

    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    void a(PlayerEntity player, CallbackInfo ci) {
        if (!((GameWorldComponent)GameWorldComponent.KEY.get(player.getWorld())).isInnocent(player) && getStack().isIn(TMMItemTags.GUNS)) {
            ci.cancel();
        }
    }
}
