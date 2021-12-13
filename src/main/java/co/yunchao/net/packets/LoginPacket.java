package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class LoginPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.LOGIN_PACKET;

    public UUID id;
    public String name;

    @Override
    public void encode(ByteBuf byteBuf) {
        writeString(byteBuf, id.toString());
        writeString(byteBuf, name);
    }

    @Override
    public void decode(ByteBuf byteBuf) {
        this.id = UUID.fromString(readString(byteBuf));
        this.name = readString(byteBuf);
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
