package co.yunchao.net.packets;

import co.yunchao.base.enums.PlayerInGameState;
import io.netty.buffer.ByteBuf;

public class PlayerStatePacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.PLAYER_STATE_PACKET;
    public PlayerInGameState player;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(player.ordinal());
    }

    @Override
    public void decode(ByteBuf buf) {
        this.player = PlayerInGameState.values()[buf.readInt()];
    }


    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
