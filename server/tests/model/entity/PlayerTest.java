package tests.model.entity;

import org.junit.jupiter.api.Test;
import src.model.entity.Entity;
import src.model.entity.players.Player;

/**
 * Tests the player component that will be managed by the server
 */
public class PlayerTest {
    /**
     * hierarchy test
     */
    @Test
    void hierarchy(){
        Player p = new Player();
        assert(p instanceof Entity);
        assert(p instanceof Player);
    }
}
