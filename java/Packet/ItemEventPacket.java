package Packet;
import com.example.yumemod.DreamEmulator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.example.yumemod.DreamEmulator.grantAdv;

public class ItemEventPacket {
    private String eventName;
    public static void encode(ItemEventPacket msg, FriendlyByteBuf buf) {buf.writeUtf(msg.eventName,32767);}
    public ItemEventPacket(String str){this.eventName=str;}
    public static ItemEventPacket decode(FriendlyByteBuf buf) {
        return new ItemEventPacket(buf.readUtf(32767));
    }

    public static void handle(ItemEventPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            Level level=player.level();
            if("colored_tv:use".equals(msg.eventName)){
                DreamEmulator.enterWorld(player,DreamEmulator.randomDream(player));
                player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                player.playNotifySound(SoundEvents.AMETHYST_CLUSTER_BREAK,
                        SoundSource.PLAYERS, 1.0F, 1.0F);
            }
            if("broken_clock:use".equals(msg.eventName)){
                ServerLevel slevel=player.server.getLevel(Level.OVERWORLD);
                long time = slevel.getDayTime() % 24000L;
                if(time<13000) {
                    slevel.setDayTime(13000);
                    player.playNotifySound(SoundEvents.BEACON_DEACTIVATE,
                            SoundSource.PLAYERS, 1.0F, 1.0F);
                }else{
                    slevel.setDayTime(1000);
                    player.playNotifySound(SoundEvents.BEACON_ACTIVATE,
                            SoundSource.PLAYERS, 1.0F, 1.0F);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}