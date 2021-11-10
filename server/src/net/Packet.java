package src.net;

import io.netty.buffer.ByteBuf;

/**
 * Represents a transmittable object between client and server
 */
public class Packet {
    public int length;
    public int id, next;
    private int opcode;
    private final ByteBuf payload;

    /**
     * Constructs a new packet
     * @param opcode Opcode
     * @param payload Byte buffer
     */
    public Packet(final int opcode, final ByteBuf payload){
        this.opcode = opcode;
        this.payload = payload;
    }

    public ByteBuf getPayload(){
        return this.payload;
    }

    public int getOpcode() {
        return opcode;
    }

    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }

    /**
     * @return Payload parsed as an int
     */
    public int readInt() {
        return payload.readInt();
    }

    /**
     * @return Payload parsed as a long
     */
    public long readLong() {
        return payload.readLong();
    }

    /**
     * @return Payload parsed as a string
     */
    public String readString() {
        StringBuilder builder = new StringBuilder();
        byte b = payload.readByte();
        while (payload.readableBytes() > 0 && (b) != 10) {
            builder.append((char) b);
            b = payload.readByte();
        }
        return builder.toString();
    }
}
