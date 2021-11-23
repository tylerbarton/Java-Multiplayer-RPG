package src.client.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Manages outgoing packets
 * @author Tyler Barton
 */
public class PacketHandler {
    private String host;
    private int port;
    private Socket socket;
    private OutputStream outstream;
    private InputStream instream;

    /**
     * Creates a packet handler that tries to open a socket to the host
     * @param host Host ip to connect to
     * @param port Host port
     */
    public PacketHandler(String host, int port){
        this.host = host;
        this.port = port;
    }

    public Socket getSocket(){return socket;}

    /**
     * Creates a connection to the server
     * @return A socket with the connection to the server
     */
    public final void openSocket() throws IOException {
        socket = new Socket(InetAddress.getByName(host), port);
        socket.setSoTimeout(20000);
        socket.setTcpNoDelay(true);

        instream = socket.getInputStream();
        outstream = socket.getOutputStream();
    }

    /**
     *
     * @param socket Network socket to start
     */
    public void startNetworkSocket(Runnable socket){
        try{
            Thread t = new Thread(socket);
            t.setDaemon(false);
            t.start();
        } catch(RuntimeException e){
            e.printStackTrace();
        }

    }
}
