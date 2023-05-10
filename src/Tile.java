import javax.swing.*;
import java.util.ArrayList;

public class Tile {

    private ArrayList<String> availableMoves;
    private ImageIcon image;

    public Tile(ArrayList<String> available, String fileName) {

        availableMoves = available;
        image = new ImageIcon(getClass().getResource(fileName));

    }

    public ArrayList<String> getAvailableMoves() {
        return availableMoves;
    }

    public ImageIcon getImage() {
        return image;
    }
}
