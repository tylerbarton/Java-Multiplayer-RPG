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
    public int[] pixelData; // Main pixel data that will be committed to the screen
    public int spriteCount = 0;
    public Sprite[] sprites;
    private int width, height;

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
        fill(0x00FFAAFF);
        addSprite(new Sprite("amazon"), 263, 150);
        addSprite(new Sprite("black"), getCenterX(), getCenterY());
    }

    /**
     * Fills the screen with black. Use '0x00FFAAFF' for debugging
     * @param value Value to be filled with.
     */
    public void fill(int value){
        Arrays.fill(this.pixelData, value);
    }

    /**
     * Adds a sprite to the
     * @param sprite visual to add
     */
    public void addSprite(Sprite sprite, int xOffset, int yOffset){
        // Book keeping
        int slot = nextSlot();
        if(slot == -1) return;
        spriteCount++;
        sprites[slot] = sprite;

        int sWidth = sprite.width;
        int sHeight = sprite.height;
        int[] pixels = sprite.getPixels();

        for (int y=0; y<sHeight; y++)
        {
            int yPix = y + yOffset;
            for (int x=0; x<sWidth; x++)
            {
                int xPix = x + xOffset;
                int value = pixels[x + (y * sWidth)];

                // Ignore transparent values
                if((value & 0xff000000) == 0){
                    continue;
                }

                // Draw the pixel data to the buffer
                this.pixelData[xPix + (yPix * width)] = value;
            }
        }
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
    public boolean containsSprite(int x, int y){
        for (int i = 0; i < spriteCount; i++) {
            if(sprites[i].containsPoint(x,y)){
                return true;
                // TODO: return the sprite instead
            }
        }
        return false;
    }

}
