package co.yunchao.net.packets;

import co.yunchao.base.enums.ChipType;
import io.netty.buffer.ByteBuf;

public class PlayerBetStackPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.PLAYER_BET_STACK_PACKET;

    public ChipType type;
    public int amount = 1;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(this.type.ordinal());
        buf.writeInt(this.amount);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.type = ChipType.values()[buf.readInt()];
        this.amount = buf.readInt();
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
