package com.example.yumemod.event;

import com.example.yumemod.DreamEmulator;
import com.example.yumemod.utility;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.example.yumemod.utility.setAllUniform;

// value = Dist.CLIENT にすることで、安全にクライアント（画面側）で実行させます
@Mod.EventBusSubscriber(modid = "yumemod", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientChatEvents {

    @SubscribeEvent
    public static void onClientChat(ClientChatEvent event) {
        String message = event.getMessage();
        if (message.length() >= 8 && message.substring(0, 8).equals("uniform ")) {
            if (message.length() >= 13 && message.substring(8, 13).equals("mode ")) {
                try {
                    int mode = Integer.parseInt(message.substring(13).trim());
                    setAllUniform(mode, utility.CurveWeight);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            if (message.length() >= 15 && message.substring(8, 15).equals("weight ")){
                try {
                    float weight = Float.parseFloat(message.substring(15).trim());
                    setAllUniform(utility.CurveMode, weight);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}