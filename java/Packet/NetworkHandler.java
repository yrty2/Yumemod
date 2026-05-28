package Packet;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath("yumemod","main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int id = 0;

    public static void register() {
        INSTANCE.registerMessage(id++,
                DiaryPacket.class,
                DiaryPacket::encode,
                DiaryPacket::decode,
                DiaryPacket::handle
        );
        INSTANCE.registerMessage(id++,
                KeyHandlerPacket.class,
                KeyHandlerPacket::encode,
                KeyHandlerPacket::decode,
                KeyHandlerPacket::handle
        );
        INSTANCE.registerMessage(id++,
                ItemEventPacket.class,
                ItemEventPacket::encode,
                ItemEventPacket::decode,
                ItemEventPacket::handle
        );
    }
}