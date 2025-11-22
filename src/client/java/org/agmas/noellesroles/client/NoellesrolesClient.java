package org.agmas.noellesroles.client;

import dev.doctor4t.trainmurdermystery.api.TMMRoles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import org.agmas.noellesroles.Noellesroles;
import org.agmas.noellesroles.packet.AbilityC2SPacket;
import org.lwjgl.glfw.GLFW;

public class NoellesrolesClient implements ClientModInitializer {

    private static KeyBinding abilityBind;
    public static TMMRoles.Role hudRole = null;

    @Override
    public void onInitializeClient() {
        abilityBind = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + Noellesroles.MOD_ID + ".ability", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.trainmurdermystery.keybinds"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (abilityBind.wasPressed()) {
                PacketByteBuf data = PacketByteBufs.create();
                client.execute(() -> {
                    ClientPlayNetworking.send(new AbilityC2SPacket());
                });
            }
        });
    }
}
