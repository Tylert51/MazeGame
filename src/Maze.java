import java.awt.*;

public class Maze {

    protected Tile[][] mazeMap;
    protected int level;
    protected GamePanel gamePanel;
    protected Tile[] possibleTiles;

    public Maze(GamePanel panel, Tile[][] map, int lvl) {
        gamePanel = panel;
        mazeMap = map;
        level = lvl;
        instantiatePossibleTiles();
    }

    public Maze(GamePanel panel, int lvl) {
        gamePanel = panel;
        level = lvl;

        mazeMap = new Tile[12][16];

        String[][] mazeIndx = MazeDatabaseReader.getMazeIndexes("src/mazes/level_one.txt");

        instantiatePossibleTiles();

        initializeMaze(mazeMap, mazeIndx);
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

                g2.drawImage(mazeMap[i][j].getImage(), (j * gamePanel.TILE_SIZE_COL), (i * gamePanel.TILE_SIZE_ROW), 20 * gamePanel.SCALE, 20 * gamePanel.SCALE, null);

            }
        }

        g2.drawImage(new Tile("/tiles/start_end/start.png").getImage(), 0, (mazeMap.length - 1) * gamePanel.TILE_SIZE_ROW, 20 * gamePanel.SCALE, 20 * gamePanel.SCALE, null);

        g2.drawImage(new Tile("/tiles/start_end/start.png").getImage(), (mazeMap[0].length - 1) * gamePanel.TILE_SIZE_COL, 0, 20 * gamePanel.SCALE, 20 * gamePanel.SCALE, null);

    }

    public void instantiatePossibleTiles() {
        possibleTiles = new Tile[17];

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
    }

    public void generateRandomTileSet(Tile[][] map) {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {

                int rand = (int) (Math.random() * 14);
                map[i][j] = possibleTiles[8];
            }
        }
    }

    public void initializeMaze(Tile[][] maze, String[][] indx) {

        for(int i = 0; i < mazeMap.length; i++) {
            for(int j = 0; j < mazeMap[0].length; j++) {
                int ind = Integer.parseInt(indx[i][j]);

            if(ind != 0) {
                maze[i][j] = possibleTiles[ind];
            } else {
                //int rand = (int) (Math.random() * 14);
                //maze[i][j] = possibleTiles[rand];

                maze[i][j] = possibleTiles[15];
            }

            }
        }

    }

}
