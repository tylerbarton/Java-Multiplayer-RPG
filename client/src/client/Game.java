package src.client;

/**
 * Main launcher and controller class for the client
 * @author Tyler Barton
 */
public class Game extends GameApplet {
    private static Game instance;

    public static void main(String args[]) {
        try{
            // Create and start the game client
            instance = new ClientWindow(args);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
