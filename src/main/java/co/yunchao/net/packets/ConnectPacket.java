package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class ConnectPacket extends DataPacket {
    private String name;
    private int chip;

    public String getName() {
        return name;
    }

    @Override
    public void encode(ByteBuf buf) {

    }

    @Override
    public void decode(ByteBuf buf) {

    }
}
