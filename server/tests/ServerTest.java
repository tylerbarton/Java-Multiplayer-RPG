package tests;

import org.junit.jupiter.api.Test;
import src.Server;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Tests server functionality
 * @author Tyler Barton
 */
public class ServerTest {

    /**
     * Tests if the server runs
     * Uses a new thread so that it returns
     */
    @Test
    void runs(){
        Thread sThread;
        Server s = new Server();
        sThread = new Thread(s);
        sThread.start();
        assertTrue(sThread.isAlive());
        assertTrue(s.isRunning());
    }
}
