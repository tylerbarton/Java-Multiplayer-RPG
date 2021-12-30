package model.players.packets;

import model.players.Player;

/**
 * A generic packet object
 * @author Tyler Barton
 */
public interface PacketType {
    /**
     * In the server loop, each packet is processed based on this method
     * @param sender The player client that sent this packet
     * @param type The type of packet this is
     * @param size The size of the packet
     */
    public void process(Player sender, int type, int size);
}
