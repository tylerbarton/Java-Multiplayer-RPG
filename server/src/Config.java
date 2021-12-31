package src;

/**
 * Contains configurable options applicable to the entire server.
 */
public class Config {
    // Network Configuration
    public final static String HOST = "127.0.0.1";
    public final static int PORT = 10242;
    public final static int PACKET_TIMEOUT_SEC = 10;

    // Database Configuration
    public final static String DATABASE_HOST = "127.0.0.1:3306";
    public final static String DATABASE_NAME = "serverdb";
    public final static String DATABASE_USER = "admin";
    public final static String DATABASE_PASS = "password";

    /**
     * Tick (or update) rate for the server
     */
    public final static int TICK_RATE = 200;
    public final static int MAX_PLAYERS = 100;
}
