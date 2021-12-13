package co.yunchao.net.packets;

import co.yunchao.base.enums.CardSuit;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class CardSpawnPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.CARD_SPAWN_PACKET;

    public UUID id;
    public CardSuit suit;
    public int point;
    public boolean flip = true;

    @Override
    public void encode(ByteBuf buf) {
        writeString(buf, id.toString());
        buf.writeInt(suit.ordinal());
        buf.writeInt(point);
        buf.writeBoolean(flip);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.id = UUID.fromString(readString(buf));
        this.suit = CardSuit.values()[buf.readInt()];
        this.point = buf.readInt();
        this.flip = buf.readBoolean();
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
