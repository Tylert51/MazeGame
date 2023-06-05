import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileReadWrite {

    public static void writeFile(ArrayList<String[][]> listOfCustomMazes) throws IOException {

        FileOutputStream fos = new FileOutputStream("src/mazes/custom/customMazes.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(listOfCustomMazes);

        oos.close();
        fos.close();

        System.out.println("Data saved");
    }

    public static ArrayList<String[][]> readFile() throws IOException, ClassNotFoundException {

        FileInputStream fi = new FileInputStream("src/mazes/custom/customMazes.txt");
        ObjectInputStream oi = new ObjectInputStream(fi);

        ArrayList<String[][]> listOfCustomMazes = new ArrayList<String[][]>();

        // Read objects
        listOfCustomMazes = (ArrayList<String[][]>) oi.readObject();

        oi.close();
        fi.close();

        System.out.println("Data loaded");

        return listOfCustomMazes;
    }



}