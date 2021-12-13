package co.yunchao.net.packets;

import co.yunchao.base.enums.GameState;
import co.yunchao.base.enums.PlayerInGameState;
import io.netty.buffer.ByteBuf;

public class CardDeSpawnPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.CARD_DE_SPAWN_PACKET;
    public PlayerInGameState playerState;
    public GameState gameState;

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

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
