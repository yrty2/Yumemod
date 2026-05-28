package com.example.yumemod.block;

import com.example.yumemod.yumemod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, yumemod.MODID);

    public static final RegistryObject<Block> SILT=BLOCKS.register("silt_block",()->new Silt());
    public static final RegistryObject<Block> LIGHT_SILT=BLOCKS.register("light_silt_block",()->new Silt());
    public static final RegistryObject<Block> GLASS_SPONGE =
            BLOCKS.register("glass_sponge",
                    () -> new Block(BlockBehaviour.Properties.of()
                            .strength(0.3f)
                            .noOcclusion()
                            .noCollission()
                            .isSuffocating((state, level, pos) -> false)
                            .isViewBlocking((state, level, pos) -> false)
                            .isRedstoneConductor((s, l, p) -> false)
                            .sound(SoundType.SLIME_BLOCK)
                    )
            );
    public static final RegistryObject<Block> YUMEDOOR_ABYSS =
            BLOCKS.register("yumedoor_abyss",
                    () -> new Block(BlockBehaviour.Properties.of()
                            .strength(0.3f)
                            .noOcclusion()
                            .noCollission()
                            .isSuffocating((state, level, pos) -> false)
                            .isViewBlocking((state, level, pos) -> false)
                            .sound(SoundType.WOOD)
                    )
            );
}