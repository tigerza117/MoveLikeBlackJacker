package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class CardFlipPacket extends DataPacket {

    private boolean AllStand;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeBoolean(AllStand);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.AllStand = buf.readBoolean();
    }
}
