import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class DrawableMaze extends Maze {


    private TilePickerPanel drawingPanel;
    private KeyHandler keyH;


    public DrawableMaze(TilePickerPanel tilePickerPanel,  GamePanel gp, KeyHandler kh) {
        super(gp);
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
