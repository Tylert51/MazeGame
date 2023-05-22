import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //Screen Settings
    public final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    public final int SCALE = 3;

    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; //48x48 tile
    public final int MAX_SCREEN_COL = 16;
    public final int MAX_SCREEN_ROW = 12;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 pixels

    public final double FPS = 60;

    private Thread gameThread;
    public KeyHandler keyH;
    public Player player;


    //temp
    int x = 100;
    int y = 100;
    int speed = 4;

    public GamePanel() {

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setDoubleBuffered(true);
        addKeyListener(keyH);
        setFocusable(true);

        keyH = new KeyHandler();
        player = new Player(this, keyH);

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

        //player.update();


        if(keyH.isUpPressed()) {
            y -= speed;

        }
        if (keyH.isDownPressed()) {
            y += speed;

        }
        if (keyH.isLeftPressed()) {
            x -= speed;

        }
        if (keyH.isRightPressed()) {
            x += speed;

        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        player.draw(g2);
    }


}
