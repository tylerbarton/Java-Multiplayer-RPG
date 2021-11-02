package model.players.packets.impl;

import model.players.Player;
import model.players.packets.PacketType;

/**
 * Used when the player initiates movement from the client
 * @author Tyler Barton
 */
public class Move implements PacketType {
    @Override
    public void process(Player sender, int type, int size) {

    }
}
