import javax.swing.JPanel;
import java.awt.*;

public class TilePickerPanel extends JPanel implements Runnable {

    //Screen Settings
    public final int ORIGINAL_TILE_COL = 20;
    public final int ORIGINAL_TILE_ROW = 20;
    public final int SCALE = 3;

    public final int TILE_SIZE_COL = ORIGINAL_TILE_COL * SCALE;
    public final int TILE_SIZE_ROW = ORIGINAL_TILE_ROW * SCALE;
    public final int MAX_SCREEN_COL = 4;
    public final int MAX_SCREEN_ROW = 4;
    public final int SCREEN_WIDTH = TILE_SIZE_COL * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE_ROW * MAX_SCREEN_ROW;

    public final double FPS = 60;

    private Thread gameThread;
    public KeyHandler keyH;
    public Player player;
    public GamePanel gamePanel;
    public DrawableMaze drawableMaze;


    public TilePickerPanel(GamePanel panel, Player player) {

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setDoubleBuffered(true);
        setFocusable(true);

        gamePanel = panel;
        keyH = new KeyHandler();
        this.player = player;

        drawableMaze = new DrawableMaze(this, gamePanel, keyH);

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

        drawableMaze.update();

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        drawableMaze.draw(g2);

        //player.draw(g2);
    }



}
