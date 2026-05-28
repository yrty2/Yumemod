package com.example.yumemod.event;

import com.example.yumemod.KeyMap.ModKeyMappings;
import com.example.yumemod.entity.Jellyfish;
import com.example.yumemod.entity.JellyfishEntity;
import com.example.yumemod.entity.JellyfishRenderer;
import com.example.yumemod.entity.ModEntities;
import com.example.yumemod.yumemod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = yumemod.MODID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.JELLYFISH.get(), JellyfishRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(Jellyfish.LAYER_LOCATION, Jellyfish::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(net.minecraftforge.client.event.RegisterKeyMappingsEvent event) {
        event.register(ModKeyMappings.DREAM_KEY);
    }
}