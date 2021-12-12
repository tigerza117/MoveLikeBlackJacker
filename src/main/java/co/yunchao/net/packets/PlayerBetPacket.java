package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class PlayerBetPacket extends DataPacket {

    private int Bet;
    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(Bet);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.Bet = buf.readInt();
    }
}
