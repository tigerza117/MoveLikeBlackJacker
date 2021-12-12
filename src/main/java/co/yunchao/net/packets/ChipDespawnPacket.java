package co.yunchao.net.packets;

import co.yunchao.base.enums.GameState;
import co.yunchao.base.enums.PlayerInGameState;
import co.yunchao.base.enums.Result;
import io.netty.buffer.ByteBuf;

public class ChipDespawnPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.CHIP_DE_SPAWN_PACKET;
    private GameState gameState;
    private PlayerInGameState playerState;
    private Result result;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(this.gameState.ordinal());
        buf.writeInt(this.playerState.ordinal());
        buf.writeInt(this.result.ordinal());
    }

    @Override
    public void decode(ByteBuf buf) {
        this.gameState = GameState.values()[buf.readInt()];
        this.playerState = PlayerInGameState.values()[buf.readInt()];
        this.result = Result.values()[buf.readInt()];
    }

    public void setPlayerState(PlayerInGameState playerState) {
        this.playerState = playerState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public PlayerInGameState getPlayerState() {
        return playerState;
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
