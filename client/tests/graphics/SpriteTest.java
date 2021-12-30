package tests.graphics;

import org.junit.jupiter.api.Test;
import src.graphics.Sprite;

public class SpriteTest {

    /**
     * Test the path return from strings
     */
    @Test
    void getFilePath(){
        String[] tests = {"player", "enemy"};

        String result1 = Sprite.getFilePath(tests[0]);
        String result2 = Sprite.getFilePath(tests[1]);

        System.out.println(result1);

        assert(result1.contains(tests[0]));
        assert(result2.contains(tests[1]));
    }


    /**
     * Test the pixels retrieved from an image
     */
    @Test
    void getPixels(){
        String test = "cactus";
        Sprite s = new Sprite(test);
        assert(s.getPixels() != null);
    }

    @Test
    void createSprite(){
        String test = "cactus";
        Sprite s = new Sprite(test);
        assert(s != null);
    }

    /**
     * Tests if a coordinate is contained in the sprite
     */
    @Test
    void hitTest(){
        Sprite s = new Sprite("black");
        assert s.containsPoint(14, 14);

        Sprite s_translated = new Sprite("black");
        s_translated.xPosition = 50;
        s_translated.yPosition = 50;
        assert s.containsPoint(14, 14);
    }

}
