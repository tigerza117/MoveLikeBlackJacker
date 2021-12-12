package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class PlayerStatePacket extends DataPacket {

    private boolean Bust;
    private boolean Win;
    private boolean Lose;
    private boolean BlkJack;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeBoolean(Bust);
        buf.writeBoolean(Win);
        buf.writeBoolean(Lose);
        buf.writeBoolean(BlkJack);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.Bust = buf.readBoolean();
        this.Win = buf.readBoolean();
        this.Lose = buf.readBoolean();
        this.BlkJack = buf.readBoolean();
    }
}
