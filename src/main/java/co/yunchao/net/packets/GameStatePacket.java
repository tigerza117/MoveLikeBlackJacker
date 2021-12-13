package co.yunchao.net.packets;

import co.yunchao.base.enums.GameState;
import io.netty.buffer.ByteBuf;

public class GameStatePacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.GAME_STATE_PACKET;
    public GameState gameState;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(this.gameState.ordinal());
    }

    @Override
    public void decode(ByteBuf buf) {
        this.gameState = GameState.values()[buf.readInt()];
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
