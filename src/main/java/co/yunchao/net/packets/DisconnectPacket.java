package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class DisconnectPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.DISCONNECT_PACKET;
    private String message;
    private boolean showDialog;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isShowDialog() {
        return showDialog;
    }

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }
}
