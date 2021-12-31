package src.world;

import src.graphics.Sprite;

/**
 * Represents an object on the map.
 * @implNote Only represents monsters & the player right now
 * @author Tyler Barton
 */
public class Entity {
    public Sprite sprite;
    public String displayName;
    public int serverId;
    public int xPosition, yPosition;
}
