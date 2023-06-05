import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class TilePickerPanel extends JPanel implements Runnable, MouseListener {

    //Screen Settings
    public final int ORIGINAL_TILE_COL = 20;
    public final int ORIGINAL_TILE_ROW = 20;
    public final int SCALE = 3;

    public final int TILE_SIZE_COL = ORIGINAL_TILE_COL * SCALE;
    public final int TILE_SIZE_ROW = ORIGINAL_TILE_ROW * SCALE;
    public final int MAX_SCREEN_COL = 5;
    public final int MAX_SCREEN_ROW = 9;
    public final int SCREEN_WIDTH = TILE_SIZE_COL * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE_ROW * MAX_SCREEN_ROW;

    public final double FPS = 60;

    private Thread gameThread;
    private KeyHandler keyH;
    private Player player;
    private GamePanel gamePanel;
    private DrawableMaze drawableMaze;
    private Tile[] possibleTiles;
    private Tile[][] tileMap;

    private boolean isTileSelected;
    private boolean isRotateSelected;
    private boolean isSaveSelected;

    private int[] mouseCoordsTile;
    private int[] mouseCoordsSetting;

    private Tile selectedTile;
    private Tile selectedSetting;


    public TilePickerPanel(GamePanel panel, Player player) {

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setDoubleBuffered(true);
        setFocusable(true);

        gamePanel = panel;
        keyH = new KeyHandler();
        this.player = player;


        addKeyListener(keyH);

        possibleTiles = new Tile[10];

        instantiateTiles();

        tileMap = new Tile[gamePanel.MAX_SCREEN_ROW][gamePanel.MAX_SCREEN_COL];
        setDefaultMap();

        addMouseListener(this);

        isTileSelected = false;
        isRotateSelected = false;
        isSaveSelected = false;

        mouseCoordsTile = new int[2];
        mouseCoordsSetting = new int[2];

        selectedTile = null;
        selectedSetting = null;

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



    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;


        for(int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++) {

                g2.drawImage(tileMap[i][j].getImage(), (j * this.TILE_SIZE_COL), (i * this.TILE_SIZE_ROW), 20 * this.SCALE, 20 * this.SCALE, null);

            }
        }

        if(isTileSelected) {
            drawSelectBox(g2, mouseCoordsTile[0], mouseCoordsTile[1]);
        }

        if(isRotateSelected || isSaveSelected) {
            drawSelectBoxSettings(g2, mouseCoordsSetting[0], mouseCoordsSetting[1]);
        }
    }

    public void instantiateTiles() {
        possibleTiles[0] = new Tile("/tiles/draw/one.png");
        possibleTiles[1] = new Tile("/tiles/draw/two.png");
        possibleTiles[2] = new Tile("/tiles/draw/three.png");
        possibleTiles[3] = new Tile("/tiles/draw/straight.png");

        possibleTiles[4] = new Tile("/tiles/draw/select_box.png");


        possibleTiles[5] = new Tile("/tiles/draw/rotate.png");
        possibleTiles[6] = new Tile("/tiles/draw/save.png");

        possibleTiles[7] = new Tile("/tiles/draw/select_box_red.png");

        possibleTiles[8] = new Tile("/tiles/empty.png");

    }

    public void setDefaultMap() {

        for(int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++) {

                tileMap[i][j] = possibleTiles[8];

            }
        }

        tileMap[1][1] = possibleTiles[0];
        tileMap[1][3] = possibleTiles[1];

        tileMap[3][1] = possibleTiles[2];
        tileMap[3][3] = possibleTiles[3];

        tileMap[6][1] = possibleTiles[5];
        tileMap[6][3] = possibleTiles[6];
    }

        @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        int[] mouseCoordCheck = getMouseCoords(e);

        if(isMouseOnTiles(mouseCoordCheck)) {

            mouseCoordsTile = getMouseCoords(e);
            isTileSelected = true;

            selectedTile = tileMap[mouseCoordsTile[0]]  [mouseCoordsTile[1]];


        } else {
            isTileSelected = false;
        }

        if(isMouseOnSettings(mouseCoordCheck)) {

            mouseCoordsSetting = getMouseCoords(e);

            if(isMouseOnRotate(mouseCoordsSetting)) {
                isRotateSelected = true;
                isSaveSelected = false;
            } else {
                isSaveSelected = true;
                isRotateSelected = false;
            }

            selectedSetting = tileMap[mouseCoordsTile[0]]  [mouseCoordsTile[1]];

            System.out.println("fjdkls;ajfk");

        } else {
            isRotateSelected = false;
            isSaveSelected = false;

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

        int xCoord = e.getY() / gamePanel.TILE_SIZE_ROW;
        int yCoord = e.getX() / gamePanel.TILE_SIZE_COL;

        coords[0] = xCoord;
        coords[1] = yCoord;

        return coords;

    }

    public void drawSelectBox (Graphics2D g2, int row, int col) {
        g2.drawImage(possibleTiles[4].getImage(), (col * this.TILE_SIZE_COL), (row * this.TILE_SIZE_ROW), 20 * this.SCALE, 20 * this.SCALE, null);
    }

    public void drawSelectBoxSettings (Graphics2D g2, int row, int col) {
        g2.drawImage(possibleTiles[7].getImage(), (col * this.TILE_SIZE_COL), (row * this.TILE_SIZE_ROW), 20 * this.SCALE, 20 * this.SCALE, null);
    }

    public boolean isMouseOnTiles(int[] mouseCoords) {
        int[] check1 = {1,1};
        int[] check2 = {1,3};
        int[] check3 = {3,1};
        int[] check4 = {3,3};
        int[] check5 = {0,0};

        return (Arrays.equals(mouseCoords, check1) || Arrays.equals(mouseCoords, check2) || Arrays.equals(mouseCoords, check3) || Arrays.equals(mouseCoords, check4) || Arrays.equals(mouseCoords, check5) );
    }

    public boolean isMouseOnSettings(int[] mouseCoords) {
        int[] check1 = {6,1};
        int[] check2 = {6,3};


        return (Arrays.equals(mouseCoords, check1) || Arrays.equals(mouseCoords, check2));
    }

    public boolean isMouseOnRotate(int[] mouseCoords) {
        int[] check1 = {6,1};

        return (Arrays.equals(mouseCoords, check1));
    }

    public boolean isTileSelected() {
        return isTileSelected;
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public boolean isRotateSelected() {
        return isRotateSelected;
    }

    public boolean isSaveSelected() {
        return isSaveSelected;
    }


}
