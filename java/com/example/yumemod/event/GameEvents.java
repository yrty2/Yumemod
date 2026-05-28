package com.example.yumemod.event;

import com.example.yumemod.entity.JellyfishEntity;
import com.example.yumemod.yumemod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.example.yumemod.DreamEmulator.grantAdv;
import static com.example.yumemod.DreamEmulator.jellyfishEventMap;

@Mod.EventBusSubscriber(modid = yumemod.MODID)
public class GameEvents{
    @SubscribeEvent
    public static void onJellyfishDeath(LivingDeathEvent event){

        if (!(event.getEntity() instanceof JellyfishEntity jelly)) return;

        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;
        grantAdv(player,"jellyfish");
        player.addEffect(new MobEffectInstance(
                MobEffects.LEVITATION,
                15 * 20,
                0,
                false,
                true
        ));
        jellyfishEventMap.put(player.getUUID(),true);
    }
}