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
    public BufferedImage currImg;

    public int spriteCounter;
    public boolean spriteNum1;

    public Player (GamePanel gp, KeyHandler kh) {

        gamePanel = gp;
        keyH = kh;

        spriteCounter = 0;
        spriteNum1 = true;

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
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));

            currImg = down1;


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update() {

        if(keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed()) {

            if (keyH.isUpPressed()) {
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

            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum1 = !spriteNum1;
                spriteCounter = 0;
            }

        } else {

            if (currImg.equals(left2) || currImg.equals(right2)) {
                spriteNum1 = true;
            }

        }

    }

    public void draw(Graphics2D g2) {

        switch (direction) {
            case "up" -> {
                if (spriteNum1) {
                    currImg = up1;
                } else {
                    currImg = up2;
                }
            }
            case "down" -> {
                if (spriteNum1) {
                    currImg = down1;
                } else {
                    currImg = down2;
                }
            }
            case "left" -> {
                if (spriteNum1) {
                    currImg = left1;
                } else {
                    currImg = left2;
                }
            }
            case "right" -> {
                if (spriteNum1) {
                    currImg = right1;
                } else {
                    currImg = right2;
                }
            }
        }

        g2.drawImage(currImg, x, y, gamePanel.TILE_SIZE_COL, gamePanel.TILE_SIZE_ROW - 12, null);




    }
}
