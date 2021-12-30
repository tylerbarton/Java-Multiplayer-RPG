package src.graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Represents a drawable image on the screen
 * @author Tyler Barton
 * @implNote Adapted from Assignment 1.0
 */
public class Sprite extends DrawingArea{
    private static final String resPath = "\\client\\Images\\Tiles\\";

    public String filePath;
    public int xPosition, yPosition;
    public int width, height;
    public int maxWidth, maxHeight;
    public float scale = 1.0f;
    private BufferedImage image;
    private ImageIcon imageIcon;

    /**
     * Create a sprite with
     * @param img
     */
    public Sprite(String img){this(img, 1.0f);}

    /**
     * Load an image from a path and scale the image.
     * @param img image name that will be appended to the resources path
     * @param scale Scale of the image before placement
     */
    public Sprite(String img, float scale){ this(img, scale, 0, 0); }

    /**
     * Creates a sprite with a transform
     * @param img Image
     * @param scale Scale of the image
     * @param x X position on the panel
     * @param y Y position on the panel
     */
    public Sprite(String img, float scale, int x, int y){
        try{
            // Load the image
            image = ImageIO.read(new File(getFilePath(img)));

            // Get the dimensions of the loaded image
            maxWidth = width = image.getWidth();
            maxHeight = height = image.getHeight();
        } catch(Exception ignored) {
            System.out.println(getFilePath(img));
            ignored.printStackTrace();
        }
    }

    /**
     * @return the resources path for sprites
     */
    public static String getFilePath(String img){
        String localDir = System.getProperty("user.dir");
        return localDir + resPath + img + ".png";
    }

    /**
     * Changes the sprites dimensions and returns the new sprite
     * @param width Horizontal pixel count
     * @param height Vertical pixel count
     * @return The resized sprite
     */
    public Image scale(int width, int height) {
        this.width = width;
        this.height = height;

        if (image != null) {
            //image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }
        return image;
    }

    /**
     * @return a new instance of a button
     * @deprecated Replaced by MouseListener and position check
     */
    public JButton getButton(){
        JButton button = new JButton();
        button.setIcon(imageIcon);
        button.setBounds(xPosition,yPosition,width, height);
        return button;
    }

    /**
     * src: https://newbedev.com/java-get-pixel-array-from-image
     * @return the pixel data associated with the image
     * ref: stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
     */
    public int[] getPixels(){
        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        int[][] result = new int[height][width];

        final int pixelLength = 4;
        for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
            int argb = 0;
            argb += ((int) pixels[pixel + 1] & 0xff); // blue
            argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
            argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
            result[row][col] = argb;
            col++;
            if (col == width) {
                col = 0;
                row++;
            }
        }

        // ref: https://stackoverflow.com/questions/8935367/convert-a-2d-array-into-a-1d-array
        int[] array = Stream.of(result)
                .flatMapToInt(IntStream::of)
                .toArray();

        return array;
    }

    /**
     * Get the bytes of an image file
     * @param path Path of the file
     * @return Bytes of the file at the path
     */
    private static byte[] readFile(String path) {
        try {
            File file = new File(path);
            int len = (int) file.length();
            byte[] bytes = new byte[len];

            DataInputStream datainputstream = new DataInputStream(
                    new BufferedInputStream(new FileInputStream(path)));
            datainputstream.readFully(bytes, 0, len);
            datainputstream.close();
            return bytes;
        } catch (Exception ignored) {}
        return null;
    }


    /**
     *
     * @param x
     * @param y
     * @return True if the point is contained
     */
    public boolean containsPoint(int x, int y){
        int bottomX = this.xPosition;
        int topX = this.xPosition+width;
        int bottomY = this.yPosition;
        int topY = this.yPosition+width;
        return !(x > topX || x < bottomX ||
                y > topY || y < bottomY);
    }
}
