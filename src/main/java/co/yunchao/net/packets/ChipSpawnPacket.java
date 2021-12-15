package co.yunchao.net.packets;

import co.yunchao.base.enums.*;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class ChipSpawnPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.CHIP_SPAWN_PACKET;

    public UUID playerId;
    public UUID id;
    public ChipType type;

    @Override
    public void encode(ByteBuf buf) {
        writeString(buf, playerId.toString());
        writeString(buf, id.toString());
        buf.writeInt(this.type.ordinal());
    }

    @Override
    public void decode(ByteBuf buf) {
        this.playerId = UUID.fromString(readString(buf));
        this.id = UUID.fromString(readString(buf));
        this.type = ChipType.values()[buf.readInt()];
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
