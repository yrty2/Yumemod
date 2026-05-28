package com.example.yumemod;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//saveSystem
public class GameState {
    public BlockPos position;
    public ResourceKey<Level> dimension;
    public ItemStack[] inventory;
    public GameState(BlockPos a,ResourceKey<Level> b,ItemStack[] c){
        position=a;
        dimension=b;
        inventory=c;
    }
}