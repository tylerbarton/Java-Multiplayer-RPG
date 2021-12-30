package src.graphics;

/**
 * Represents the bounding region where graphics can be drawn
 * Taken from my cs242-fa21-assignment 1.0
 * @author Tyler Barton
 */
public class DrawingArea extends Node {
    private static int width;
    private static int height;
    private static int bottomX;
    private static int centerX;
    private static int topX;
    private static int bottomY;
    private static int centerY;
    private static int topY;

    /**
     * Initializes the Drawing Area with an assumed 0,0 position
     * @param width horizontal count of pixels
     * @param height vertical count of pixels
     */
    public static void init(int width, int height) {
        DrawingArea.width = width;
        DrawingArea.height = height;
        setDrawingArea(width, height, 0, 0);
    }

    /**
     * Sets the position bounds an item can be drawn on
     * @param bottomX The lower bound horizontal position on the screen
     * @param bottomY The lower bound vertical position on the screen
     * @param topX The upper bound horizontal position on the screen
     * @param topY The upper bound vertical position on the screen
     */
    public static void setDrawingArea(int bottomX, int bottomY, int topX, int topY) {
        // Bound check
        if (topX < 0) topX = 0;
        if (topY < 0) topY = 0;
        if (bottomX > getWidth()) bottomX = getWidth();
        if (bottomY > getHeight()) bottomY = getHeight();

        // Assign variables
        DrawingArea.topX = topX;
        DrawingArea.topY = topY;
        DrawingArea.bottomX = bottomX;
        DrawingArea.bottomY = bottomY;
        DrawingArea.centerY = DrawingArea.getWidth() / 2;
        DrawingArea.centerX = DrawingArea.getHeight() / 2;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static int getBottomX() {
        return bottomX;
    }

    public static int getCenterX() {
        return centerX;
    }

    public static int getTopX() {
        return topX;
    }

    public static int getBottomY() {
        return bottomY;
    }

    public static int getCenterY() {
        return centerY;
    }

    public static int getTopY() {
        return topY;
    }
}

