package co.yunchao.client.net;

import co.yunchao.net.packets.DataPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;

public class NetworkHandler extends SimpleChannelInboundHandler<DataPacket> {
    private final Interface anInterface;

    public NetworkHandler(Interface anInterface) {
        this.anInterface = anInterface;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataPacket packet) throws Exception {
        Platform.runLater(() -> anInterface.getOnHandler().run(packet));
    }
}
