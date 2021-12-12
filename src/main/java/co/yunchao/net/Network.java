package co.yunchao.net;

import co.yunchao.net.packets.*;

import java.util.List;

public class Network {
    private static final List<Class<? extends DataPacket>> packetPool = List.of(
            LoginPacket.class,
            DisconnectPacket.class,
            PlayerJoinPacket.class
    );

    public static int getPacketId(DataPacket packet) {
        return packetPool.indexOf(packet.getClass());
    }

    public static DataPacket getPacket(int id) {
        Class<? extends DataPacket> packetClass = packetPool.get(id);
        if (packetClass == null) return null;
        DataPacket packet = null;
        try {
            packet = packetClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packet;
    }
}
