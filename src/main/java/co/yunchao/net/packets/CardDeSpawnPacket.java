package co.yunchao.net.packets;

import co.yunchao.base.enums.GameState;
import co.yunchao.base.enums.PlayerInGameState;
import co.yunchao.server.models.Player;
import io.netty.buffer.ByteBuf;

public class CardDeSpawnPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.CARD_DE_SPAWN_PACKET;
    private PlayerInGameState playerState;
    private GameState gameState;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(playerState.ordinal());
        buf.writeInt(gameState.ordinal());
    }

    @Override
    public void decode(ByteBuf buf) {
        this.playerState = PlayerInGameState.values()[buf.readInt()];
        this.gameState = GameState.values()[buf.readInt()];
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setPlayerState(PlayerInGameState playerState) {
        this.playerState = playerState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public PlayerInGameState getPlayerState() {
        return playerState;
    }

    @Override
    public byte pid() {
        return this.NETWORK_ID;
    }
}
