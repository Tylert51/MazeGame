import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.awt.event.MouseListener;

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
    private Maze[] mazes;
    private DrawableMaze drawableMaze;
    private TilePickerPanel drawingPanel;

    CollisionChecker collisionChecker;

    private String currentLevel;
    private int gameMode;


    public GamePanel(int gameMode, String level) {

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setDoubleBuffered(true);
        setFocusable(true);

        addMouseListener(this);

        currentLevel = level;
        this.gameMode = gameMode;

        if(gameMode == 1) {
            keyH = new KeyHandler();

            mazes = new Maze[5];

            for(int i = 0; i < 4; i++) {
                mazes[i] = new Maze(this, i + 1);
            }

            addKeyListener(keyH);

            player = new Player(this, keyH);
            collisionChecker = new CollisionChecker(this);
            player.addCollisionChecker(collisionChecker);

        } else if (gameMode == 2) {

            KeyHandler keyHandler = new KeyHandler();

            drawingPanel = new TilePickerPanel(this, player);
            drawableMaze = new DrawableMaze(drawingPanel, this, keyHandler);


        }
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

                    }

                } else if (gameMode == 2) {
                    update();
                }


                repaint();
                System.out.println(gameMode);


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
            mazes[0].draw(g2);
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

    public Maze[] getMazes() {
        return mazes;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public int getGameMode() {
        return gameMode;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {


        if(gameMode == 2 && drawingPanel.isTileSelected()) {
            int[] mouseCoords = getMouseCoords(e);
            Tile[][] mazeMap = drawableMaze.getMazeMap();

            Tile selectedTile = drawingPanel.getSelectedTile();

            System.out.println(selectedTile);

            mazeMap[mouseCoords[0]] [mouseCoords[1]] = selectedTile;


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
}
