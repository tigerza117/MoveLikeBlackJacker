package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class GameMetadataPacket extends DataPacket {

    private boolean PyHit;
    private boolean PyStand;
    private boolean PyConfirm;
    private boolean PyDouble;
    @Override
    public void encode(ByteBuf buf) {
        buf.writeBoolean(PyHit);
        buf.writeBoolean(PyStand);
        buf.writeBoolean(PyConfirm);
        buf.writeBoolean(PyDouble);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.PyHit = buf.readBoolean();
        this.PyStand = buf.readBoolean();
        this.PyConfirm = buf.readBoolean();
        this.PyDouble = buf.readBoolean();
    }
}
