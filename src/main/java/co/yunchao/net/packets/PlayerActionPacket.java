package co.yunchao.net.packets;

import co.yunchao.base.enums.GameState;
import co.yunchao.base.enums.PlayerInGameState;
import io.netty.buffer.ByteBuf;

public class PlayerActionPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.PLAYER_ACTION_PACKET;
    public PlayerInGameState player;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(this.player.ordinal());
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
