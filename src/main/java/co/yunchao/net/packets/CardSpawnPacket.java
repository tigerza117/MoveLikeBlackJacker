package co.yunchao.net.packets;

import co.yunchao.base.enums.CardSuit;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class CardSpawnPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.CARD_SPAWN_PACKET;

    public UUID id;
    public CardSuit suit;
    public int number;
    public boolean flip = true;

    @Override
    public void encode(ByteBuf buf) {
        writeString(buf, id.toString());
        buf.writeInt(suit.ordinal());
        buf.writeInt(number);
        buf.writeBoolean(flip);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.id = UUID.fromString(readString(buf));
        this.suit = CardSuit.values()[buf.readInt()];
        this.number = buf.readInt();
        this.flip = buf.readBoolean();
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
