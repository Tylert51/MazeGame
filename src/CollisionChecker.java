import java.util.ArrayList;

public class CollisionChecker {

    private GamePanel gamePanel;
    private Player player;
    private Maze[] mazes;

    public CollisionChecker(GamePanel gp) {
        gamePanel = gp;
        player = gp.getPlayer();
        mazes = gp.getMazes();
    }

    public ArrayList<String> checkTile(Player player) {
        Maze maze = mazes[0];
        Tile[][] tileMap = maze.getMazeMap();

        int[] currTileCoords = player.getCurrTileCoords();
        Tile currTile = tileMap[ currTileCoords[0] ]  [ currTileCoords[1] ];
        ArrayList<String> availableMoves = currTile.getAvailableMoves();

        return availableMoves;

    }
}
