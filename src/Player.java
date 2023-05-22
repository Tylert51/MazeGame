import java.awt.*;

public class Player {

    public int x;
    public int y;
    public int speed;

    public GamePanel gamePanel;
    public KeyHandler keyH;

    public Player (GamePanel gp, KeyHandler kh) {

        gamePanel = gp;
        keyH = kh;

        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
    }

    public void update() {

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

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(x, y, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE);

    }
}
