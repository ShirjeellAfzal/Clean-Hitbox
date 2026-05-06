package com.example.shirjeelplays.cleanhitbox;

import com.example.shirjeelplays.cleanhitbox.compat.ModMenuIntegration;
import com.example.shirjeelplays.cleanhitbox.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CleanHitbox implements ClientModInitializer {
    public static final String MOD_ID = "clean-hitbox";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static KeyBinding configKeyBinding;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Clean Hitbox...");
        ModConfig.load();

        configKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.clean-hitbox.open_config",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "CLEANHITBOX"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (configKeyBinding.wasPressed()) {
                client.setScreen(ModMenuIntegration.createConfigScreen(client.currentScreen));
            }
        });

        LOGGER.info("Clean Hitbox initialized!");
    }
}
