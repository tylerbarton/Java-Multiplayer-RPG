package src.client;

/**
 * Houses configurable options applicable to the entire client.
 */
public class Config {
    // Server Connection
    public static final String USERNAME = "Admin";
    public static final String HOST = "127.0.0.1";
    public static final int HOST_PORT = 10242;

    // Client Window Configuration
    public static final String CLIENT_TITLE = "CS242 Final Project by Tyler Barton";
    public static final int CLIENT_WIDTH = 800;
    public static final int CLIENT_HEIGHT = 512;

    // Graphical & Performance
    public static final int MAX_SPRITES = 20000;
    public static final int SPRITE_WIDTH = 16;
    public static final int SPRITE_HEIGHT = 16;

    // Debug Options
    public static final boolean VERBOSE_MODE = false;
    public static final boolean DRAW_FPS = true;
}
