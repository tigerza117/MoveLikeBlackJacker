package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class DisconnectPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.DISCONNECT_PACKET;

    public String message;
    public boolean showDialog;

    @Override
    public void encode(ByteBuf buf) {
        writeString(buf, this.message);
        buf.writeBoolean(showDialog);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.message = readString(buf);
        this.showDialog = buf.readBoolean();
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
