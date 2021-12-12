package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class TimingPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.TIMING_PACKET;

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

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
