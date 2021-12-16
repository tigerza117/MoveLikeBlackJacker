package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class StopSoundPacket extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.STOP_SOUND_PACKET;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void encode(ByteBuf buf) {

    }

    @Override
    public void decode(ByteBuf buf) {

    }
}
