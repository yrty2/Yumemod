package com.example.yumemod.event;

import Packet.KeyHandlerPacket;
import Packet.NetworkHandler;
import com.example.yumemod.GameState;
import com.example.yumemod.KeyMap.ModKeyMappings;
import com.example.yumemod.item.ModItems;
import com.example.yumemod.utility;
import com.example.yumemod.yumemod;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.example.yumemod.DreamEmulator.*;

@Mod.EventBusSubscriber(modid=yumemod.MODID, value = Dist.CLIENT)
public class KeyHandler{
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event){
        if (ModKeyMappings.DREAM_KEY.consumeClick()){
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;
            NetworkHandler.INSTANCE.sendToServer(new KeyHandlerPacket());
        }
    }
}