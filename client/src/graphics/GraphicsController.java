package src.graphics;

import src.client.Config;

import java.util.Arrays;

import static src.client.Config.MAX_SPRITES;

/**
 * Controls the creation and calculations of visual information
 * @author Tyler Barton
 */
public class GraphicsController extends DrawingArea {
    private final int height;
    private final int width;
    public boolean active = true; // Changed when window focus changes to save pc power (wishful thinking haha)
    public int spriteCount = 0;
    public Sprite[] sprites;
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

        initWorld();
        //createTestScreen();
    }

    /**
     * Draws the initial game screen
     */
    private void initWorld(){
        // Set the background to grass
        fill(new Sprite("tile_2"));

        // Draws the player in the center of the screen
        // Hardcoded for speed right now
        addSpriteTile(new Sprite("creature_player"), 25, 16, SpriteLayer.ENTITY);
    }

    /**
     * Method used for drawing test
     */
    private void createTestScreen(){
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

        for (int y = 0; y < yCount; y++) {
            for (int x = 0; x < xCount; x++) {
                addSpriteTile(sprite, x, y, SpriteLayer.WORLD_MAP);
            }
        }
    }

    /**
     * Helper method to translate tile coordinate to local coordinates
     * @param sprite image
     * @param x x tile
     * @param y y tile
     */
    public void addSpriteTile(Sprite sprite, int x, int y, SpriteLayer layer){
        // Bound check
        if(x < 0 || y < 0) return;
        if(x > Config.CLIENT_WIDTH / Config.SPRITE_WIDTH ||
            y > Config.CLIENT_HEIGHT / Config.SPRITE_HEIGHT) return;

        if(layer.equals(SpriteLayer.ENTITY)){
            addEntity(sprite, x*sprite.width, y*sprite.height);
        } else {
            addWorld(sprite, x*sprite.width, y*sprite.height);
        }
    }

    /**
     * Draws a sprite's pixel data to the screen
     * @param sprite The sprite to be drawn
     * @param xOffset x screen position
     * @param yOffset y screen position
     * @param layer determines interactable or not
     */
    public void addSprite(Sprite sprite, int xOffset, int yOffset, SpriteLayer layer){
        // Book keeping
        int slot = nextSlot();
        if(slot == -1) return;
        spriteCount++;
        sprites[slot] = sprite;
        sprite.setLayer(layer);

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
        addSprite(sprite, xOffset, yOffset, SpriteLayer.ENTITY);
    }

    /**
     * Draws a sprite on the screen that cannot be interacted with.
     * @param sprite Image to be drawn
     * @param xOffset x position
     * @param yOffset y position
     */
    public void addWorld(Sprite sprite, int xOffset, int yOffset){
        sprite.interactable = false;
        addSprite(sprite, xOffset, yOffset, SpriteLayer.WORLD_MAP);
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