package com.example.yumemod.event;

import com.example.yumemod.entity.JellyfishRenderer;
import com.example.yumemod.yumemod;
import com.example.yumemod.entity.JellyfishEntity;
import com.example.yumemod.entity.ModEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = yumemod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.JELLYFISH.get(), JellyfishEntity.createAttributes().build());
    }
    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(
                ModEntities.JELLYFISH.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                JellyfishEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
    }
    /*@SubscribeEvent
    public static void addSpawns(BiomeLoadingEvent event) {
        event.getSpawns().addSpawn(
                MobCategory.CREATURE,
                new MobSpawnSettings.SpawnerData(
                        ModEntities.JELLYFISH.get(),
                        20,
                        1,
                        3
                )
        );
    }*/
}
