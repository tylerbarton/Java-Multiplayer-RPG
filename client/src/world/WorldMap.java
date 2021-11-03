package src.world;

/**
 * Represents the world map the player will be interacting with
 */
public class WorldMap {
    private EntityHandler entityHandler;
    private int dimX, dimY;
    private int[][] spriteData;
    private int[][] collisionData; // 1-impassable, 0-walkable


    public WorldMap(int width, int height){
        dimX = width;
        dimY = height;
        spriteData = new int[height][width];
        collisionData = new int[height][width];
        entityHandler = new EntityHandler();
    }

    /**
     * Converts screen position to world position
     * @param x screen pos
     * @param y screen pos
     * @return coordinates in world position
     */
    public static Coord ScreenToWorldCoord(int x, int y){
        int worldX = (int) Math.floor(x/16d);
        int worldY = (int) Math.floor(y/16d);
        return new Coord(worldX, worldY);
    }
}
