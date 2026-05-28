package com.example.yumemod.DreamMaps;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class Dreams{
    public static final ResourceKey<Level> DREAM_FIELD=ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("yumemod", "dream_field")
    );
    public static final ResourceKey<Level> DREAM_NETHER=ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("yumemod", "dream_nether")
    );
    public static final ResourceKey<Level> ICEBERG=ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("yumemod", "iceberg")
    );
    public static final ResourceKey<Level> ABYSS=ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("yumemod", "abyss")
    );
    public static final ResourceKey<Level> SEWERS=ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("yumemod", "sewers")
    );
    public static final ResourceKey<Level> FLOATING_STAIR=ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("yumemod", "floating_stair")
    );
    public static final ResourceKey<Level> SAND_TUNNEL=ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("yumemod", "sand_tunnel")
    );
    public static final ResourceKey<Level> BRIDGE_WORLD=ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("yumemod", "bridge_world")
    );
    public static final ResourceKey<Level> STREET=ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("yumemod", "street")
    );
    public static final ResourceKey<Level> DENSE_FOREST=ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath("yumemod", "dense_forest")
    );
}