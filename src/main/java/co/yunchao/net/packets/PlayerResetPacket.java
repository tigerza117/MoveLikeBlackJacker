package co.yunchao.net.packets;

import co.yunchao.base.enums.GameState;
import co.yunchao.base.enums.Result;
import io.netty.buffer.ByteBuf;

public class PlayerResetPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.PLAYER_RESET_PACKET;

    private GameState gameState;
    private Result result;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(this.gameState.ordinal());
        buf.writeInt(this.result.ordinal());
    }

    @Override
    public void decode(ByteBuf buf) {
        this.gameState = GameState.values()[buf.readInt()];
        this.result = Result.values()[buf.readInt()];
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
