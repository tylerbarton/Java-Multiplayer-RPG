package src.client.net.impl;

import src.client.net.Opcode;

import java.net.Socket;

public class MovementPacket extends PacketType {
    /**
     * Starts a new movement packet
     * @param socket Client Socket used for output stream
     */
    public MovementPacket(Socket socket, int x, int y) {
        super(Opcode.Out.MOVEMENT.getOpcode(), socket);
        buffer.writeInt(x);
        buffer.writeInt(y);
    }
}
