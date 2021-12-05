package co.yunchao.net.handler;

import co.yunchao.net.Network;
import co.yunchao.net.packets.Packet;
import co.yunchao.server.net.Interface;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> output) throws Exception {
        int id = in.readInt();
        Packet packet = Network.getPacket(id);
        if (packet == null) throw new NullPointerException("Couldn't find id of packet " + id);

        packet.read(in);
        output.add(packet);
    }
}
