package com.example.yumemod.music;

import com.example.yumemod.yumemod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = yumemod.MODID,
        bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = Dist.CLIENT)
public class DreamMusicManager{

    private static DreamMusics instance;

    public static void start(){
        Minecraft mc = Minecraft.getInstance();
        if (instance == null || instance.isStopped()) {
            instance = new DreamMusics(mc);
            mc.getSoundManager().play(instance);
        }
    }
}