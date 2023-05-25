import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MazeDatabaseReader {

    public static String[][] getMazeIndexes(String fileName) {
        String[][] indx = new String[12][16];
        try {
            File mazeLevel = new File(fileName);
            Scanner reader = new Scanner(mazeLevel);

            int i = 0;

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] data = line.split(" ");

                indx[i] = data;
                i++;
            }
        }
        catch (FileNotFoundException noFile) {
            System.out.println("File not found!");
            return null;
        }
        return indx;
    }
}
