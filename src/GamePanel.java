import javax.swing.JPanel;
import java.awt.*;
import java.util.Arrays;

public class GamePanel extends JPanel implements Runnable {

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

    public int currentLevel;


    public GamePanel() {

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setDoubleBuffered(true);
        setFocusable(true);

        keyH = new KeyHandler();

        mazes = new Maze[4];

        for(int i = 0; i < 4; i++) {
            mazes[i] = new Maze(this, i + 1);
        }

        //drawableMaze = new DrawableMaze(this);

        addKeyListener(keyH);

        drawingPanel = new TilePickerPanel(this, player);

        currentLevel = 1;

        player = new Player(this, keyH);
        collisionChecker = new CollisionChecker(this);
        player.addCollisionChecker(collisionChecker);


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
                update();

                // Draw: draw the screen with the update information
                repaint();

                int[] currTileCoords = player.getCurrTileCoords();
                System.out.println(Arrays.toString(currTileCoords));
                //System.out.println(collisionChecker.checkTile());

                delta--;
            }
        }

    }

    public void update() {

        player.update();

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        mazes[0].draw(g2);

        player.draw(g2);
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

    public int getCurrentLevel() {
        return currentLevel;
    }

}
