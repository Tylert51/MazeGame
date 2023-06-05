import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.MouseListener;
import static javax.swing.JOptionPane.showMessageDialog;

public class GamePanel extends JPanel implements Runnable, MouseListener {

    //Screen Settings
    public final int ORIGINAL_TILE_COL = 20;
    public final int ORIGINAL_TILE_ROW = 20;
    public final int SCALE = 3;

    public final int TILE_SIZE_COL = ORIGINAL_TILE_COL * SCALE;
    public final int TILE_SIZE_ROW = ORIGINAL_TILE_ROW * SCALE;
    public final int MAX_SCREEN_COL = 16;
    public final int MAX_SCREEN_ROW = 12;
    public final int SCREEN_WIDTH = TILE_SIZE_COL * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE_ROW * MAX_SCREEN_ROW;

    public final double FPS = 144;

    private Thread gameThread;
    private KeyHandler keyH;
    private Player player;
    private Maze maze;
    private DrawableMaze drawableMaze;
    private TilePickerPanel drawingPanel;

    CollisionChecker collisionChecker;

    private String currentLevel;
    private int gameMode;
    private boolean finished;

    private int currStraight, currOne, currTwo, currThree;

    private static ArrayList<String[][]> listOfCustomMazes;


    public GamePanel(int gameMode, String level) throws IOException, ClassNotFoundException {



        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setDoubleBuffered(true);
        setFocusable(true);

        addMouseListener(this);

        listOfCustomMazes = FileReadWrite.readFile();


        currentLevel = level;
        this.gameMode = gameMode;
        finished = false;

        if(gameMode == 1) {
            keyH = new KeyHandler();

            maze = new Maze(this);

            addKeyListener(keyH);

            player = new Player(this, keyH);
            collisionChecker = new CollisionChecker(this);
            player.addCollisionChecker(collisionChecker);

        } else if (gameMode == 2) {

            KeyHandler keyHandler = new KeyHandler();

            drawingPanel = new TilePickerPanel(this, player);
            drawableMaze = new DrawableMaze(drawingPanel, this, keyHandler);

            resetTileValues();
        }
    }

