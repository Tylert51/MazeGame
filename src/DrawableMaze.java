import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class DrawableMaze extends Maze {


    private TilePickerPanel drawingPanel;
    private KeyHandler keyH;
    private static ArrayList<int[][]> listOfCustomMazes;

    public DrawableMaze(TilePickerPanel tilePickerPanel,  GamePanel gp, KeyHandler kh) {
        super(gp, 0);
        drawingPanel = tilePickerPanel;
        keyH = kh;

        for(int i = 0; i < mazeMap.length; i++) {
            for(int j = 0; j < mazeMap[0].length; j++) {
                mazeMap[i][j] = possibleTiles[16];
            }
        }



    }

    public void update() {



    }


//    @Override
//    public void draw(Graphics2D g2) {
//
//        for(int i = 0; i < mazeMap.length; i++) {
//            for (int j = 0; j < mazeMap[0].length; j++) {
//
//                g2.drawImage(mazeMap[i][j].getImage(), (j * gamePanel.TILE_SIZE_COL), (i * gamePanel.TILE_SIZE_ROW), 20 * gamePanel.SCALE, 20 * gamePanel.SCALE, null);
//
//            }
//        }
//
//        g2.drawImage(new Tile("/tiles/start_end/start.png").getImage(), 0, (mazeMap.length - 1) * gamePanel.TILE_SIZE_ROW, 20 * gamePanel.SCALE, 20 * gamePanel.SCALE, null);
//
//        g2.drawImage(new Tile("/tiles/start_end/start.png").getImage(), (mazeMap[0].length - 1) * gamePanel.TILE_SIZE_COL, 0, 20 * gamePanel.SCALE, 20 * gamePanel.SCALE, null);
//
//
//    }

    public void setListOfCustomMazes(ArrayList<int[][]> customMazes) {
        listOfCustomMazes = customMazes;
    }

    public ArrayList<int[][]> getListOfCustomMazes() {
        return listOfCustomMazes;
    }

    private void print2DArr(Tile[][] mazeMap) {
        for(Tile[] row : mazeMap) {
            for(Tile tile : row) {
                System.out.print(tile + " ") ;
            }
            System.out.println();
        }
    }

    public void printMazeMap() {
        print2DArr(mazeMap);
    }


}
