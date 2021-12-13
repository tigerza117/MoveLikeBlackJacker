package co.yunchao.net.packets;

import co.yunchao.base.enums.GameState;
import co.yunchao.base.enums.PlayerInGameState;
import io.netty.buffer.ByteBuf;

public class CardSpawnPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.CARD_SPAWN_PACKET;
    public GameState gameState;
    public PlayerInGameState playerState;

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
        return NETWORK_ID;
    }
}
