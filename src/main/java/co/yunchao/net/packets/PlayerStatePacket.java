package co.yunchao.net.packets;

import co.yunchao.base.enums.PlayerInGameState;
import io.netty.buffer.ByteBuf;

public class PlayerStatePacket extends DataPacket {
    private PlayerInGameState state;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(state.ordinal());
    }

    @Override
    public void decode(ByteBuf buf) {
        this.state = PlayerInGameState.values()[buf.readInt()];
    }

    public PlayerInGameState getState() {
        return state;
    }

    public void setState(PlayerInGameState state) {
        this.state = state;
    }
}
