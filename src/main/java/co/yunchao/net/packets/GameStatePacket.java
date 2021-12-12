package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class GameStatePacket extends DataPacket {
    private boolean IsAllReady;
    private boolean IsAllStand;
    @Override
    public void encode(ByteBuf buf) {
        buf.writeBoolean(IsAllReady);
        buf.writeBoolean(IsAllStand);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.IsAllReady = buf.readBoolean();
        this.IsAllStand = buf.readBoolean();
    }

    @Override
    public byte pid() {
        return 0;
    }
}
