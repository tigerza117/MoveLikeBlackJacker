package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class PlayerResetPacket extends DataPacket {

    private boolean ResetCard;
    private boolean ResetBet;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeBoolean(ResetCard);
        buf.writeBoolean(ResetBet);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.ResetBet = buf.readBoolean();
        this.ResetCard = buf.readBoolean();
    }

    @Override
    public byte pid() {
        return 0;
    }
}
