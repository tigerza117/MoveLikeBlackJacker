package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class PlayerSound extends DataPacket {
    @Override
    public byte pid() {
        return 0;
    }

    @Override
    public void encode(ByteBuf buf) {

    }

    @Override
    public void decode(ByteBuf buf) {

    }
}
