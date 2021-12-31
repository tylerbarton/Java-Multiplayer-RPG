package src.db.model;

import src.model.entity.players.Player;

/**
 * A link that acts as a serializable format for the player
 */
public class PlayerData {
    // ACCOUNT INFORMATION
    public int uuid;
    public String username;

    // ENTITY INFORMATION
    public int yLocation;
    public int xLocation;

    /**
     * Converts this object to a player object
     * @return
     */
    public Player toPlayer(){
        Player p = new Player();
        p.username = username;
        p.xPos = xLocation;
        p.yPos = yLocation;
        p.displayName = username;
        return p;
    }
}
