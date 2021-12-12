package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class PlayerLeavePacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.PLAYER_LEAVE_PACKET;

    private String name;

    @Override
    public void encode(ByteBuf buf) {
        writeString(buf, this.name);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.name = readString(buf);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
