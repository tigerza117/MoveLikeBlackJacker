package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class PlaySoundPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.PLAY_SOUND_PACKET;

    public String name;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void encode(ByteBuf buf) {
        writeString(buf, name);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.name = readString(buf);
    }
}
