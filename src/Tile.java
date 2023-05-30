import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Tile {

    private ArrayList<String> availableMoves;
    private BufferedImage image;
    private Rectangle collisionArea;
    private boolean colliding;

    public Tile(ArrayList<String> available, String fileName) {

        availableMoves = available;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        colliding = false;



    }

    public Tile(String fileName) {

        try {
            image = ImageIO.read(getClass().getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getAvailableMoves() {
        return availableMoves;
    }

    public BufferedImage getImage() {
        return image;
    }
}
