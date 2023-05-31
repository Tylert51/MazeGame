import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileReadWrite {

    public void writeFile() throws IOException {

        Tile[] possibleTiles = new Tile[17];

        possibleTiles[0] = new Tile("/tiles/one/bottom.png");
        possibleTiles[1] = new Tile("/tiles/one/left.png");
        possibleTiles[2] = new Tile("/tiles/one/right.png");
        possibleTiles[3] = new Tile("/tiles/one/up.png");

        possibleTiles[4] = new Tile("/tiles/two/bottom_left.png");
        possibleTiles[5] = new Tile("/tiles/two/bottom_right.png");
        possibleTiles[6] = new Tile("/tiles/two/top_left.png");
        possibleTiles[7] = new Tile("/tiles/two/top_right.png");

        possibleTiles[8] = new Tile("/tiles/three/down_open.png");
        possibleTiles[9] = new Tile("/tiles/three/left_open.png");
        possibleTiles[10] = new Tile("/tiles/three/right_open.png");
        possibleTiles[11] = new Tile("/tiles/three/up_open.png");

        possibleTiles[12] = new Tile("/tiles/straight/right.png");
        possibleTiles[13] = new Tile("/tiles/straight/up.png");

        possibleTiles[14] = new Tile("/tiles/start_end/start.png");

        possibleTiles[15] = new Tile("/tiles/empty.png");

        possibleTiles[16] = new Tile("/tiles/two/top_left_blue.png");


        FileOutputStream fos = new FileOutputStream("possibleTiles.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(possibleTiles);

        oos.close();
        fos.close();

        System.out.println("Data saved");
    }

    public Tile[] readPossibleTiles() throws IOException, ClassNotFoundException {

        FileInputStream fi = new FileInputStream("possibleTiles.txt");
        ObjectInputStream oi = new ObjectInputStream(fi);

        // Read objects
        Tile[] possibleTiles = (Tile[]) oi.readObject();

        oi.close();
        fi.close();

        System.out.println("Data loaded");

        return possibleTiles;
    }

}