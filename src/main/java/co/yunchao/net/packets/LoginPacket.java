package co.yunchao.net.packets;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class LoginPacket extends DataPacket {
    private UUID id;
    private String name;

    @Override
    public void encode(ByteBuf byteBuf) {
        writeString(byteBuf, id.toString());
        writeString(byteBuf, name);
    }

    @Override
    public void decode(ByteBuf byteBuf) {
        this.id = UUID.fromString(readString(byteBuf));
        this.name = readString(byteBuf);
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public byte pid() {
        return 0;
    }
}
