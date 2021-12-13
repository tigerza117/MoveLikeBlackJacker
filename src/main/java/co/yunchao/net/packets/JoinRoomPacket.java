package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class JoinRoomPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.JOIN_ROOM_PACKET;

    private String roomId;

    @Override
    public void encode(ByteBuf buf) {
        writeString(buf, roomId);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.roomId = readString(buf);
    }

    public String getRoomId() {
        return roomId;
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
