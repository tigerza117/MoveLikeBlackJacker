package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class CardDeSpawnPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.CARD_DE_SPAWN_PACKET;

    public UUID id;

    @Override
    public void encode(ByteBuf buf) {
        writeString(buf, id.toString());
    }

    @Override
    public void decode(ByteBuf buf) {
        this.id = UUID.fromString(readString(buf));
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
