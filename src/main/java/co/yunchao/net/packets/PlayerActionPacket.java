package co.yunchao.net.packets;

import co.yunchao.base.models.Player;
import io.netty.buffer.ByteBuf;

public class PlayerActionPacket extends DataPacket {
    private Player player;

    @Override
    public void encode(ByteBuf buf) {

    }

    @Override
    public void decode(ByteBuf buf) {

    }

    @Override
    public byte pid() {
        return 0;
    }
}
