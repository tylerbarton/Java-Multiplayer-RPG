package src.client;

import src.client.net.PacketHandler;
import src.client.net.impl.LoginPacket;
import src.client.net.impl.MovementPacket;
import src.graphics.GraphicsController;
import src.world.Coord;
import src.world.Entity;
import src.world.EntityHandler;
import src.world.WorldMap;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Handles Client Logic
 * @author Tyler Barton
 */
public class GameClient implements Runnable {
    // Game manager
    public int gameState = 0; // 0 - loading, 1-login, 2-game
    public static WorldMap worldMap;

    // Graphical Components
    GameApplet applet;
    public static GraphicsController graphic;
    public boolean rendering;
    public static int FPS;
    public int currentFPS;
    public long lastFPSUpdate;

    // Networking components
    public static PacketHandler packetHandler;

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
        initWorldMap();
    }
    private boolean shift = false;

    /**
     * Initializes the local version of the world map
     */
    private void initWorldMap(){
        worldMap = new WorldMap(0, 0);
    }

    /**
     * Handles the clients main loop
     */
    private void runGameLoop(){
        handleGraphics();
    }

    /**
     * Handles mouse input
     * @param e Mouse event
     */
    public void handleMousePressed(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        Coord world = WorldMap.ScreenToWorldCoord(x, y);

        // Send a movement packet
        EntityHandler.player.xPosition = x;
        EntityHandler.player.yPosition = y;
        EntityHandler.changed = true;
        try {
            new MovementPacket(GameClient.packetHandler.getSocket(), world.x, world.y).send();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if(Config.VERBOSE_MODE){
            System.out.println("Mouse World Coords: " + world.x + "," + world.y);
        }
    }

    /**
     * Callable thread method to run the client
     */
    @Override
    public final void run() {
        this.lastFPSUpdate = System.currentTimeMillis();

        // Login
        this.gameState = 1;
        try {
            this.packetHandler = new PacketHandler(Config.HOST, Config.HOST_PORT);
            packetHandler.openSocket();
            new LoginPacket(packetHandler.getSocket(), Config.USERNAME).send();
            System.out.println("Connection established");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Connection failed.");
        }

        // Game is running
        this.gameState = 2;
        while(this.gameState == 2){
            runGameLoop();
        }
    }

    /**
     * Handles keyboard input
     * @param e
     */
    public void handleKeyPressed(KeyEvent e){
        if(e.getKeyCode() == 16) shift = true;
    }

    public void handleKeyReleased(KeyEvent e){
        if(e.getKeyCode() == 16) shift = false;
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
            // Update the entities - could move this to main game loop
            drawGameWorld();

            // Draw the screen
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
        if(worldMap.needsUpdate()){
            System.out.println("Redrawing entities");
            for(Entity e : worldMap.getEntityHandler().entities.values()){
                GameClient.graphic.addEntity(e.sprite, e.xPosition, e.yPosition);
            }
            worldMap.getEntityHandler().changed = false;
        }
    }
}
