package co.yunchao.net.handler;

import co.yunchao.net.Network;
import co.yunchao.net.packets.DataPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> output) throws Exception {
        byte id = in.readByte();
        DataPacket packet = Network.getPacket(id);
        if (packet == null) throw new NullPointerException("Couldn't find id of packet " + id);

        packet.decode(in);
        output.add(packet);
    }
}
