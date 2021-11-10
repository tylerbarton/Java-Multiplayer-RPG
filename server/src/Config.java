package src;

/**
 * Contains configurable options applicable to the entire server.
 */
public class Config {
    // CONNECTION OPTIONS
    public final static String HOST = "127.0.0.1";
    public final static int PORT = 10242;

    // DATABASE OPTIONS
    public final static String DATABASE_HOST = "localhost:3306";
    public final static String DATABASE_NAME = "ServerData";
    public final static String DATABASE_USER = "admin";
    public final static String DATABASE_PASS = "password";

    /**
     * Tick (or update) rate for the server
     */
    public final static int TICK_RATE = 200;
    public final static int MAX_PLAYERS = 20;
}
