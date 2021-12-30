package src.client;

import src.graphics.DrawingArea;
import src.graphics.GraphicsController;
import src.world.Coord;
import src.world.WorldMap;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Handles Client Logic
 * @author Tyler Barton
 */
public class GameClient implements Runnable {
    // Game manager
    public int gameState = 0;

    // Graphical Components
    GameApplet applet;
    public static GraphicsController graphic;
    public boolean rendering;
    public static int FPS;
    public int currentFPS;
    public long lastFPSUpdate;

    /**
     * @return The graphics handler for the game
     */
    public GraphicsController getGraphic(){
        return graphic;
    }

    /**
     * Create a new game
     * @param gameApplet Link to the applet controlling the game
     */
    public GameClient(GameApplet gameApplet) {
        this.applet = gameApplet;
        GameClient.graphic = new GraphicsController(gameApplet.width, gameApplet.height);
    }

    /**
     * Callable thread method to run the client
     */
    @Override
    public final void run() {
        // Start game
        // TODO: initial loading of everything
        GameClient.graphic = new GraphicsController(DrawingArea.getWidth(),
                DrawingArea.getHeight());
        this.lastFPSUpdate = System.currentTimeMillis();
        this.gameState = 1;

        // Game is running
        while(this.gameState == 1){
            runGameLoop();
        }
    }

    /**
     * Handles the clients main loop
     */
    private void runGameLoop(){
        handleGraphics();
    }

    /**
     * Handles mouse input
     * @param e
     */
    public void handleMousePressed(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        Coord world = WorldMap.ScreenToWorldCoord(x, y);

        if(Config.VERBOSE_MODE){
            System.out.println("Mouse World Coords: " + world.x + "," + world.y);
        }
    }

    /**
     * Handles keyboard input
     * @param e
     */
    public void handleKeyPressed(KeyEvent e){

    }

    /**
     * In the run
     */
    private void handleGraphics(){
        //this.draw();

        // FPS Information
        currentFPS++;
        long time = System.currentTimeMillis();
        if (time - lastFPSUpdate >= 1000) {
            applet.draw();
            lastFPSUpdate = time;
            GameClient.FPS = currentFPS;
            currentFPS = 0;
        }
        rendering=false;
    }

    /**
     * Checks how to update the screen for the current client state.
     * If the user is in game, it will draw the game world.
     */
    private void draw(){
        if(this.rendering){
            this.rendering = false;
        }

        // Draw game world
        this.drawGameWorld();
    }

    /**
     * Draws the game world to the screen
     */
    private void drawGameWorld() {
        System.out.println("Drawing Game World");
    }
}
