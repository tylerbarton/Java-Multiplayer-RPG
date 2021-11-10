package src.net;

import io.netty.channel.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.model.entity.players.Player;

import java.net.InetSocketAddress;


/**
 * A login request made by a user client
 */
public class LoginRequest {
    // Login Response Codes
    public static final int LOGIN_UNSUCCESSFUL = 0;
    public static final int LOGIN_SUCCESSFUL = 1;
    public static final int ACCOUNT_IN_USE = 2;
    public static final int SERVER_TIMEOUT = 3;
    private final static Logger LOGGER = LogManager.getLogger();
    public String username;
    public Channel channel;
    private boolean processed = false;
    private String ipAddress;
    private Player loadedPlayer;
    private int response;
    /**
     * Creates a new login request and attempts to connect
     */
    public LoginRequest(Channel channel, String username){
        //this.channel = channel;
        this.username = username;
        //this.ipAddress = resolveIpAddress(channel);
    }

    public boolean isProcessed(){return processed;}

    /**
     * @param channel Client connection channel
     * @return A formatted ipAddress
     */
    private String resolveIpAddress(Channel channel){
        return ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();
    }

    /**
     * Process the login request
     */
    public void process(){
        int response = LOGIN_SUCCESSFUL;
        this.response = response;
        LOGGER.info("Processed login request for " + this.username + " with response: " + this.response);
    }
}
