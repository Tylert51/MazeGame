import java.awt.*;
import java.io.IOException;


public class Maze {

    protected Tile[][] mazeMap;

    protected GamePanel gamePanel;
    protected static Tile[] possibleTiles;

    public Maze(GamePanel panel) {
        gamePanel = panel;


        mazeMap = new Tile[12][16];

        try {
            instantiateBaseTiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (panel.getGameMode() == 1) {
            String lvl = gamePanel.getCurrentLevel();

            String[][] mazeIndx;

            if(lvl.contains("p")) {

                mazeIndx = MazeDatabaseReader.getMazeIndexes("src/mazes/level_" + lvl.substring(1) + ".txt");

            } else {

                mazeIndx = gamePanel.getListOfCustomMazes().get(Integer.parseInt(lvl.substring(1)) - 1);

            }

            initializeMaze(mazeMap, mazeIndx);
        }




    }

    public Tile[][] getMazeMap() {
        return mazeMap;
    }

    public void setMazeMap(Tile[][] map) {
        mazeMap = map;
    }



    public static Tile[] getPossibleTiles() {
        return possibleTiles;
    }

    public void setPossibleTiles(Tile[] possibleTiles) {
        this.possibleTiles = possibleTiles;
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


                maze[i][j] = possibleTiles[ind];


            }
        }

    }

    public void instantiateBaseTiles() throws IOException, ClassNotFoundException {

        possibleTiles = new Tile[17];

        Rectangle rectangle1 = new Rectangle();
        Rectangle rectangle2 = new Rectangle();
        Rectangle rectangle3 = new Rectangle();

        // One Line
        possibleTiles[0] = new Tile("/tiles/one/bottom.png");
        rectangle1.setBounds(0 * getScaleFactor(), 19 * getScaleFactor(), 20 * getScaleFactor(), 1 * getScaleFactor());
        possibleTiles[0].addCollisionArea(rectangle1);
        possibleTiles[0].addAvailableMoves("left", "right", "up");


        possibleTiles[1] = new Tile("/tiles/one/left.png");
        rectangle1.setBounds(0 * getScaleFactor(), 0 * getScaleFactor(), 1 * getScaleFactor(), 20 * getScaleFactor());
        possibleTiles[1].addCollisionArea(rectangle1);
        possibleTiles[1].addAvailableMoves("down", "right", "up");


        possibleTiles[2] = new Tile("/tiles/one/right.png");
        rectangle1.setBounds(19 * getScaleFactor(), 0 * getScaleFactor(), 1 * getScaleFactor(), 20 * getScaleFactor());
        possibleTiles[2].addCollisionArea(rectangle1);
        possibleTiles[2].addAvailableMoves("left", "down", "up");


        possibleTiles[3] = new Tile("/tiles/one/up.png");
        rectangle1.setBounds(0 * getScaleFactor(), 0 * getScaleFactor(), 20 * getScaleFactor(), 1 * getScaleFactor());
        possibleTiles[3].addCollisionArea(rectangle1);
        possibleTiles[3].addAvailableMoves("left", "right", "down");


        // Two Lines
        possibleTiles[4] = new Tile("/tiles/two/bottom_left.png");
        rectangle1.setBounds(possibleTiles[1].getCollisionArea().get(0));
        rectangle2.setBounds(possibleTiles[0].getCollisionArea().get(0));
        possibleTiles[4].addCollisionArea(rectangle1, rectangle2);
        possibleTiles[4].addAvailableMoves("up", "right");


        possibleTiles[5] = new Tile("/tiles/two/bottom_right.png");
        rectangle1.setBounds(possibleTiles[0].getCollisionArea().get(0));
        rectangle2.setBounds(possibleTiles[2].getCollisionArea().get(0));
        possibleTiles[5].addCollisionArea(rectangle1, rectangle2);
        possibleTiles[5].addAvailableMoves("left", "up");


        possibleTiles[6] = new Tile("/tiles/two/top_left.png");
        rectangle1.setBounds(possibleTiles[3].getCollisionArea().get(0));
        rectangle2.setBounds(possibleTiles[1].getCollisionArea().get(0));
        possibleTiles[6].addCollisionArea(rectangle1, rectangle2);
        possibleTiles[6].addAvailableMoves("down", "right");


        possibleTiles[7] = new Tile("/tiles/two/top_right.png");
        rectangle1.setBounds(possibleTiles[2].getCollisionArea().get(0));
        rectangle2.setBounds(possibleTiles[3].getCollisionArea().get(0));
        possibleTiles[7].addCollisionArea(rectangle1, rectangle2);
        possibleTiles[7].addAvailableMoves("left", "down");



        // Three Tiles
        possibleTiles[8] = new Tile("/tiles/three/down_open.png");
        rectangle1.setBounds(possibleTiles[1].getCollisionArea().get(0));
        rectangle2.setBounds(possibleTiles[2].getCollisionArea().get(0));
        rectangle2.setBounds(possibleTiles[3].getCollisionArea().get(0));
        possibleTiles[8].addCollisionArea(rectangle1, rectangle2, rectangle3);
        possibleTiles[8].addAvailableMoves("down");


        possibleTiles[9] = new Tile("/tiles/three/left_open.png");
        rectangle1.setBounds(possibleTiles[0].getCollisionArea().get(0));
        rectangle2.setBounds(possibleTiles[2].getCollisionArea().get(0));
        rectangle2.setBounds(possibleTiles[3].getCollisionArea().get(0));
        possibleTiles[9].addCollisionArea(rectangle1, rectangle2, rectangle3);
        possibleTiles[9].addAvailableMoves("left");


        possibleTiles[10] = new Tile("/tiles/three/right_open.png");
        rectangle1.setBounds(possibleTiles[0].getCollisionArea().get(0));
        rectangle2.setBounds(possibleTiles[1].getCollisionArea().get(0));
        rectangle2.setBounds(possibleTiles[3].getCollisionArea().get(0));
        possibleTiles[10].addCollisionArea(rectangle1, rectangle2, rectangle3);
        possibleTiles[10].addAvailableMoves("right");


        possibleTiles[11] = new Tile("/tiles/three/up_open.png");
        rectangle1.setBounds(possibleTiles[0].getCollisionArea().get(0));
        rectangle2.setBounds(possibleTiles[1].getCollisionArea().get(0));
        rectangle2.setBounds(possibleTiles[2].getCollisionArea().get(0));
        possibleTiles[11].addCollisionArea(rectangle1, rectangle2, rectangle3);
        possibleTiles[11].addAvailableMoves("up");



        // Straight Tiles
        possibleTiles[12] = new Tile("/tiles/straight/right.png");
        rectangle1.setBounds(possibleTiles[0].getCollisionArea().get(0));
        rectangle2.setBounds(possibleTiles[3].getCollisionArea().get(0));
        possibleTiles[12].addCollisionArea(rectangle1, rectangle2);
        possibleTiles[12].addAvailableMoves("right", "left");


        possibleTiles[13] = new Tile("/tiles/straight/up.png");
        rectangle1.setBounds(possibleTiles[1].getCollisionArea().get(0));
        rectangle2.setBounds(possibleTiles[2].getCollisionArea().get(0));
        possibleTiles[13].addCollisionArea(rectangle1, rectangle2);
        possibleTiles[13].addAvailableMoves("up", "down");


        // Extraneous Tiles
        possibleTiles[14] = new Tile("/tiles/start_end/start.png");
        rectangle1.setBounds(1 * getScaleFactor(), 1 * getScaleFactor(), 18 * getScaleFactor(), 18 * getScaleFactor());
        possibleTiles[14].addCollisionArea(rectangle1);

        possibleTiles[15] = new Tile("/tiles/empty.png");
        possibleTiles[15].addAvailableMoves("up", "down", "left", "right");

        possibleTiles[16] = new Tile("/tiles/two/top_left_gray.png");

    }

    public int getScaleFactor() {
        return gamePanel.SCALE;
    }


}
