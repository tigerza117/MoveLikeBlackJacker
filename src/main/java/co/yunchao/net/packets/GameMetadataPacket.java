package co.yunchao.net.packets;

import co.yunchao.base.enums.GameState;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class GameMetadataPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.GAME_METADATA_PACKET;

    public String id;
    public GameState state;
    public int tick = 5;
    public int maxTick = 5;
    public UUID currentPlayerTurn = UUID.randomUUID();

    @Override
    public void encode(ByteBuf buf) {
        writeString(buf, id);
        buf.writeInt(state.ordinal());
        buf.writeInt(tick);
        buf.writeInt(maxTick);
        writeString(buf, currentPlayerTurn.toString());
    }

    @Override
    public void decode(ByteBuf buf) {
        this.id = readString(buf);
        this.state = GameState.values()[buf.readInt()];
        this.tick = buf.readInt();
        this.maxTick = buf.readInt();
        this.currentPlayerTurn = UUID.fromString(readString(buf));
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
