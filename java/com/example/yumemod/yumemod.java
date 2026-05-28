package com.example.yumemod;

import com.example.yumemod.DreamMaps.Features.ModFeatures;
import com.example.yumemod.KeyMap.ModKeyMappings;
import com.example.yumemod.block.ModBlocks;
import com.example.yumemod.entity.ModEntities;
import com.example.yumemod.item.ModItems;
import com.example.yumemod.shaders.ModShaders;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.IEventBus;
import org.slf4j.Logger;
import math.algebra.*;

@Mod(yumemod.MODID)
public class yumemod{
    public static final String MODID="yumemod";
    private static final Logger LOGGER=LogUtils.getLogger();
    public complex a=new complex(1,2);
    public yumemod(){
        IEventBus bus=FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);
        ModFeatures.FEATURES.register(bus);
        ModEntities.ENTITIES.register(bus);
        Packet.NetworkHandler.register();
    }
}