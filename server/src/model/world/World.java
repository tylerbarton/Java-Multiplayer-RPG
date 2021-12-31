package src.model.world;

import src.client.net.Opcode;
import src.client.net.PacketBuffer;
import src.model.entity.Creature;
import src.model.entity.players.Player;
import src.net.Packet;

import java.util.ArrayList;

/**
 * Manages the game world state
 */
public class World {
    public static ArrayList<Player> players;
    public static ArrayList<Creature> npcs;

    /**
     * Called on world creation
     */
    public void init(){
        players = new ArrayList<>();
        npcs = new ArrayList<>();
    }

    /**
     * Adds a player to the managed player list
     * @param p
     */
    public void addPlayer(Player p){
        players.add(p);
    }

    /**
     * Parse packets
     * @implNote Could be moved to a packet parsing class
     * @implNote Could replace this with process() method and do general processing
     */
    public void processPackets(){
        if(players.size() > 0){
            System.out.println("More than 0 player");
            for (Player p:
                    players) {
                System.out.println("Processing packets for " + p.username);
                // Get the next packet
                Packet packet = p.incomingPackets.poll();
                if(packet == null) continue;

                System.out.println("Processing packets for " + p.username + "," + packet.getOpcode());

                // Parse the packet
                if(packet.getOpcode() == Opcode.Out.MOVEMENT.getOpcode()){
                    PacketBuffer buffer = new PacketBuffer(packet.getPayload().array());
                    p.xPos = buffer.readInt();
                    p.yPos = buffer.readInt();
                    System.out.println("Updated player position to: " + p.xPos + "," + p.yPos);
                } else {
                    System.out.println("Nonrecognized packet detected");
                }
            }
        }
    }
}
