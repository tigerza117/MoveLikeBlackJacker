package co.yunchao.net.packets;

import co.yunchao.base.enums.PlayerInGameState;
import io.netty.buffer.ByteBuf;

public class PlayerStatePacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.PLAYER_STATE_PACKET;
    private PlayerInGameState player;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(player.ordinal());
    }

    @Override
    public void decode(ByteBuf buf) {
        this.player = PlayerInGameState.values()[buf.readInt()];
    }

    public PlayerInGameState getState() {
        return player;
    }

    public void setState(PlayerInGameState state) {
        this.player = state;
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
