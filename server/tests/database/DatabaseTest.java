package tests.database;

import org.junit.jupiter.api.Test;
import src.db.Database;
import src.model.entity.players.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTest {

    /**
     * Tests the ability to connect to the database.
     */
    @Test
    void connection(){
        Database db = new Database(null);
        db.open();

        assert(db.isConnected());
    }

    /**
     * Tests the ability to close the connection to the database
     */
    @Test
    void close(){
        Database db = new Database(null);
        db.open();
        db.close();

        assert(!db.isConnected());
    }


    @Test
    void addPlayer(){
        Player p = new Player();
        p.xPos = 100;
        p.yPos = 100;
        p.username = "hellodatabase";

        Database db = new Database(null);
        db.open();

        db.querySavePlayerData(p);

        db.close();
    }

    /**
     * Loads a username from an input id
     */
    @Test
    void getPlayerFromId(){
        int id = 0;

        Database db = new Database(null);
        db.open();

        Player p = db.queryLoadPlayerDataById(id);

        assertEquals(p.username, "admin");
        assertEquals(p.xPos, 100);
        assertEquals(p.yPos, 100);

        db.close();
    }

    /**
     * Tests that the database returns a null object for invalid player
     */
    @Test
    void getPlayerInvalid(){
        int id = -2; // -1 is already in use from tests

        Database db = new Database(null);
        db.open();
        Player p = db.queryLoadPlayerDataById(id);
        db.close();
        assert(p==null);
    }
}
