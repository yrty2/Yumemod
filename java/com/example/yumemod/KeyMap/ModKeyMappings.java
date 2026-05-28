package com.example.yumemod.KeyMap;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class ModKeyMappings {
    public static final KeyMapping DREAM_KEY = new KeyMapping(
            "key.yumemod.dream_key",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "key.categories.yumemod"
    );
}