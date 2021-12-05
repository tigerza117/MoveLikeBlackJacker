package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class ConnectPacket implements Packet {
    private String name;

    @Override
    public void read(ByteBuf buff) throws IOException {

    }

    @Override
    public void write(ByteBuf buff) throws IOException {

    }

    public String getName() {
        return name;
    }
}
