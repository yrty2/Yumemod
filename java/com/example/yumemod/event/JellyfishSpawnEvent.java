package com.example.yumemod.event;

import com.example.yumemod.DreamMaps.Dreams;
import com.example.yumemod.entity.JellyfishEntity;
import com.example.yumemod.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.example.yumemod.DreamEmulator.jellyfishEventMap;

@Mod.EventBusSubscriber(modid = "yumemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class JellyfishSpawnEvent{

    //data merge block ~ ~ ~ {LootTable:"minecraft:chests/simple_dungeon"}
    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.level instanceof ServerLevel level)) return;

        if (!level.dimension().equals(Dreams.ABYSS)) return;

        Player player = level.players().isEmpty() ? null : level.players().get(0);
        if (player == null) return;

        int range=60;
        if(jellyfishEventMap.getOrDefault(player.getUUID(),false)){
            range=10;
        }
        if (level.random.nextInt(range) != 0) return;

        double x = player.getX() + (level.random.nextDouble() - 0.5) * 32;
        double y = player.getY() + 1 + level.random.nextInt(10);
        double z = player.getZ() + (level.random.nextDouble() - 0.5) * 32;

        BlockPos pos = BlockPos.containing(x, y, z);

        if (!level.isEmptyBlock(pos)) return;

        JellyfishEntity j = ModEntities.JELLYFISH.get().create(level);
        if (j != null) {
            j.moveTo(x, y, z, level.random.nextFloat() * 360F, 0);
            level.addFreshEntity(j);
        }
    }
}