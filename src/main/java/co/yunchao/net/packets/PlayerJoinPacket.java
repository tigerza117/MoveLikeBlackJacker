package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class PlayerJoinPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.PLAYER_JOIN_PACKET;

    public String name;
    public UUID id;
    public boolean isDealer;
    public int offsetX;
    public int offsetY;

    @Override
    public void encode(ByteBuf buf) {
        writeString(buf, this.name);
        writeString(buf, this.id.toString());
        buf.writeBoolean(this.isDealer);
        buf.writeInt(this.offsetX);
        buf.writeInt(this.offsetY);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.name = readString(buf);
        this.id = UUID.fromString(readString(buf));
        this.isDealer = buf.readBoolean();
        this.offsetY = buf.readInt();
        this.offsetX = buf.readInt();
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
