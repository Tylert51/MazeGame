import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player {

    private int xCoord;
    private int yCoord;
    private Integer upSpeed, downSpeed, rightSpeed, leftSpeed;

    private GamePanel gamePanel;
    private KeyHandler keyHandler;

    private BufferedImage up, down, left, right;
    private String direction;
    private BufferedImage currImg;

    private int spriteCounter;
    private boolean spriteNum1;

    private CollisionChecker collisionChecker;
    private Rectangle collisionArea;
    private boolean colliding;

    private Tile[][] mazeMap ;
    //private Tile previousTile;
    private Tile currTile;


    public Player (GamePanel gp, KeyHandler kh) {

        gamePanel = gp;
        keyHandler = kh;

        setDefaultValues();
        getPlayerImage();

        colliding = false;
        collisionArea = new Rectangle(8, 3, 32, 45);

        mazeMap = (gamePanel.getMazes())[0].getMazeMap();

        currTile = mazeMap[getCurrTileCoords()[0]] [getCurrTileCoords()[1]];
    }

    public void addCollisionChecker(CollisionChecker collisionChecker) {
        this.collisionChecker = collisionChecker;
    }

    public void setDefaultValues() {
        xCoord = 0 * gamePanel.TILE_SIZE_COL + (2 * gamePanel.SCALE);
        yCoord = 11 * gamePanel.TILE_SIZE_ROW + (2 * gamePanel.SCALE);

        setDeafultSpeedValues();
        direction = "down";
    }

    public void setDeafultSpeedValues() {
        upSpeed = 0;
        downSpeed = 0;
        leftSpeed = 0;
        rightSpeed = 0;
    }

    public void getPlayerImage() {

        try {

            up = ImageIO.read(getClass().getResourceAsStream("/ghosts/up.png"));

            down = ImageIO.read(getClass().getResourceAsStream("/ghosts/down.png"));

            left = ImageIO.read(getClass().getResourceAsStream("/ghosts/left.png"));

            right = ImageIO.read(getClass().getResourceAsStream("/ghosts/right.png"));

            currImg = down;


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update() {

        if(keyHandler.isUpPressed() || keyHandler.isDownPressed() || keyHandler.isLeftPressed() || keyHandler.isRightPressed()) {

            ArrayList<String> availableMoves = new ArrayList<String>();

            int[] coords = getCurrTileCoords();


            currTile = mazeMap[coords[0]] [coords[1]];
            availableMoves = currTile.getAvailableMoves();


            setDeafultSpeedValues();

            for(String move : availableMoves) {
                if (move.equals("up")) {
                    upSpeed = 2;
                } else if (upSpeed != 2) {
                    upSpeed = 0;
                }

                if (move.equals("down")) {
                    downSpeed = 2;
                } else if (downSpeed != 2) {
                    downSpeed = 0;
                }

                if (move.equals("left")) {
                    leftSpeed = 2;
                } else if (leftSpeed != 2) {
                    leftSpeed = 0;
                }

                if (move.equals("right")) {
                    rightSpeed = 2;
                } else if (rightSpeed != 2) {
                    rightSpeed = 0;
                }
            }


            if (keyHandler.isUpPressed()) {
                direction = "up";
                yCoord -= upSpeed;

            } else if (keyHandler.isDownPressed()) {
                direction = "down";
                yCoord += downSpeed;

            } else if (keyHandler.isLeftPressed()) {
                direction = "left";
                xCoord -= leftSpeed;

            } else if (keyHandler.isRightPressed()) {
                direction = "right";
                xCoord += rightSpeed;

            }

            spriteCounter++;
            if (spriteCounter > 24) {    // 12 for 60 FPS (24 for 144 fps)
                spriteNum1 = !spriteNum1;
                spriteCounter = 0;
            }

        }

    }

    public void draw(Graphics2D g2) {

        switch (direction) {
            case "up" -> {
                currImg = up;
            }
            case "down" -> {
                currImg = down;
            }
            case "left" -> {
                currImg = left;
            }
            case "right" -> {
                currImg = right;
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

    public String getDirection() {
        return direction;
    }

    public int[] getCurrTileCoords() {
        int[] coords = new int[2];

        int xTopLeft = yCoord / gamePanel.TILE_SIZE_ROW;
        int yTopLeft = xCoord / gamePanel.TILE_SIZE_COL;

        int xTopRight = yCoord / gamePanel.TILE_SIZE_ROW;
        int yTopRight = (xCoord + (16 * gamePanel.SCALE)) / gamePanel.TILE_SIZE_COL;

        int xBottomLeft = (yCoord + (16 * gamePanel.SCALE)) / gamePanel.TILE_SIZE_ROW;
        int yBottomLeft = xCoord / gamePanel.TILE_SIZE_COL;

        int xBottomRight = (yCoord + (16 * gamePanel.SCALE)) / gamePanel.TILE_SIZE_ROW;
        int yBottomRight = (xCoord + (16 * gamePanel.SCALE)) / gamePanel.TILE_SIZE_COL;

        if(xCoordsMatch(xTopLeft, xTopRight, xBottomLeft, xBottomRight) && yCoordsMatch(yTopLeft, yTopRight, yBottomLeft, yBottomRight)) {
            coords[0] = xTopLeft;
            coords[1] = yTopLeft;
        } else {
            coords = null;
        }

        return coords;
    }

    private boolean xCoordsMatch(int x1, int x2, int x3, int x4) {
        return x1 == x2 && x1 == x3 && x1 == x4;
    }

    private boolean yCoordsMatch(int y1, int y2, int y3, int y4) {
        return y1 == y2 && y1 == y3 && y1 == y4;
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }

    public void move(int x, int y) {
        xCoord += x;
        yCoord += y;
    }
}

