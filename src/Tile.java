import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Tile {

    private ArrayList<String> availableMoves;
    private BufferedImage image;
    private String fileName;
    private ArrayList<Rectangle> collisionArea;
    private boolean colliding;


    public Tile(String fileName) {
        this.fileName = fileName;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        collisionArea = new ArrayList<Rectangle>();
        availableMoves = new ArrayList<String>();

    }

    public ArrayList<String> getAvailableMoves() {
        return availableMoves;
    }

    public BufferedImage getImage() {

        return image;

    }

    public void addCollisionArea(Rectangle ... hitBoxes) {

        collisionArea.addAll(Arrays.asList(hitBoxes));

    }

    public ArrayList<Rectangle> getCollisionArea() {
        return collisionArea;
    }

    public void addAvailableMoves(String ... moves) {

        availableMoves.addAll(Arrays.asList(moves));

    }

    public String toString() {
        return fileName;
    }


}
