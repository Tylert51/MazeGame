import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //Screen Settings
    public final int ORIGINAL_TILE_COL = 16;
    public final int ORIGINAL_TILE_ROW = 20;
    public final int SCALE = 3;

    public final int TILE_SIZE_COL = ORIGINAL_TILE_COL * SCALE;
    public final int TILE_SIZE_ROW = ORIGINAL_TILE_ROW * SCALE;
    public final int MAX_SCREEN_COL = 16;
    public final int MAX_SCREEN_ROW = 12;
    public final int SCREEN_WIDTH = TILE_SIZE_COL * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE_ROW * MAX_SCREEN_ROW;

    public final double FPS = 60;

    private Thread gameThread;
    public KeyHandler keyH;
    public Player player;
    public Maze[] maze;

    public int currentLevel;


    public GamePanel() {

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setDoubleBuffered(true);
        setFocusable(true);

        keyH = new KeyHandler();
        player = new Player(this, keyH);

        maze = new Maze[4];

        for(int i = 0; i < 4; i++) {
            maze[i] = new Maze(this, i + 1);
        }

        addKeyListener(keyH);

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

        maze[0].draw(g2);

        player.draw(g2);
    }


}
