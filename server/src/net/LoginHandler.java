package src.net;

import io.netty.channel.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.Server;
import src.db.Database;
import src.model.entity.players.Player;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Handles initial client connection request to the server
 */
public class LoginHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Server server;
    private final Database db;
    private final Queue<LoginRequest> requests;
    private volatile Boolean running = false;

    /**
     * @param server The server that contains this component
     */
    public LoginHandler(Server server){
        this.server = server;
        //this.db = server.getDatabase();
        this.db = server.getDatabase();
        requests = new ConcurrentLinkedQueue<>();
    }

    /**
     * Processes the login request
     * @param request Generated login request
     */
    public Player processRequest(final LoginRequest request){
        String username = request.username;
        Player p = db.queryLoadPlayerDataByName(username);
        if(p != null){
            LOGGER.info("Account logged in: " + username);
        } else {
            LOGGER.info("Account created: " + username);
            p = new Player();
            p.username = username;
            p.displayName = username;
            p.xPos = 0;
            p.yPos = 0;
            db.querySavePlayerData(p);
        }
        return p;
    }

    /**
     * Add a request to the login queue
     * @param request login request from a client
     */
    public void addRequest(final LoginRequest request){
        requests.add(request);
    }

    private boolean shouldAllowConnection(Channel channel){
        //TODO: implement
        return false;
    }


    @Override
    public void run() {
        this.running = true;
        synchronized(running) {
            try {
                // Save requests should be run BEFORE logout requests or else we get duplication glitch because a user can login before they've saved, but after they've logged out.
                // See Player.logout, save requests are added first before removal so we are good.
                LoginRequest request;
                while ((request = requests.poll()) != null) {
                    processRequest(request);
                }
            } catch (final Throwable e) {
                LOGGER.catching(e);
            }
        }
    }
}
