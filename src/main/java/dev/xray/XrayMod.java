package dev.xray;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class XrayMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Type /xray in chat to turn X-ray on or off
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, ctx) ->
            dispatcher.register(ClientCommandManager.literal("xray").executes(c -> {
                toggle(Minecraft.getInstance());
                return 1;
            })));
    }

    private static void toggle(Minecraft mc) {
        XrayState.enabled = !XrayState.enabled;
        if (mc.player != null) {
            mc.player.displayClientMessage(
                Component.literal("X-Ray: " + (XrayState.enabled ? "ON" : "OFF")), true);
        }
        if (mc.levelRenderer != null) mc.levelRenderer.allChanged();
    }
}
