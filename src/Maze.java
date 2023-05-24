import java.awt.*;

public class Maze {

    private Tile[][] mazeMap;
    private int level;
    private GamePanel gamePanel;

    public Maze(GamePanel panel, Tile[][] map, int lvl) {
        gamePanel = panel;
        mazeMap = map;
        level = lvl;
    }

    public Maze(GamePanel panel, int lvl) {
        gamePanel = panel;
        level = lvl;

        mazeMap = new Tile[16][20];

        for(int i = 0; i < 16; i++) {
            for(int j = 0; j < 20; j++) {
                mazeMap[i][j] = new Tile("/tiles/straight.png");
            }
        }
    }

    public Tile[][] getMazeMap() {
        return mazeMap;
    }

    public void setMazeMap(Tile[][] map) {
        mazeMap = map;
    }

    public int getLevel() {
        return level;
    }

    public void draw(Graphics2D g2) {

        for(int i = 0; i < mazeMap.length; i++) {
            for(int j = 0; j < mazeMap[0].length; j++) {
                g2.drawImage(mazeMap[i][j].getImage(), i * gamePanel.TILE_SIZE_COL, j * gamePanel.TILE_SIZE_ROW, 16 * gamePanel.SCALE, 20 * gamePanel.SCALE, null);
            }
        }
    }

}
