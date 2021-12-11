package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

public class PlayerJoinPacket extends DataPacket {
    private String name;
    private String id;

    @Override
    public void encode(ByteBuf buf) {
        writeString(buf, this.name);
        writeString(buf, this.id);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.name = readString(buf);
        this.id = readString(buf);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }
}