package Packet;

import com.example.yumemod.DreamEmulator;
import com.example.yumemod.GameState;
import com.example.yumemod.item.ModItems;
import com.example.yumemod.utility;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.example.yumemod.DreamEmulator.grantAdv;

public class KeyHandlerPacket {
        public static void encode(KeyHandlerPacket msg, FriendlyByteBuf buf) {}

        public KeyHandlerPacket(){}


        public static KeyHandlerPacket decode(FriendlyByteBuf buf) {
            return new KeyHandlerPacket();
        }

        public static void handle(KeyHandlerPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player == null) return;

                ItemStack stack = player.getMainHandItem();

                if(stack.is(Items.BOOK) && !DreamEmulator.dreamingMap.getOrDefault(player.getUUID(), false)){
                    stack.shrink(1);
                    player.setItemInHand(InteractionHand.MAIN_HAND,stack);
                    player.addItem(new ItemStack(ModItems.YUMENIKKI.get()));
                }
                if (stack.is(ModItems.YUMENIKKI.get()) &&
                        !DreamEmulator.dreamingMap.getOrDefault(player.getUUID(), false)) {

                    GameState recent = DreamEmulator.recentDreamMap.get(player.getUUID());

                    if (recent != null) {
                        CompoundTag ctag=utility.g2ctag(recent);
                        ctag.putLong("time",System.currentTimeMillis());
                        stack.getOrCreateTag().put("Diary", ctag);
                        player.playNotifySound(SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT,
                                SoundSource.PLAYERS, 1.0F, 1.0F);
                        grantAdv(player,"yumenikki");
                    } else {
                        player.playNotifySound(SoundEvents.VILLAGER_NO,
                                SoundSource.PLAYERS, 1.0F, 1.0F);
                    }
                }
            });
            ctx.get().setPacketHandled(true);
        }
}