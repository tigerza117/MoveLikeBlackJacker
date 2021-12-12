package co.yunchao.net.packets;

import co.yunchao.base.enums.GameState;
import co.yunchao.base.enums.PlayerInGameState;
import io.netty.buffer.ByteBuf;

public class PlayerBetPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.PLAYER_BET_PACKET;
    private GameState gameState;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(this.gameState.ordinal());
    }

    @Override
    public void decode(ByteBuf buf) {
        this.gameState = GameState.values()[buf.readInt()];
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
