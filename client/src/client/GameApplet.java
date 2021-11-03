package src.client;

import src.graphics.DrawingArea;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

/**
 * Controller class
 * @author Tyler Barton
 */
public class GameApplet extends Applet implements MouseListener, MouseMotionListener, MouseWheelListener,
        KeyListener, FocusListener, WindowListener, ImageProducer, ImageObserver {

    GameClient client;
    Thread clientThread = null;
    // TODO: Packet handler

    // Mouse variables
    public int vk_key = KeyEvent.VK_A;
    public int mouseX = 1, mouseY = 1;
    public int clickX = 1, clickY = 1;
    public int saveClickX, saveClickY;

    // Visuals
    public int width, height;
    private ColorModel imageModel;
    private ImageConsumer consumer;
    private Image currentScreenFrame;
    private int fps;
    private Graphics g;

    public void setGraphics(Graphics g){
        this.g = g;
    }

    @Override
    public void init(){
        try{
            // Add input listeners
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
            this.addKeyListener(this);
            this.setFocusTraversalKeysEnabled(false);

            this.width = Config.CLIENT_WIDTH;
            this.height = Config.CLIENT_HEIGHT;

            // Create the game
            client = new GameClient(this);
            startApplet();

            initGraphics();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Initialize all graphical components
     */
    private void initGraphics(){
        width = this.width;
        height = this.height;
        DrawingArea.init(width, height);
//        imageModel = new DirectColorModel(32, 16711680, '\uff00', 255);
        imageModel = new DirectColorModel(32, 0x00FF0000, 0x0000FF00, 0x000000FF);

        if (width > 1 && height > 1) {
            this.currentScreenFrame = createImage(this);
            this.commitImage();
            prepareImage(this.currentScreenFrame, this);
            this.commitImage();
            prepareImage(this.currentScreenFrame, this);
            this.commitImage();
            prepareImage(this.currentScreenFrame, this);
        }
    }

    /**
     * Starts the game logic
     */
    private void startApplet(){
        clientThread = new Thread(this.client);
        clientThread.start();
    }

    /**
     * Calculates the current screen frame and updates the variable
     */
    private synchronized void commitImage(){
        try{
            if(this.consumer != null) {
                int width = DrawingArea.getWidth();
                int height = DrawingArea.getHeight();
                int[] data = client.getGraphic().pixelData;

                this.consumer.setPixels(0, 0, width, height,
                        this.imageModel, data, 0, width);
                this.consumer.imageComplete(2);
            }
        } catch (Exception e) {
            System.out.println("Failed to commit final screen image");
            e.printStackTrace();
        }
    }

    /**
     * Calculates the next screen and display it to the user.
     * This is the final method before output is sent to the screen
     * @param g Graphics component
     * @param x offset
     * @param y offset
     */
    private void draw(Graphics g, int x, int y){
        this.commitImage();
        g.drawImage(currentScreenFrame, x, y, this);
    }

    /**
     * Called from the game client when an update is requested
     */
    public final void draw(){
        Graphics g = getGraphics();
        draw(g, 0, 0); // TODO: set offset?
        if(Config.DRAW_FPS){
            drawFps(g);
        }
    }

    /**
     * Draws the fps on the corner of the screen
     * @param g Graphical component used to draw
     */
    public final void drawFps(Graphics g){
        String fpsStr = String.valueOf(GameClient.FPS);
        g.drawString(fpsStr, 15, 15);
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(Config.VERBOSE_MODE){
            System.out.println("Key Pressed:" + e.getKeyChar());
        }
        vk_key = e.getKeyCode();
        client.handleKeyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        String options[] = { "Yes", "No!" };
        int userPrompt = JOptionPane.showOptionDialog(null, "Are you sure you wish to quit?", "Close Game?",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[1]);
        if (userPrompt == JOptionPane.YES_OPTION) {
            destroy();
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        clickX = x;
        clickY = y;

        if(Config.VERBOSE_MODE){
            System.out.println(e.toString());
            System.out.println("Mouse pressed at " + clickX + "," + clickY);
        }
        client.handleMousePressed(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
       mouseClicked(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        // Update our variables
        mouseX = x;
        mouseY = y;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getXOnScreen();
        int y = e.getY();

        // Update our variables
        mouseX = x;
        mouseY = y;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public void addConsumer(ImageConsumer ic) {
        this.consumer = ic;
        ic.setDimensions(DrawingArea.getWidth(), DrawingArea.getHeight());
        ic.setProperties(null);
        ic.setColorModel(imageModel);
        ic.setHints(14);
    }

    @Override
    public boolean isConsumer(ImageConsumer ic) {
        return this.consumer == ic;
    }

    @Override
    public void removeConsumer(ImageConsumer ic) {
        if (this.consumer == ic){
            this.consumer = null;
        }
    }

    @Override
    public void startProduction(ImageConsumer ic) {
        this.addConsumer(ic);
    }

    @Override
    public void requestTopDownLeftRightResend(ImageConsumer ic) {

    }
}
