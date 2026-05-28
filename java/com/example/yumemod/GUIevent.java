package com.example.yumemod;

import com.example.yumemod.DreamMaps.Dreams;
import com.example.yumemod.block.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.example.yumemod.DreamEmulator.dreamingMap;

@Mod.EventBusSubscriber(modid = "yumemod", value = Dist.CLIENT)
public class GUIevent{
    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) return;
        GuiGraphics gui = event.getGuiGraphics();
        int w = event.getWindow().getGuiScaledWidth();
        int h = event.getWindow().getGuiScaledHeight();
        if(mc.player.level().dimension()==Dreams.ABYSS){
            gui.fill(0, 0, w, h, 0x05074DD8);
        }else if(mc.player.level().dimension()==Dreams.DREAM_NETHER){
            gui.fill(0, 0, w, h, 0x01FFFFFF);
        }else if(mc.player.level().dimension()==Dreams.SEWERS){
            gui.fill(0, 0, w, h, 0x01DDDDDD);
        }else if(dreamingMap.getOrDefault(mc.player.getUUID(),false) && mc.player.level().dimension()== Level.OVERWORLD){
            gui.fill(0, 0, w, h, 0xFF000000);
        }else{
            return;
        }
    }
}