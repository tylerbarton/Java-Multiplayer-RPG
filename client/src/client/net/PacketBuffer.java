package src.client.net;

/**
 * Builds a payload as a byte array to best transmitted to the server.
 * @implNote Length and opcode are handled outside this class.
 * @author Tyler Barton
 */
public class PacketBuffer {
    public byte[] dataBuffer;   // buffer
    public int packetEnd;       // length

    public PacketBuffer(int size){
        dataBuffer = new byte[size];
    }

    /**
     * Write a single byte to the buffer
     * @param val
     */
    public final void writeByte(int val) {
        try {
            this.dataBuffer[this.packetEnd++] = (byte) val;

        } catch (RuntimeException ignored) {}
    }

    /**
     * Write a 16-bit integer
     * @param val value
     */
    public void writeShort(short val){
        this.dataBuffer[this.packetEnd++] = (byte)(val >> 8);
        this.dataBuffer[this.packetEnd++] = (byte) val;
    }

    /**
     * Write a 32-bit integer
     * @param val value
     */
    public final void writeInt(int val){
        try {
            this.dataBuffer[this.packetEnd++] = (byte) (val >> 24);
            this.dataBuffer[this.packetEnd++] = (byte) (val >> 16);
            this.dataBuffer[this.packetEnd++] = (byte) (val >> 8);
            this.dataBuffer[this.packetEnd++] = (byte) val;
        } catch (RuntimeException ignored){}
    }

    /**
     * @return a 32-bit integer from the buffer
     */
    public final int readInt() {
        try {
            this.packetEnd += 4;

            return (this.dataBuffer[this.packetEnd - 3] << 16 & 16711680)
                    + (this.dataBuffer[this.packetEnd - 4] << 24 & -16777216)
                    + (0xFF00 & this.dataBuffer[this.packetEnd - 2] << 8)
                    + (this.dataBuffer[this.packetEnd - 1] & 255);
        } catch (RuntimeException ignored) {}
        return -1;
    }

    public final String readString(){
        StringBuilder bldr = new StringBuilder();
        byte b;
        while ((b = dataBuffer[this.packetEnd++]) != 10) {
            bldr.append((char) b);
        }
        return bldr.toString();
    }

    public final void writeString(String value) {
        try {
            byte[] stringBytes = value.getBytes();
            for (byte b : stringBytes)
                writeByte(b);
            writeByte(10);
        } catch (RuntimeException ignored) {}
    }
}
