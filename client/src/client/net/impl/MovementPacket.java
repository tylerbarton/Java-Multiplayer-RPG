package src.client.net.impl;

import java.net.Socket;

public class MovementPacket extends PacketType {
    /**
     * Starts a new packet
     *
     * @param opcode Operation code
     * @param socket Client Socket used for output stream
     */
    public MovementPacket(int opcode, Socket socket, int x, int y) {
        super(opcode, socket);
        buffer.writeInt(x);
        buffer.writeInt(y);
    }
}
