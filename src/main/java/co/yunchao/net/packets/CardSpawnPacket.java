package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class CardSpawnPacket extends DataPacket {

    private boolean ResetCard;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeBoolean(ResetCard);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.ResetCard = buf.readBoolean();
    }

}
