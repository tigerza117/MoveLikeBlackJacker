package co.yunchao.net;

import co.yunchao.net.packets.*;

public class Network {
    private static final Class<? extends DataPacket>[] packetPool = new Class[256];

    public Network() {
        this.registerPackets();
    }

    public static DataPacket getPacket(int id) {
        Class<? extends DataPacket> clazz = packetPool[id];
        if (clazz != null) {
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void registerPacket(byte id, Class<? extends DataPacket> clazz) {
        packetPool[id & 0xff] = clazz;
    }

    private void registerPackets() {
        this.registerPacket(ProtocolInfo.LOGIN_PACKET, LoginPacket.class);
        this.registerPacket(ProtocolInfo.CARD_DE_SPAWN_PACKET, CardDeSpawnPacket.class);
        this.registerPacket(ProtocolInfo.CARD_FLIP_PACKET, CardFlipPacket.class);
        this.registerPacket(ProtocolInfo.CARD_SPAWN_PACKET, CardSpawnPacket.class);
        this.registerPacket(ProtocolInfo.CHIP_DE_SPAWN_PACKET, ChipDespawnPacket.class);
        this.registerPacket(ProtocolInfo.CHIP_SPAWN_PACKET, ChipSpawnPacket.class);

    }

}
