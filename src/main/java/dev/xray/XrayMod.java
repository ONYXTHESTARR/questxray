package dev.xray;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class XrayMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Type /xray in chat to turn X-ray on or off
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, ctx) ->
            dispatcher.register(ClientCommands.literal("xray").executes(c -> {
                toggle(Minecraft.getInstance());
                return 1;
            })));
    }

    private static void toggle(Minecraft mc) {
        XrayState.enabled = !XrayState.enabled;
        if (mc.player != null) {
            mc.player.sendSystemMessage(
                Component.literal("X-Ray: " + (XrayState.enabled ? "ON" : "OFF")));
        }
        rebuildChunks(mc);
    }

    // Makes the world redraw so the change shows up. Tries the known method names.
    private static void rebuildChunks(Minecraft mc) {
        if (mc.levelRenderer == null) return;
        for (String name : new String[] {"allChanged", "needsUpdate"}) {
            try {
                mc.levelRenderer.getClass().getMethod(name).invoke(mc.levelRenderer);
                return;
            } catch (Exception ignored) {
            }
        }
        try {
            mc.getClass().getMethod("reloadResourcePacks").invoke(mc);
        } catch (Exception ignored) {
        }
    }
}
