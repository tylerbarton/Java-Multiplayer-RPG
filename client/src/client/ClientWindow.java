package src.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Viewer class
 */
public class ClientWindow extends Game implements ActionListener, WindowListener
{
    public static GameApplet applet;
    private static JPanel gamePanel;
    private static JFrame frame;
    public static JFrame getFrame() {
        return frame;
    }


    /**
     * Creates a standardized game panel to be housed in the JFrame.
     * @return Game Panel
     */
    private JPanel createGamePanel(){
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BorderLayout());
        jpanel.setPreferredSize(new Dimension(Config.CLIENT_WIDTH, Config.CLIENT_HEIGHT));

        // Add the game applet
        applet = new Game();
        jpanel.add(applet);
        applet.setGraphics(jpanel.getGraphics());
        applet.init();
        applet.start();

        // Listener linking
        frame.addWindowListener(applet);

        return jpanel;
    }

    /**
     * Creates the initial window
     */
    public void initUI() {
        try {
            JPopupMenu.setDefaultLightWeightPopupEnabled(false);
            frame = new JFrame(Config.CLIENT_TITLE);
            frame.setLayout(new BorderLayout());
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setUndecorated(false);

            JPanel gamePanel = createGamePanel();


            frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClientWindow(String args[]) {
        super();
        try {
            initUI();
            // Test ingmethod

//            Font font = new Font("Helvetica", Font.PLAIN,12);
//            drawString(font, "hello", 512, 512, frame.getGraphics());
//            } catch (Exception ex) {
//            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawString(Font font, String str, int y, int x, Graphics g) {
        try {
            FontMetrics metrics = getFontMetrics(font);
            g.setFont(font);
            g.setColor(Color.red);
            g.drawString(str, x - metrics.stringWidth(str) / 2, y + metrics.getHeight() / 4);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draw method
     * @param g graphics object to draw on
     */
    @Override
    public void paint(Graphics g){
        System.out.println("Painting ClientWindow");
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

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
}
