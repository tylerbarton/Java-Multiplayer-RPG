package src.client.net;

/**
 * Interprets a payload as a byte array to be written to & read from
 * @implNote Length and opcode are handled outside this class.
 * @author Tyler Barton
 */
public class PacketBuffer {
    public byte[] dataBuffer;   // buffer
    public int length;          // length

    /**
     * Used on packet creation
     * @param size
     */
    public PacketBuffer(int size){
        dataBuffer = new byte[size];
    }

    /**
     * Used on packet retrieval
     * @param data
     */
    public PacketBuffer(byte[] data){
        this.dataBuffer = data.clone();
    }

    /**
     * Write a single byte to the buffer
     * @param val
     */
    public final void writeByte(int val) {
        try {
            this.dataBuffer[this.length++] = (byte) val;

        } catch (RuntimeException ignored) {}
    }

    /**
     * Write a 16-bit integer
     * @param val value
     */
    public void writeShort(short val){
        this.dataBuffer[this.length++] = (byte)(val >> 8);
        this.dataBuffer[this.length++] = (byte) val;
    }

    /**
     * Write a 32-bit integer
     * @param val value
     */
    public final void writeInt(int val){
        try {
            this.dataBuffer[this.length++] = (byte) (val >> 24);
            this.dataBuffer[this.length++] = (byte) (val >> 16);
            this.dataBuffer[this.length++] = (byte) (val >> 8);
            this.dataBuffer[this.length++] = (byte) val;
        } catch (RuntimeException ignored){}
    }

    /**
     * @return a 32-bit integer from the buffer
     */
    public final int readInt() {
        try {
            //this.length += 4;
            return ((this.dataBuffer[this.length - 4] << 24) +
                    (this.dataBuffer[this.length - 3] << 16) +
                    (this.dataBuffer[this.length - 2] << 8) +
                    (this.dataBuffer[this.length - 1]));
        } catch (RuntimeException ignored) {}
        return -1;
    }

    public final String readString(){
        StringBuilder bldr = new StringBuilder();
        byte b;
        while ((b = dataBuffer[this.length++]) != 10) {
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
