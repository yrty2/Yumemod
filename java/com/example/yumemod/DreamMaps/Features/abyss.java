package com.example.yumemod.DreamMaps.Features;

import com.example.yumemod.block.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class abyss extends Feature<NoneFeatureConfiguration> {
    public abyss(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        Random rand=new Random();

        if(rand.nextDouble()<0.1){
            for (int dx = -6; dx <= 6; dx++) {
                for (int dz = -6; dz <= 6; dz++) {
                    BlockPos top = level.getHeightmapPos(
                            Heightmap.Types.WORLD_SURFACE,
                            origin.offset(dx, 0, dz)
                    );

                    BlockPos ground = top.below();

                    if (level.getBlockState(ground).is(ModBlocks.SILT.get()) && rand.nextDouble()<0.05){
                        level.setBlock(
                                top,
                                ModBlocks.GLASS_SPONGE.get().defaultBlockState(),
                                2
                        );
                    }
                }
            }
        }
        return true;
    }
}