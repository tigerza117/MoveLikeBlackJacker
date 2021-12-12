package co.yunchao.net.packets;

import network.ycc.raknet.packet.SimpleFramedPacket;

public abstract class DataPacket extends SimpleFramedPacket {
    public abstract byte pid();
}
