package co.yunchao.net.handler;

import co.yunchao.net.Network;
import co.yunchao.net.packets.DataPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<DataPacket> {
    @Override
    protected void encode(ChannelHandlerContext ctx, DataPacket packet, ByteBuf out) throws Exception {
        out.writeByte(packet.pid());
        packet.encode(out);
    }
}
