package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class GameMetadataPacket extends DataPacket {

    @Override
    public void encode(ByteBuf buf) {

    }

    @Override
    public void decode(ByteBuf buf) {

    }

    @Override
    public byte pid() {
        return 0;
    }
}
