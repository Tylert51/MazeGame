import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {

    public int x;
    public int y;
    public int speed;

    public GamePanel gamePanel;
    public KeyHandler keyH;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public Player (GamePanel gp, KeyHandler kh) {

        gamePanel = gp;
        keyH = kh;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {

        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));

            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));

            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));

            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update() {

        if(keyH.isUpPressed()) {
            direction = "up";
            y -= speed;

        }
        if (keyH.isDownPressed()) {
            direction = "down";
            y += speed;

        }
        if (keyH.isLeftPressed()) {
            direction = "left";
            x -= speed;

        }
        if (keyH.isRightPressed()) {
            direction = "right";
            x += speed;

        }

    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        //g2.fillRect(x, y, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE);

        BufferedImage img = null;

        switch (direction) {
            case "up":
                img = up1;
                break;

            case "down":
                img = down1;
                break;

            case "left":
                img = left1;
                break;

            case "right":
                img = right1;
                break;
        }

        g2.drawImage(img, x, y, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE, null);




    }
}
