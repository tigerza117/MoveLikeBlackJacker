package co.yunchao.net;

import co.yunchao.net.packets.*;

import java.util.List;

public class Network {
    private static final List<Class<? extends Packet>> packetPool = List.of(ConnectPacket.class, DisconnectPacket.class);

    public static int getPacketId(Packet packet) {
        return packetPool.indexOf(packet.getClass());
    }

    public static Packet getPacket(int id) {
        Class<? extends Packet> packetClass = packetPool.get(id);
        if (packetClass == null) return null;
        Packet packet = null;
        try {
            packet = packetClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packet;
    }
}
