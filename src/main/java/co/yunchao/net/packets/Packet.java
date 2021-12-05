package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public interface Packet {
    void read(ByteBuf buff) throws IOException;
    void write(ByteBuf buff) throws IOException;
}
