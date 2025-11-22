package org.agmas.noellesroles.client.mixin.swapper;

import dev.doctor4t.trainmurdermystery.cca.GameWorldComponent;
import dev.doctor4t.trainmurdermystery.client.gui.screen.ingame.LimitedHandledScreen;
import dev.doctor4t.trainmurdermystery.client.gui.screen.ingame.LimitedInventoryScreen;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import org.agmas.noellesroles.Noellesroles;
import org.agmas.noellesroles.client.MorphlingPlayerWidget;
import org.agmas.noellesroles.client.SwapperPlayerWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;


@Mixin(LimitedInventoryScreen.class)
public abstract class SwapperScreenMixin extends LimitedHandledScreen<PlayerScreenHandler>{
    @Shadow @Final public ClientPlayerEntity player;

    public SwapperScreenMixin(PlayerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }


    @Inject(method = "init", at = @At("TAIL"))
    void b(CallbackInfo ci) {
        GameWorldComponent gameWorldComponent = (GameWorldComponent) GameWorldComponent.KEY.get(player.getWorld());
        /*if (gameWorldComponent.isRole(player,Noellesroles.SWAPPER)) {
            Log.info(LogCategory.GENERAL, "Render Swapper");
            List<AbstractClientPlayerEntity> entries = MinecraftClient.getInstance().world.getPlayers();
            entries.removeIf((e) -> e.getUuid().equals(player.getUuid()));
            int apart = 36;
            int x = ((LimitedInventoryScreen)(Object)this).width / 2 - (entries.size()) * apart / 2 + 9;
            int shouldBeY = (((LimitedInventoryScreen)(Object)this).height - 32) / 2;
            int y = shouldBeY + 80;

            for(int i = 0; i < entries.size(); ++i) {
                SwapperPlayerWidget child = new SwapperPlayerWidget(((LimitedInventoryScreen)(Object)this), x + apart * i, y, entries.get(i), i);
                addDrawableChild(child);
            }
        }*/
    }

}
