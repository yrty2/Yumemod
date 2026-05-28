package com.example.yumemod.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class Silt extends Block {
    public Silt() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLUE)
                .strength(0.6f)
                .sound(SoundType.MUD));
    }
}