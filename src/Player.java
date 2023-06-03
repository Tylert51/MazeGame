import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {

    private int xCoord;
    private int yCoord;
    private int speed;

    private GamePanel gamePanel;
    private KeyHandler keyHandler;

    private BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    private String direction;
    private BufferedImage currImg;

    private int spriteCounter;
    private boolean spriteNum1;

    private Rectangle collisionArea;
    private boolean colliding;

    public Player (GamePanel gp, KeyHandler kh) {

        gamePanel = gp;
        keyHandler = kh;

        spriteCounter = 0;
        spriteNum1 = true;

        setDefaultValues();
        getPlayerImage();

        colliding = false;
        collisionArea = new Rectangle(8, 3, 32, 45);
    }

    public void setDefaultValues() {
        xCoord = 100;
        yCoord = 100;
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

        if(keyHandler.isUpPressed() || keyHandler.isDownPressed() || keyHandler.isLeftPressed() || keyHandler.isRightPressed()) {

            if (keyHandler.isUpPressed()) {
                direction = "up";
                yCoord -= speed;

            }
            if (keyHandler.isDownPressed()) {
                direction = "down";
                yCoord += speed;

            }
            if (keyHandler.isLeftPressed()) {
                direction = "left";
                xCoord -= speed;

            }
            if (keyHandler.isRightPressed()) {
                direction = "right";
                xCoord += speed;

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

        g2.drawImage(currImg, xCoord, yCoord, currImg.getWidth() * gamePanel.SCALE, currImg.getHeight() * gamePanel.SCALE, null);

    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public Rectangle getCollisionArea() {
        return collisionArea;
    }

    public String getDirection() {
        return direction;
    }
}

