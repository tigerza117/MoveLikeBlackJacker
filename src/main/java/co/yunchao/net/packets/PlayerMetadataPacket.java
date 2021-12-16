package co.yunchao.net.packets;

import co.yunchao.base.enums.PlayerInGameState;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class PlayerMetadataPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.PLAYER_METADATA_PACKET;

    public UUID id;
    public String name;
    public PlayerInGameState state;
    public int chips;
    public int currentBetStage;
    public boolean isDealer;

    @Override
    public void encode(ByteBuf buf) {
        writeString(buf, this.name);
        writeString(buf, this.id.toString());
        buf.writeInt(state.ordinal());
        buf.writeInt(chips);
        buf.writeInt(this.currentBetStage);
        buf.writeBoolean(isDealer);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.name = readString(buf);
        this.id = UUID.fromString(readString(buf));
        this.state = PlayerInGameState.values()[buf.readInt()];
        this.chips = buf.readInt();
        this.currentBetStage = buf.readInt();
        this.isDealer = buf.readBoolean();
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
