package com.example.yumemod.DreamMaps.Features;

import com.example.yumemod.yumemod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, yumemod.MODID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> WHEAT_FIELD =
            FEATURES.register("dream_field_feature",
                    () -> new dream_field(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ABYSS =
            FEATURES.register("abyss_feature",
                    () -> new abyss(NoneFeatureConfiguration.CODEC));
}
