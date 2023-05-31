import java.awt.*;

public class DrawableMaze extends Maze {

    private int[][] startEndTiles;
    private TilePickerPanel drawingPanel;
    private KeyHandler keyH;

    public DrawableMaze(TilePickerPanel tilePickerPanel,  GamePanel gp, KeyHandler kh) {
        super(gp, 0);
        drawingPanel = tilePickerPanel;
        keyH = kh;

        for(int i = 0; i < mazeMap.length; i++) {
            for(int j = 0; j < mazeMap[0].length; j++) {
                mazeMap[i][j] = possibleTiles[16];
            }
        }

        startEndTiles = new int[2][2];


    }

    public void update() {


    }

    @Override
    public void draw(Graphics2D g2) {

        for(int i = 0; i < mazeMap.length; i++) {
            for (int j = 0; j < mazeMap[0].length; j++) {

                g2.drawImage(mazeMap[i][j].getImage(), (j * gamePanel.TILE_SIZE_COL), (i * gamePanel.TILE_SIZE_ROW), 20 * gamePanel.SCALE, 20 * gamePanel.SCALE, null);

            }
        }
    }

    public void setStartEndTiles(int[] startPos, int[] endPos) {
        startEndTiles[0] = startPos;
        startEndTiles[1] = endPos;
    }
}
