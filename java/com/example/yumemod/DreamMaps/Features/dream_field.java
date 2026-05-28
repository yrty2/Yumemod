package com.example.yumemod.DreamMaps.Features;

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

public class dream_field extends Feature<NoneFeatureConfiguration> {
    public dream_field(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();

        for (int dx = -6; dx <= 6; dx++) {
            for (int dz = -6; dz <= 6; dz++) {
                BlockPos top = level.getHeightmapPos(
                        Heightmap.Types.WORLD_SURFACE,
                        origin.offset(dx, 0, dz)
                );

                BlockPos ground = top.below();

                if (level.getBlockState(ground).is(Blocks.GRASS_BLOCK)) {
                    level.setBlock(
                            ground,
                            Blocks.FARMLAND.defaultBlockState()
                                    .setValue(FarmBlock.MOISTURE, 7),
                            2
                    );

                    level.setBlock(
                            top,
                            Blocks.WHEAT.defaultBlockState()
                                    .setValue(CropBlock.AGE, 7),
                            2
                    );
                }
            }
        }

        return true;
    }
}