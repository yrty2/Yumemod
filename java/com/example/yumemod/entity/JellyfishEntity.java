package com.example.yumemod.entity;

import com.example.yumemod.DreamMaps.Dreams;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.biome.BiomeData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class JellyfishEntity extends PathfinderMob{
    public final AnimationState jetSwimAnimationState = new AnimationState();
    public JellyfishEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.FOLLOW_RANGE, 8.0);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.jetSwimAnimationState.startIfStopped(this.tickCount);
        }
        double rise = Math.sin(this.tickCount * 0.02)*0.003;
        this.setDeltaMovement(this.getDeltaMovement().add(0, rise, 0));
    }
    public static boolean canSpawn(EntityType<JellyfishEntity> type,
                                   ServerLevelAccessor level,
                                   MobSpawnType spawnType,
                                   BlockPos pos,
                                   RandomSource random) {

        return level.getLevel().dimension().equals(Dreams.ABYSS)
                && level.isEmptyBlock(pos)
                && level.isEmptyBlock(pos.above())
                && level.isEmptyBlock(pos.above(2));
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }
}