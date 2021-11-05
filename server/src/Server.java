package src;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.util.logging.LogUtil;

/**
 * Main server controller
 * @Author Tyler Barton
 */
public class Server implements Runnable {
    private boolean running = false;

    // Manages logging of all server details
    public final static Logger LOGGER;

    // Logger configuration
    static {
        LogUtil.configure();
        LOGGER = LogManager.getLogger();
    }

    public static void main(String[] args){
        // TODO: Initialize entity information
        // TODO: deserialize SQL state information
        Server s = new Server();
        s.init();
    }

    /**
     * Initialize the server instance
     */
    private void init(){
        LOGGER.info("Hello World!");
    }

    /**
     * Method called when new thread is started
     */
    @Override
    public void run() {
    }
}
