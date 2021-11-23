package src;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.db.Database;
import src.model.world.World;
import src.net.ConnectionHandler;
import src.util.logging.LogUtil;

/**
 * Main server controller
 * @Author Tyler Barton
 * @ImplNote Server implements Runnable and has exposed methods (init, start) for testing purposes
 */
public class Server implements Runnable {
    private boolean running = false;
    private long startTime;
    private Thread connectionThread;
    private ConnectionHandler connection;
    private World world;
    private Database database;
    public final static Logger LOGGER;

    public Database getDatabase(){return this.database;}
    public void openDatabase(){database = new Database(this);
        database.open();}

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
        s.start();

        // Keep the application running while the server is alive
        while (s.running) {
            try {
                Thread.sleep(1000);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return The run status of the server.
     */
    public boolean isRunning(){ return running; }

    /**
     * Initialize the server instance
     */
    public void init(){
        // TODO: Initialize entity information
        // TODO: deserialize SQL state information
        startTime = System.currentTimeMillis();
        LOGGER.info("Started server.");
        openDatabase();
        this.initConnection();
    }

    /**
     * Starts the server
     */
    public void start(){
        this.run();
    }

    /**
     * Clean up server objects
     */
    public void stop(){
        database.close();
    }

    /**
     * Initialize the connection
     */
    private void initConnection(){
        connection = new ConnectionHandler(this);
        connectionThread = new Thread(connection);
        connectionThread.start();
    }

    /**
     * Runs the server's main loop
     * @implNote Naming convention is intention as multi-threading support can be added
     */
    @Override
    public void run(){
        this.running = true;
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
        stop();
    }
}
