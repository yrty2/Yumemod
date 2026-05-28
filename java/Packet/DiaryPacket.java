package Packet;

import com.example.yumemod.DreamEmulator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DiaryPacket {
    private final CompoundTag tag;
    public DiaryPacket(CompoundTag tag) {
        this.tag = tag;
    }

    public static void encode(DiaryPacket msg, FriendlyByteBuf buf) {
        buf.writeNbt(msg.tag);
    }

    public static DiaryPacket decode(FriendlyByteBuf buf) {
        return new DiaryPacket(buf.readNbt());
    }

    public static void handle(DiaryPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            CompoundTag prev = DreamEmulator.nextDreamMap.get(player.getUUID());

            if (prev != null && prev.equals(msg.tag)) {
                DreamEmulator.nextDreamMap.remove(player.getUUID());
                player.level().playSound(null,player.blockPosition(),SoundEvents.VILLAGER_NO,SoundSource.PLAYERS,1.0F,1.0F);
            } else {
                DreamEmulator.nextDreamMap.put(player.getUUID(), msg.tag.copy());
                player.level().playSound(null,player.blockPosition(),SoundEvents.BOOK_PAGE_TURN,SoundSource.PLAYERS,1.0F,1.0F);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}