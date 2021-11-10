package tests.net;

import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.string.StringDecoder;
import org.junit.jupiter.api.Test;
import src.Config;
import src.client.net.Opcode;
import src.net.ConnectionHandler;
import src.net.Packet;
import src.net.PacketBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test initial connection & login functionality
 */
public class ConnectionTest {
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

    /**
     * Simulates a client sending a message to the server
     * @param msg The message to send to the server
     */
    private String clientSend(String msg) throws IOException {
        if(clientOut != null) {
            clientOut.println(msg);
        }
        return clientIn.readLine();
    }

    /**
     * Mocks sending a packet to the server
     * @param p The packet to send to the server
     * @return Server response
     */
    private String clientSendPacket(Packet p) throws IOException {
        if(clientOut != null){
            clientOut.print(p);
        }
        return clientIn.readLine();
    }

    /**
     * Tests the server simulation method found in this class
     */
    @Test
    void mockServer(){
        initServer();
        assert(serverHandler != null);
        assert(serverThread.isAlive());
    }

    /**
     * Tests the client simulation method found in this class
     */
    @Test
    void mockClient() throws IOException {
        // A server is required to test the client's ability to connect
        initServer();
        initClient();
        assert(clientSocket != null);
    }

    /**
     * Simulates a simple connection, send & receive from mock-client to server
     * @throws IOException
     */
    @Test
    void connect() throws IOException {
        initServer();
        initClient();

        String msg = "Login";
        String resp = clientSend(msg);

        assert(Objects.equals(resp, msg));
    }

    /**
     * Tests that the response of a string message is the same string
     * As to say that the message is echoed by the server.
     */
    @Test
    void stringMessage(){
        try {
            initServer();
            initClient();
            assertEquals(clientSend("hello world"), "hello world");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the ability for the server to drop an active connection
     * @throws IOException
     */
    @Test
    void dropClient() throws IOException {
        initServer();
        initClient();
    }

    @Test
    public void nettyTest() {
        EmbeddedChannel channel = new EmbeddedChannel(new StringDecoder(StandardCharsets.UTF_8));
        channel.writeInbound(Unpooled.wrappedBuffer(new byte[]{(byte)0xE2,(byte)0x98,(byte)0xA2}));
        String myObject = channel.readInbound();
        // Perform checks on your object
        assertEquals("☢", myObject);
    }

    /**
     * Tests what happens when the client does not respond
     */
    @Test
    void timeOut() throws IOException {
        initServer();
        initClient();
        clientSend("hello world");

        assert(serverHandler.getConnectionCount() == 1);
        closeClient();
        assert(serverHandler.getConnectionCount() == 0);
    }

    /**
     * A test method called with an active server
     */
    @Test
    void serverlessPing() throws IOException {
        initClient();
        assertEquals(clientSend("hello world"), "hello world");
    }


    /**
     * Tests for a unique session id to be associated with the client connection
     */
    @Test
    void sessionId() throws IOException {
        // Simulate server client connection
        initServer();
        initClient();

        // Try to get a session id back from the server
        String resp = clientSend("hello");
        //byte[] buffer = clientSocket.getInputStream().readAllBytes();
        //Packet respPacket =
        //String resp = clientSend("hello");
        //System.out.println(buffer);
        System.out.println(resp);
    }

    /**
     * Tests sending a generic packet to the server
     */
    @Test
    void sendPacket() throws IOException {
        initServer();
        initClient();

        PacketBuilder packet = new PacketBuilder(0);
        packet.writeInt(1337);
        clientSendPacket(packet.getPacket());
    }





    /**
     * Tests sending a login packet to the server to be processed
     */
    @Test
    void loginPacket() throws IOException {
        // Simulate client + server
        initServer();
        initClient();

        // Build our login packet
        String username = "helloworld";
        PacketBuilder packetBuilder = new PacketBuilder(0);
        packetBuilder.setOpcode(Opcode.Out.LOGIN.getOpcode());
        packetBuilder.writeString(username);

        // Send our packet
        String sessionId = clientSendPacket(packetBuilder.getPacket());
        String resp = clientIn.readLine();
        System.out.println(sessionId);
        System.out.println(resp);
    }
}
