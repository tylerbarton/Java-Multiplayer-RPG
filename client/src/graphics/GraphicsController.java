package src.graphics;

import src.client.Config;

import java.util.Arrays;

import static src.client.Config.MAX_SPRITES;

/**
 * Controls the creation and calculations of visual information
 * @author Tyler Barton
 */
public class GraphicsController extends DrawingArea {
    public enum SPRITE_LAYER {
        WORLDMAP,
        ENTITY,
        INTERFACE
    }
    public boolean active = true; // Changed when window focus changes to save pc power
    private final int width;
    public int spriteCount = 0;
    public Sprite[] sprites;
    private final int height;
    public int[] pixelData; // Main pixel data that will be committed to the screen in GameApplet

    /**
     * Constructor
     * @param width Width of the screen
     * @param height Height of the screen
     */
    public GraphicsController(int width, int height){
        this.width = width;
        this.height = height;
        this.pixelData = new int[width * height];
        this.sprites = new Sprite[MAX_SPRITES];

        createTestScreen();
    }

    /**
     * Method used for drawing test
     */
    private void createTestScreen(){
        // Test
        //fill(0xFFFFAAFF);

        fill(new Sprite("tile_2"));

//        addSpriteTile(new Sprite("tile_1"), 5, 5);
//
//        addSpriteTile(new Sprite("tile_1"), 5, 1);
//        addSpriteTile(new Sprite("tile_1"), 3, 1);
//        addSpriteTile(new Sprite("tile_1"), 1, 1);
        addSpriteTile(new Sprite("tile_1"), 25, 25);

        //addEntity(new Sprite("tile_2"), 10, 10);
        //addEntity(new Sprite("tile_1"), 263, 150);
    }

    /**
     * Fills the screen with black. Use '0x00FFAAFF' for debugging
     * @param color Value to be filled with.
     */
    public void fill(int color){
        Arrays.fill(this.pixelData, color);
    }

    /**
     * Fills the background with a repeating sprite
     * @param sprite image
     */
    public void fill(Sprite sprite){
        int xCount = this.width / sprite.width;
        int yCount = this.height / sprite.height;

        System.out.println(xCount + "," + yCount);

        for (int y = 0; y < yCount; y++) {
            for (int x = 0; x < xCount; x++) {
                addSpriteTile(sprite, x, y);
            }
        }
    }

    /**
     * Helper method to translate tile coordinate to local coordinates
     * @param sprite image
     * @param x x tile
     * @param y y tile
     */
    public void addSpriteTile(Sprite sprite, int x, int y){
        addEntity(sprite, x*sprite.width, y*sprite.height);
    }

    /**
     * Draws a sprite's pixel data to the screen
     * @param sprite The sprite to be drawn
     * @param xOffset x screen position
     * @param yOffset y screen position
     * @param layer determines interactable or not
     */
    public void addSprite(Sprite sprite, int xOffset, int yOffset, SPRITE_LAYER layer){
        // Book keeping
        int slot = nextSlot();
        if(slot == -1) return;
        spriteCount++;
        sprites[slot] = sprite;

        // Sprite Information
        int sWidth = sprite.width;
        int sHeight = sprite.height;
        int[] pixels = sprite.getPixels();

        // Drawing
        for (int y=0; y<sHeight; y++)
        {
            int yPix = y + yOffset;
            for (int x=0; x<sWidth; x++)
            {
                int xPix = x + xOffset;
                int value = pixels[x + (y * sWidth)];

                if((value&0xFF000000) != 0){
                    // Out of range error handling
                    if(xPix + (yPix * width) < width*height){
                        this.pixelData[xPix + (yPix * width)] = (value);
                    }
                }
            }
        }
    }

    /**
     * Adds a sprite to the entity layer
     * @param sprite visual to add
     */
    public void addEntity(Sprite sprite, int xOffset, int yOffset){
        sprite.interactable = true;
        addSprite(sprite, xOffset, yOffset, SPRITE_LAYER.ENTITY);
    }

    /**
     * Draws a sprite on the screen that cannot be interacted with.
     * @param sprite Image to be drawn
     * @param xOffset x position
     * @param yOffset y position
     */
    public void addWorld(Sprite sprite, int xOffset, int yOffset){
        sprite.interactable = false;
        addSprite(sprite, xOffset, yOffset, SPRITE_LAYER.WORLDMAP);
    }

    /**
     * Finds the lowest available player slot between 1 and {@link Config#MAX_SPRITES}.
     * @return the available next slot, else -1
     */
    private int nextSlot() {
        for (int i = 0; i < Config.MAX_SPRITES; i++) {
            if(sprites[i] == null){
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if a sprite is contained in this coordinate
     * @param x screen position
     * @param y screen position
     * @return True if hit, else false
     */
    public Sprite containsSprite(int x, int y){
        for (int i = 0; i < spriteCount; i++) {
            if(sprites[i].containsPoint(x,y)){
                return sprites[i];
            }
        }
        return null;
    }
}