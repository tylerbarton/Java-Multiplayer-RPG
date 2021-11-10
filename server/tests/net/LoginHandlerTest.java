package tests.net;

import org.junit.jupiter.api.Test;
import src.Server;
import src.net.LoginHandler;
import src.net.LoginRequest;

import java.util.Random;

public class LoginHandlerTest {
    // Server
    private Server server;
    private Thread serverThread;

    /**
     * Simulates a server on a new thread
     */
    private void initServer(){
        server = new Server();
        serverThread = new Thread(server);
        serverThread.start();
    }


    @Test
    public void TestNewAccount() {
        Random r = new Random();
        String username = "newaccount" + r.nextInt(1000);

        initServer();
        LoginHandler loginHandler = new LoginHandler(server);
        loginHandler.processRequest(new LoginRequest(null, username));

    }

    @Test
    public void TestExistingAccount(){
        String username = "admin";

        initServer();
        LoginHandler loginHandler = new LoginHandler(server);
        loginHandler.processRequest(new LoginRequest(null, username));
    }

    //TODO: Test for login length
    //TODO: Test for invalid login
    //TODO: Test for no name
}