    public void resetTileValues() {
        currStraight = 1;
        currOne = 1;
        currTwo = 1;
        currThree = 1;
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;



        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if(delta >= 1) {

                // Updates information such character positions

                if(gameMode == 1) {

                    int[] coords = player.getCurrTileCoords();

                    if (coords == null) {

                        if (player.getDirection().equals("up")) {
                            player.move(0, -2);
                        } else if (player.getDirection().equals("down")) {
                            player.move(0, 2);
                        } else if (player.getDirection().equals("left")) {
                            player.move(-2, 0);
                        } else if (player.getDirection().equals("right")) {
                            player.move(2, 0);
                        }

                    } else {

                        update();

                        int[] end = {0, 15};


                        if(Arrays.equals(coords, end) && !finished) {
                            showMessageDialog(this, "Congrats!!!");
                            finished = true;
                        }

                    }

                } else if (gameMode == 2) {
                    update();
                }


                repaint();



                //System.out.println(Arrays.toString(coords));
                //System.out.println(collisionChecker.checkTile(player));

                delta--;
            }
        }

    }

    public void update() {
        if(gameMode == 1) {
            player.update();
        } else if (gameMode == 2) {
            drawableMaze.update();
        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;


        if(gameMode == 1) {
            maze.draw(g2);
            player.draw(g2);


        } else if (gameMode == 2) {

            drawableMaze.draw(g2);


        }


    }

    public TilePickerPanel getDrawingPanel() {
        return drawingPanel;
    }

    public Player getPlayer() {
        return player;
    }

    public Maze getMaze() {
        return maze;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setListOfCustomMazes(ArrayList<String[][]> customMazes) {
        listOfCustomMazes = customMazes;
    }

    public ArrayList<String[][]> getListOfCustomMazes() {
        return listOfCustomMazes;
    }

    public void addCustomMaze (String[][] customMaze) {
        listOfCustomMazes.add(customMaze);
    }

    public void clearCustomMazes() {
        listOfCustomMazes.clear();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {


        if(gameMode == 2)  {

            int[] mouseCoords = getMouseCoords(e);
            Tile[][] mazeMap = drawableMaze.getMazeMap();
            Tile[] possibleTiles = Maze.getPossibleTiles();

            if(drawingPanel.isTileSelected()) {


                Tile selectedTile = drawingPanel.getSelectedTile();
                selectedTile = possibleTiles[getUniversalTileIndx(selectedTile, possibleTiles)];

                mazeMap[mouseCoords[0]][mouseCoords[1]] = selectedTile;

                resetTileValues();

            }
            if(drawingPanel.isRotateSelected() && !(mazeMap[mouseCoords[0]] [mouseCoords[1]].equals(possibleTiles[15]) || mazeMap[mouseCoords[0]] [mouseCoords[1]].equals(possibleTiles[16]))) {


                Tile[] straightTiles = {possibleTiles[12], possibleTiles[13]};

                Tile[] oneTiles = {possibleTiles[1], possibleTiles[3], possibleTiles[2], possibleTiles[0]};

                Tile[] twoTiles = {possibleTiles[6], possibleTiles[7], possibleTiles[5], possibleTiles[4]};

                Tile[] threeTiles = {possibleTiles[10], possibleTiles[8], possibleTiles[9], possibleTiles[11]};

                Tile currTile = mazeMap[mouseCoords[0]][mouseCoords[1]];



                if (idxOfTile(straightTiles, currTile) != -1) {

                    mazeMap[mouseCoords[0]][mouseCoords[1]] = straightTiles[currStraight];

                    currStraight += 1;
                    currStraight %= 2;

                } else if (idxOfTile(oneTiles, currTile) != -1) {

                    mazeMap[mouseCoords[0]][mouseCoords[1]] = oneTiles[currOne];

                    currOne += 1;
                    currOne %= 4;

                } else if (idxOfTile(twoTiles, currTile) != -1) {

                    mazeMap[mouseCoords[0]][mouseCoords[1]] = twoTiles[currTwo];

                    currTwo += 1;
                    currTwo %= 4;

                } else if (idxOfTile(threeTiles, currTile) != -1) {

                    mazeMap[mouseCoords[0]][mouseCoords[1]] = threeTiles[currThree];

                    currThree += 1;
                    currThree %= 4;

                }
            }


            if(drawingPanel.isSaveSelected()) {
                String[][] mazeMapIndx = convertTileToIndx(mazeMap, possibleTiles);

                listOfCustomMazes.add(mazeMapIndx);

                //clearCustomMazes();

                try {
                    FileReadWrite.writeFile(listOfCustomMazes);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                //print2DArr(mazeMapIndx);


            }
        }




    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public int[] getMouseCoords(MouseEvent e) {

        int[] coords = new int[2];

        int xCoord = e.getY() / TILE_SIZE_ROW;
        int yCoord = e.getX() / TILE_SIZE_COL;

        coords[0] = xCoord;
        coords[1] = yCoord;

        return coords;

    }

    private void print2DArr(Tile[][] mazeMap) {
        for(Tile[] row : mazeMap) {
            for(Tile tile : row) {
                System.out.print(tile + " ") ;
            }
            System.out.println();
        }
    }

    private void print2DArr(String[][] mazeMap) {
        for(String[] row : mazeMap) {
            for(String num : row) {
                System.out.print(num + " ") ;
            }
            System.out.println();
        }
    }

    private int idxOfTile (Tile[] tiles, Tile search) {
        for(int i = 0; i < tiles.length; i++) {
            Tile tile = tiles[i];

            if(tile.equals(search)) {
                return i;
            }
        }

        return -1;
    }

    private String[][] convertTileToIndx(Tile[][] maze, Tile[] possibleTiles) {
        String[][] mazeIndexes = new String[maze.length][maze[0].length];

        for(int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                mazeIndexes[i][j] = getUniversalTileIndx(maze[i][j], possibleTiles) + "";
            }
        }

        return mazeIndexes;
    }

    private int getUniversalTileIndx (Tile tile, Tile[] possibleTiles) {
        int tempIndx = idxOfTile(possibleTiles, tile);

        if(tempIndx != -1 && tempIndx != 16) {
            return tempIndx;
        }

        if(tile.getFileName().equals("/tiles/draw/straight.png")) {
            return 12;

        } else  if(tile.getFileName().equals("/tiles/draw/one.png")) {
            return 1;

        } else  if(tile.getFileName().equals("/tiles/draw/two.png")) {
            return 6;

        } else  if(tile.getFileName().equals("/tiles/draw/three.png")) {
            return 10;
        }

        return 15;

    }
}
