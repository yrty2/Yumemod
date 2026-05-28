package com.example.yumemod.item;

import Packet.ItemEventPacket;
import Packet.NetworkHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class usableItem extends Item {
    private final String description;
    private final String ename;
    public usableItem(Properties properties,String eventName,String desc){
        super(properties);
        this.description=desc;
        this.ename=eventName;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){
        ItemStack stack = player.getItemInHand(hand);
        if(level.isClientSide){
            NetworkHandler.INSTANCE.sendToServer(new ItemEventPacket(ename));
        }
        return InteractionResultHolder.sidedSuccess(stack,!level.isClientSide);
    }
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
            tooltip.add(
                    Component.literal(description).withStyle(net.minecraft.ChatFormatting.GRAY)
            );
    }
}