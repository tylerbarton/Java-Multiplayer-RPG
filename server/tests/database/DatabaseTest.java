package tests.database;

import org.junit.jupiter.api.Test;
import src.db.Database;
import src.model.entity.players.Player;

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

    }
}
