package co.yunchao.net.packets;

import co.yunchao.base.enums.GameState;
import co.yunchao.base.enums.PlayerInGameState;
import io.netty.buffer.ByteBuf;

public class CardFlipPacket extends DataPacket {
    private PlayerInGameState playerState;
    private GameState gameState;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(gameState.ordinal());
    }

    @Override
    public void decode(ByteBuf buf) {
        this.gameState = GameState.values()[buf.readInt()];
    }

    @Override
    public byte pid() {
        return 0;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }
}
