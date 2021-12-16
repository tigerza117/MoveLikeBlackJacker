package co.yunchao.net.packets;

import co.yunchao.base.enums.ScoreColorType;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class SetScorePacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.SET_SCORE_PACKET;

    public UUID playerId;
    public String text;
    public ScoreColorType colorType;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void encode(ByteBuf buf) {
        writeString(buf, playerId.toString());
        writeString(buf, text);
        buf.writeInt(colorType.ordinal());
    }

    @Override
    public void decode(ByteBuf buf) {
        this.playerId = UUID.fromString(readString(buf));
        this.text =  readString(buf);
        this.colorType = ScoreColorType.values()[buf.readInt()];
    }
}
