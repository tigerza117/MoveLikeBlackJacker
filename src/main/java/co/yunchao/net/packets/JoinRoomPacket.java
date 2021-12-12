package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class JoinRoomPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.JOIN_ROOM_PACKET;

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
