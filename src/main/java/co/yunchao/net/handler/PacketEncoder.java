package co.yunchao.net.handler;

import co.yunchao.net.Network;
import co.yunchao.net.packets.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
        int id = Network.getPacketId(packet);
        if (id == -1) throw new NullPointerException("Couldn't find id of packet " + packet.getClass().getSimpleName());

        out.writeInt(id);
        packet.write(out);
    }
}
