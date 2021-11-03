package tests.client;

import org.junit.jupiter.api.Test;
import src.client.ClientWindow;
import src.client.GameApplet;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Tests control features
 */
public class AppletTest {

    /**
     * Tests that Applet registers a mouse press
     */
    @Test
    void MousePressed() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        int testX = 100, testY=150;

        // Initialize
        ClientWindow client = new ClientWindow(null);
        GameApplet applet = ClientWindow.applet;

        // Execute press
        robot.delay(3000);
        robot.mouseMove(testX, testY);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        assert(applet.mouseX != 0);
        assert(applet.mouseY != 0);
    }

    @Test
    void KeyboardPressed() throws AWTException {
        Robot robot = new Robot();

        // Initialize
        ClientWindow client = new ClientWindow(null);
        GameApplet applet = ClientWindow.applet;

        robot.delay(3000);
        robot.keyPress(KeyEvent.VK_A);

        assert(applet.vk_key == KeyEvent.VK_A);
        robot.delay(3000);
    }

    /**
     * Tests that the client remains active
     */
    @Test
    void isActive() throws AWTException {
        Robot robot = new Robot();
        ClientWindow client = new ClientWindow(null);
        GameApplet applet = ClientWindow.applet;

        robot.delay(3000);
        assert applet.isDisplayable();
    }

}
