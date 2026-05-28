package com.example.yumemod.entity;

import com.example.yumemod.yumemod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, yumemod.MODID);

    public static final RegistryObject<EntityType<JellyfishEntity>> JELLYFISH =
            ENTITIES.register("jellyfish",
                    () -> EntityType.Builder
                            .of(JellyfishEntity::new, MobCategory.AMBIENT)
                            .sized(0.6f, 0.8f)
                            .build("jellyfish")
            );
}