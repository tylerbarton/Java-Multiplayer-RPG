package tests.net;

import org.junit.jupiter.api.Test;
import src.Config;
import src.client.net.PacketBuffer;
import src.client.net.impl.LoginPacket;
import src.net.ConnectionHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Client used to simulate functionality of packet functions
 */
public class ConnectionPacketTest {
    public PacketBuffer buffer;
    // Client
    private Socket clientSocket;
    private PrintWriter clientOut;
    private BufferedReader clientIn;
    // Server
    private ConnectionHandler serverHandler;
    private Thread serverThread;

    /**
     * Simulates setting up the connection handler
     */
    private void initServer(){
        serverHandler = new ConnectionHandler(null);
        serverThread = new Thread(serverHandler);
        serverThread.start();
    }

    /**
     * Simulates a client
     */
    private void initClient() throws IOException {
        clientSocket = new Socket(InetAddress.getByName(Config.HOST), Config.PORT);
        clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
        clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    /**
     * Simulates closing of the client
     */
    private void closeClient() throws IOException {
        if(clientSocket != null){
            clientSocket.close();
        }
    }

    public void startNewPacket(int opcode){
        this.buffer = new PacketBuffer(1024);
        this.buffer.length = buffer.length + 2;       // Buffer 2 bytes for length
        this.buffer.writeByte(opcode);                      // Write Opcode (1)
    }

    public void finishPacket() throws IOException {
        // Insert length at the start
        int length = this.buffer.length - 2;
        this.buffer.dataBuffer[0] = (byte)(length >> 8);
        this.buffer.dataBuffer[1] = (byte) length;

        // ship this off - make sure to include 'length' header
        clientSocket.getOutputStream().write(buffer.dataBuffer, 0, length + 2);
        clientSocket.getOutputStream().flush();
    }

    /**
     * Tests sending a generic packet to the server
     */
    @Test
    void sendPacket() throws IOException, InterruptedException {
        // Server & Client Simulation
        initServer();
        initClient();

        // Packet Creation
        startNewPacket(67);
        buffer.writeInt(68);
        buffer.writeInt(69);
        finishPacket();

        clientIn.readLine();
        closeClient();
        System.out.println("Ok.");
    }

    /**
     * Sends a packet with string
     * @throws IOException
     */
    @Test
    void sendStringPacket() throws IOException {
        // Server & Client Simulation
        initServer();
        initClient();

        // Packet Creation
        startNewPacket(67);
        buffer.writeString("username");
        finishPacket();

        clientIn.readLine();
        closeClient();
        System.out.println("Ok.");
    }

    /**
     * Tests sending a pre-made login packet to the server
     * @throws IOException
     */
    @Test
    void sendLoginPacket() throws IOException{
        // Server & Client Simulation
        //initServer();
        initClient();

        // Packet Creation
        new LoginPacket(clientSocket, "username_test").send();

        clientIn.readLine();
        closeClient();
        System.out.println("Ok.");
    }

}
