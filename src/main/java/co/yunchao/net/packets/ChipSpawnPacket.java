package co.yunchao.net.packets;

import co.yunchao.base.enums.GameState;
import co.yunchao.base.enums.PlayerInGameState;
import co.yunchao.base.enums.Result;
import io.netty.buffer.ByteBuf;

public class ChipSpawnPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.CHIP_SPAWN_PACKET;
    private PlayerInGameState playerState;
    private GameState gameState;
    private Result result;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(this.playerState.ordinal());
        buf.writeInt(this.gameState.ordinal());
        buf.writeInt(this.result.ordinal());
    }

    @Override
    public void decode(ByteBuf buf) {

    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setPlayerState(PlayerInGameState playerState) {
        this.playerState = playerState;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public PlayerInGameState getPlayerState() {
        return playerState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Result getResult() {
        return result;
    }

    @Override
    public byte pid() {
        return 0;
    }
}
