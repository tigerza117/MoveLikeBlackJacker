package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class TimingPacket extends DataPacket {

    private int timeServer;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(timeServer);

    }

    @Override
    public void decode(ByteBuf buf) {
        this.timeServer = buf.readInt();
    }

    public void setTimeServer(int timeServer) {
        this.timeServer = timeServer;
    }

    public int getTimeServer() {
        return timeServer;
    }
}
