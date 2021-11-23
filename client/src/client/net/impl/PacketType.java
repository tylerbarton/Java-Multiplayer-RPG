package src.client.net.impl;

import src.client.net.PacketBuffer;

import java.io.IOException;
import java.net.Socket;

/**
 * A pre-made packet that can easily be shipped
 */
public abstract class PacketType{
    private final Socket socket;
    public int opcode;
    public PacketBuffer buffer;

    /**
     * Starts a new packet
     * @param opcode Operation code
     * @param socket Client Socket used for output stream
     */
    public PacketType(int opcode, Socket socket){
        this.opcode = opcode;
        this.socket = socket;
        this.buffer = new PacketBuffer(1024);
        this.buffer.length = buffer.length + 2;       // Buffer 2 bytes for length
        this.buffer.writeByte(opcode);                // Write Opcode (1)
    }

    /**
     * Calculates packet length
     * @throws IOException
     */
    protected void finishPacket() throws IOException {
        // Insert length at the start
        int length = this.buffer.length - 2;
        this.buffer.dataBuffer[0] = (byte)(length >> 8);
        this.buffer.dataBuffer[1] = (byte) length;
    }

    /**
     * Sends the packet to the server - make sure to include 'length' header
     */
    protected void send() throws IOException {
        this.finishPacket();
        socket.getOutputStream().write(buffer.dataBuffer, 0, buffer.length);
        socket.getOutputStream().flush();
    }
}
