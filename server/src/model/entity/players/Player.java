package src.model.entity.players;

import io.netty.channel.Channel;
import src.model.entity.Creature;
import src.net.Packet;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Represents a player object in the game world
 */
public class Player extends Creature {
    // General Information
    public String username;
    // Networking
    private Channel channel;
    private final LinkedList<Packet> incomingPackets = new LinkedList<>();
    private final ArrayList<Packet> outgoingPackets = new ArrayList<>();

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

    /**
     * Adds a received packet to this player model to be processed later
     * @param packet
     */
    public void addPacket(Packet packet){
        incomingPackets.add(packet);
    }
}
