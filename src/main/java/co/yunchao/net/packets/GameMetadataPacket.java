package co.yunchao.net.packets;

import co.yunchao.base.enums.GameState;
import io.netty.buffer.ByteBuf;

public class GameMetadataPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.GAME_METADATA_PACKET;

    public String id;
    public GameState state;
    public int tick;

    @Override
    public void encode(ByteBuf buf) {
        writeString(buf, id);
        buf.writeInt(state.ordinal());
        buf.writeInt(tick);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.id = readString(buf);
        this.state = GameState.values()[buf.readInt()];
        this.tick = buf.readInt();
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
