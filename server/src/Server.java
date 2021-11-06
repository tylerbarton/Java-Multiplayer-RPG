package src;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.model.world.World;
import src.util.logging.LogUtil;

/**
 * Main server controller
 * @Author Tyler Barton
 */
public class Server {
    private boolean running = false;
    private long startTime;
    private World world;
    public final static Logger LOGGER;

    // Logger configuration
    static {
        LogUtil.configure();
        LOGGER = LogManager.getLogger();
    }


    // If we wanted multiple worlds, we could implement runnable in this class
    // Then use a ConcurrentHashMap
    public static void main(String[] args){
        Server s = new Server();
        s.init();
        s.run();
    }

    /**
     * Initialize the server instance
     */
    private void init(){
        // TODO: Initialize entity information
        // TODO: deserialize SQL state information
        startTime = System.currentTimeMillis();
        LOGGER.info("Started server.");
    }

    /**
     * Runs the server's main loop
     * @implNote Naming convention is intention as multi-threading support can be added
     */
    private void run(){
        while(running){
            // TODO: Process incoming packets
            // TODO: Game loop + Game Events
            // TODO: World updating + Movement updating
            // TODO: Process outgoing packets
            // TODO: Book keeping
            try{

            }catch(final Throwable t){
                LOGGER.catching(t);
            }
        }
    }
}
