package src.world;

import src.graphics.Sprite;

import java.util.HashMap;

/**
 * Handles the entities on the screen including the player
 * @author Tyler Barton
 */
public class EntityHandler {
    public static Entity player;
    public static boolean changed = true;
    public HashMap<Integer, Entity> entities; // <Server id, Entity>

    public EntityHandler(){
        entities = new HashMap<>();
    }

    /**
     * Add an entity to the world
     * @param e entity
     */
    public void add(int id, Entity e){
        entities.put(id, e);
        changed = true;
    }

    /**
     * Remove an entity from this
     * @param id server id
     */
    public void remove(int id){
        entities.remove(id);
        changed = true;
    }


    /**
     * Creates the player's entity
     */
    public void createPlayer(){
        player = new Entity();
        player.sprite = new Sprite("creature_player");
        player.xPosition = 0;
        player.yPosition = 0;
        add(0, player);
    }
}
