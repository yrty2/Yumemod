package com.example.yumemod.item;

import Packet.DiaryPacket;
import Packet.NetworkHandler;
import com.example.yumemod.GameState;
import com.example.yumemod.utility;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import java.util.List;

import static com.example.yumemod.DreamEmulator.grantAdv;
import static com.example.yumemod.DreamEmulator.nextDreamMap;

public class yumenikki extends Item {
    public yumenikki(Properties properties){
        super(properties);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){
        ItemStack stack = player.getItemInHand(hand);
        if(level.isClientSide){
            CompoundTag tag=stack.getOrCreateTag();
            if(!tag.contains("Diary")){
                tag.put("Diary", new CompoundTag());
            }
            CompoundTag ctag = tag.getCompound("Diary");
            NetworkHandler.INSTANCE.sendToServer(new DiaryPacket(ctag));
        }
        return InteractionResultHolder.sidedSuccess(stack,!level.isClientSide);
    }
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("Diary")){
            CompoundTag ctag=tag.getCompound("Diary");
            long writeday=ctag.getLong("time");
            long dt=(System.currentTimeMillis()-writeday)/1000L;
            tooltip.add(
                    Component.literal("記録された夢がある　").withStyle(net.minecraft.ChatFormatting.GRAY).
                            append(
                                    Component.literal(utility.getTimeString(dt) + "前").withStyle(net.minecraft.ChatFormatting.YELLOW)
                            )
            );
        }
    }
}