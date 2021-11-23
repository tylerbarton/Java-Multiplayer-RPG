package src.client.net.impl;

import src.client.net.Opcode;

import java.net.Socket;

/**
 * Used to log into the server
 */
public class LoginPacket extends PacketType {
    String username;

    public LoginPacket(Socket socket, String username){
        super(Opcode.Out.LOGIN.getOpcode(), socket);
        this.username = username;
    }
}
