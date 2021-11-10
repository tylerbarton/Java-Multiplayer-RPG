package src.model.entity.players;

import io.netty.channel.Channel;
import src.model.entity.Creature;

/**
 * Represents a player object in the game world
 */
public class Player extends Creature {
    private Channel channel;
    public String username;

    /**
     * Used to process a login attempt on this player
     */
    public void Login(Channel channel, String username){
        this.channel = channel;
    }

    /**
     * Used to process a logout attempt or a disconnect of the user.
     */
    public void Logout(){

    }

    /**
     * @return the id in the database
     */
    public int getDbUuid(){
        // TODO: How to generate this?
        return -1;
    }
}
