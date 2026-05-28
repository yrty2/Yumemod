package com.example.yumemod.music;

import com.example.yumemod.DreamEmulator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class DreamMusics extends AbstractTickableSoundInstance {

    private final Minecraft mc;

    public DreamMusics(Minecraft mc) {
        super(SoundEvents.MUSIC_GAME.value(), SoundSource.MUSIC, SoundInstance.createUnseededRandom());
        this.mc = mc;

        this.looping = true;
        this.delay = 0;
        this.volume = 1.0f;
        this.pitch = 0.6f;
    }

    @Override
    public void tick() {
        if (mc.player == null) {
            stop();
            return;
        }

        // 夢状態じゃなくなったら停止
        if (!DreamEmulator.dreamingMap.getOrDefault(mc.player.getUUID(), false)){
            stop();
        }
    }
}