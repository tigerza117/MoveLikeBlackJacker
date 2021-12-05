package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class JoinRoomResponse implements Packet {
    private int id;

    @Override
    public void read(ByteBuf buff) throws IOException {

    }

    @Override
    public void write(ByteBuf buff) throws IOException {

    }

    public int getId() {
        return id;
    }
}
