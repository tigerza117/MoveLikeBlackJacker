package co.yunchao.net.packets;

import co.yunchao.base.enums.PlayAction;
import io.netty.buffer.ByteBuf;

public class PlayerActionPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.PLAYER_ACTION_PACKET;

    public PlayAction action;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(this.action.ordinal());
    }

    @Override
    public void decode(ByteBuf buf) {
        this.action = PlayAction.values()[buf.readInt()];
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
