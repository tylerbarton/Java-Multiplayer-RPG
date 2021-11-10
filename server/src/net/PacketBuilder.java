package src.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Similar to StringBuilder, acts as a way to build a packet with appropriate data
 * @author Tyler Barton
 */
public class PacketBuilder {
    // Variables
    private int opcode;
    private ByteBuf payload = Unpooled.buffer();

    public PacketBuilder(){ this(0); }
    public PacketBuilder(int opcode){this.opcode = opcode;}

    // Accessors
    public void setOpcode(int opcode){this.opcode = opcode;}

    // Methods
    /**
     * Writes an integer to the payload
     * @param i the integer to write to the payload
     */
    public void writeInt(int i){
        payload.writeInt(i);
    }

    /**
     * Writes a string to the payload
     *
     * @param string The string to write
     */
    public void writeString(String string) {
        payload.writeBytes(string.getBytes());
        payload.writeByte((byte) 10);
    }

    /**
     * @return The constructed packet
     */
    public Packet getPacket(){
        return new Packet(opcode, payload);
    }
}
